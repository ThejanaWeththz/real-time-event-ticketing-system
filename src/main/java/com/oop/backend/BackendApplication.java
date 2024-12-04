package com.oop.backend;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@CrossOrigin
@RestController
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@GetMapping("/configuration")
	public HashMap<String, Object> getConfiguration() {
		Configuration.getInstance().loadConfig();
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("status", Configuration.getInstance().getIsRunning());
		response.put("total_tickets", Configuration.getInstance().getTotalTickets());
		return response;
	}

	@PostMapping("/configuration")
	public HashMap<String, Object> setConfiguration(@RequestBody HashMap<String, Object> body) {
		if (body.containsKey("status"))
			Configuration.getInstance().setIsRunning((boolean) body.get("status"));

		if (body.containsKey("total_tickets"))
			Configuration.getInstance().setTotalTickets((int) body.get("total_tickets"));

		if (body.containsKey("ticket_release_rate"))
			Configuration.getInstance().setTicketReleaseRate((int) body.get("ticket_release_rate"));

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
		new Thread(vendor, String.valueOf("Vendor " + vendor.getVendorId())).start();
		return vendor;
	}

	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody HashMap<String, Object> body) {
		int retrievalRate = body.containsKey("retrieval_rate") ? (int) body.get("retrieval_rate")
				: Configuration.getInstance().getCustomerRetrievalRate();

		Customer customer = new Customer(retrievalRate);

		new Thread(customer, String.valueOf("Customer " + customer.getCustomerId())).start();
		return customer;
	}
}
