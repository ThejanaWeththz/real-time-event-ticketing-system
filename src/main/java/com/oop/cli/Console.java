package com.oop.cli;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import com.google.gson.Gson;

public class Console {
    private static boolean isRunning = true;

    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;
    private static Gson file = new Gson();

    private static HashMap<String, Object> config = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        config.put("isRunning", isRunning);
        System.out.println("Welcome to the Event Ticket Managing System");
        System.out.println("Setup configuration\n");

        System.out.print("Total ticket capacity for the ticket pool: ");
        totalTickets = input.nextInt();
        config.put("totalTickets", totalTickets);

        System.out.print("Ticket releasing rate for vendors(set in milliseconds(ex : 1000)): ");
        ticketReleaseRate = input.nextInt();
        config.put("ticketReleaseRate", ticketReleaseRate);

        System.out.print("Ticket retrieving rate for customers(set in milliseconds(ex : 1000)): ");
        customerRetrievalRate = input.nextInt();
        config.put("customerRetrievalRate", customerRetrievalRate);

        System.out.print("Maximum ticket capacity for the ticket pool at a time: ");
        maxTicketCapacity = input.nextInt();
        config.put("maxTicketCapacity", maxTicketCapacity);

        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);

        input.close();
        saveConfig();
    }

    public static void saveConfig() {
        try {
            FileWriter writer = new FileWriter("config.json");
            file.toJson(config, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("JSON error");
        }
    }
}