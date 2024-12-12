package com.oop.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.oop.cli.models.*;
import com.oop.cli.util.Client;

public class CliApplication {

    private static Scanner input = new Scanner(System.in);
    private static Client client = new Client("http://localhost:8080");
    private static int response;

    public static void main(String[] args) {
        // Welcome message
        System.out.println("Welcome to Real Time Ticket Managing Application\n");
        // Main while loop
        while (true) {
            System.out.println("""
                    *************************************************
                    *\t\t\t\t\tMENU OPTIONS\t\t\t\t*
                    *************************************************
                    """);
            System.out.print("""
                    \t1) Set Configuration
                    \t2) Add Vendor
                    \t3) Add Customer
                    \t4) View Vendors
                    \t5) View Customers
                    \t0) Exit
                    *************************************************

                    Please select an option :\s""");
            // Getting user inputs for the menu
            try {
                int userOption = input.nextInt();
                switch (userOption) {
                    case 1:
                        updateConfiguration();
                        break;
                    case 2:
                        addVendor();
                        break;
                    case 3:
                        addCustomer();
                        break;
                    case 4:
                        getVendor();
                        break;
                    case 5:
                        getCustomer();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid input");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                input.next();
            }
        }

    }

    public static void updateConfiguration() {
        Map<String, Object> map = new HashMap<>();
        System.out.println("Welcome to the Event Ticket Managing System");
        System.out.println("Setup configuration\n");

        prompt("Total ticket capacity for the ticket pool: ");
        int totalTickets = response;

        prompt("Ticket releasing rate for vendors(set in seconds): ");
        int releaseRate = response;

        prompt("Ticket retrieving rate for customers(set in seconds): ");
        int retrievalRate = response;

        prompt("Maximum ticket capacity for the ticket pool at a time: ");
        int maxTicketCapacity = response;

        map.put("total_tickets", totalTickets);
        map.put("release_rate", releaseRate);
        map.put("retrieval_rate", retrievalRate);
        map.put("max_tickets", maxTicketCapacity);

        client.post("/configuration", map, Configuration.class);
    }

    public static Vendor addVendor() {
        Map<String, Object> map = new HashMap<>();

        prompt("Enter Tickets Per Release : ");
        int ticketsPerRelease = response;
        prompt("Enter Tickets Release Rate : ");
        int ticketReleaseRate = response;

        map.put("tickets_per_release", ticketsPerRelease);
        map.put("release_rate", ticketReleaseRate);

        Vendor createdVendor = client.post("/vendors", map, Vendor.class);
        System.out.println("Vendor ID: " + createdVendor.getVendorId());
        System.out.println("Tickets Per Release: " + createdVendor.getTicketsPerRelease());
        System.out.println("Tickets Release Rate: " + createdVendor.getTicketReleaseRate());
        System.out.println("Vendor Status: " + createdVendor.getRunStatus());
        System.out.println();
        if (createdVendor == null || createdVendor.getVendorId() == null) {
            System.out.println("Fail");
        }
        return createdVendor;
    }

    public static Customer addCustomer() {
        Map<String, Object> map = new HashMap<>();

        prompt("Enter Retrieval Rate : ");
        int retrievalInterval = response;

        map.put("retrieval_rate", retrievalInterval);

        Customer createdCustomer = client.post("/customers", map, Customer.class);
        System.out.println("Customer ID: " + createdCustomer.getCustomerId());
        System.out.println("Customer Retrieval Rate: " + createdCustomer.getCustomerRetrievalRate());
        System.out.println("Vendor Status: " + createdCustomer.getRunStatus());
        System.out.println();
        if (createdCustomer == null || createdCustomer.getCustomerId() == null) {
            System.out.println("Fail");
        }
        return createdCustomer;
    }

    public static List<Vendor> getVendor() {
        Vendor[] list = client.get("/vendors", Vendor[].class);
        for (Vendor vendor : list) {
            System.out.println("Vendor ID: " + vendor.getVendorId());
            System.out.println("Tickets Per Release: " + vendor.getTicketsPerRelease());
            System.out.println("Vendor Status: " + vendor.getRunStatus());
            System.out.println();
        }
        return Arrays.asList(list);
    }

    public static List<Customer> getCustomer() {
        Customer[] list = client.get("/customers", Customer[].class);
        for (Customer customer : list) {
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Retrieval Rate: " + customer.getCustomerRetrievalRate());
            System.out.println();
        }
        return Arrays.asList(list);
    }

    private static void prompt(String message) {
        while (true) {
            try {
                System.out.print(message);
                response = input.nextInt();
                if (response < 0) {
                    System.out.println("Please enter a valid amount greater than one.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid amount.");
                input.next();
            }
        }
    }
}
