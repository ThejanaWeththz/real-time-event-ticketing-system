package com.oop.backend;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.config.Configuration;
import com.oop.cli.Console;

@SpringBootApplication
@CrossOrigin
@RestController
public class BackendApplication {
	public HashMap<Integer, Thread> vendorThreads = new HashMap<Integer, Thread>();
	public HashMap<Integer, Thread> customerThreads = new HashMap<Integer, Thread>();
	public HashMap<Integer, Vendor> vendors = new HashMap<Integer, Vendor>();
	public HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();

	public static void main(String[] args) {
		Console.main(args);
		SpringApplication.run(BackendApplication.class, args);
		Configuration.getInstance().loadConfig();
	}

	@GetMapping("/configuration")
	public HashMap<String, Object> getConfiguration() {
		Configuration.getInstance().loadConfig();
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("status", Configuration.getInstance().getIsRunning());
		response.put("total_tickets", Configuration.getInstance().getTotalTickets());
		response.put("release_rate", Configuration.getInstance().getTicketReleaseRate());
		response.put("retrieval_rate", Configuration.getInstance().getCustomerRetrievalRate());
		response.put("max_tickets", Configuration.getInstance().getMaxTicketCapacity());
		return response;
	}

	@PostMapping("/configuration")
	public HashMap<String, Object> setConfiguration(@RequestBody HashMap<String, Object> body) {
		if (body.containsKey("status"))
			Configuration.getInstance().setIsRunning((boolean) body.get("status"));

		if (body.containsKey("total_tickets"))
			Configuration.getInstance().setTotalTickets((int) body.get("total_tickets"));

		if (body.containsKey("release_rate"))
			Configuration.getInstance().setTicketReleaseRate((int) body.get("release_rate"));

		if (body.containsKey("retrieval_rate"))
			Configuration.getInstance().setCustomerRetrievalRate((int) body.get("retrieval_rate"));

		if (body.containsKey("max_tickets"))
			Configuration.getInstance().setMaxTicketCapacity((int) body.get("max_tickets"));

		Configuration.getInstance().saveConfig();
		return body;
	}

	@GetMapping("/tickets")
	public List<Ticket> getTickets() {
		List<Ticket> ticketList = TicketPool.getInstance().getTickets();
		return ticketList;
	}

	@PostMapping("/vendors")
	public Vendor createVendor(@RequestBody HashMap<String, Object> body) {
		int ticketsPerRelease = body.containsKey("tickets_per_release") ? (int) body.get("tickets_per_release")
				: Configuration.getInstance().getTicketReleaseRate();
		int releaseRate = body.containsKey("release_rate") ? (int) body.get("release_rate")
				: Configuration.getInstance().getTicketReleaseRate();

		Vendor vendor = new Vendor(ticketsPerRelease, releaseRate);
		vendors.put(vendor.getVendorId(), vendor);
		Thread vendorThread = new Thread(vendor, String.valueOf("Vendor " + vendor.getVendorId()));
		vendorThreads.put(vendor.getVendorId(), vendorThread);
		vendorThread.start();
		return vendor;
	}

	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody HashMap<String, Object> body) {
		int retrievalRate = body.containsKey("retrieval_rate") ? (int) body.get("retrieval_rate")
				: Configuration.getInstance().getCustomerRetrievalRate();

		Customer customer = new Customer(retrievalRate);
		customers.put(customer.getCustomerId(), customer);
		Thread customerThread = new Thread(customer, String.valueOf("Customer " + customer.getCustomerId()));
		customerThreads.put(customer.getCustomerId(), customerThread);
		customerThread.start();
		return customer;
	}

	@PostMapping("/vendors/{vendorId}")
	public void switchVendor(@PathVariable int vendorId) {
		Thread vendorThread = vendorThreads.get(vendorId);
		if (vendorThread.isAlive()) {
			vendorThread.interrupt();
			vendorThreads.replace(vendorId, new Thread(vendors.get(vendorId), "Vendor: " + vendorId));
		} else {
			vendorThread.start();
		}
	}

	@PostMapping("/customers/{customerId}")
	public void switchCustomer(@PathVariable int customerId) {
		Thread customerThread = customerThreads.get(customerId);
		if (customerThread.isAlive()) {
			customerThread.interrupt();
			customerThreads.replace(customerId, new Thread(customers.get(customerId), "Customer: " + customerId));
		} else {
			customerThread.start();
		}
	}
}
