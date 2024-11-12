package com.oop.backend;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OopdemoApplication.class, args);
	}

	@GetMapping("/configuration")
	public HashMap<String, Object> getConfiguration() {
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("status", Configuration.getInstance().getIsRunning());
		return response;
	}

	@PostMapping("/configuration")
	public HashMap<String, Object> setConfiguration(@RequestBody HashMap<String, Object> status) {
		if (status.containsKey("status"))
			Configuration.getInstance().setIsRunning((boolean) status.get("status"));
		return status;
	}

	@GetMapping("/tickets")
	public List<Ticket> getTickets() {
		return TicketPool.getInstance().getTickets();
	}

	@PostMapping("/vendors")
	public Vendor createVendor(@RequestBody HashMap<String, Object> body) {
		int ticketsPerRelease = body.containsKey("tickets_per_release") ? (int) body.get("tickets_per_release")
				: Configuration.getInstance().getTicketReleaseRate();
		int releaseInterval = body.containsKey("release_interval") ? (int) body.get("release_interval")
				: Configuration.getInstance().getReleaseInterval();

		Vendor vendor = new Vendor(ticketsPerRelease, releaseInterval);
		new Thread(vendor, String.valueOf(vendor.getVendorId())).start();
		return vendor;
	}

	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody HashMap<String, Object> body) {
		int retrievalInterval = body.containsKey("retrieval_internal") ? (int) body.get("retrieval_interval")
				: Configuration.getInstance().getCustomerRetrievalRate();

		Customer customer = new Customer(retrievalInterval);

		new Thread(customer, String.valueOf(customer.getCustomerId())).start();
		return customer;
	}
}
