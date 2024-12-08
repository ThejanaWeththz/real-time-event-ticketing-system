package com.oop.cli;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.google.gson.Gson;

public class Console {
    private static boolean isRunning = true;

    private static Gson file = new Gson();
    private static int response;
    private static Scanner input = new Scanner(System.in);

    private static HashMap<String, Object> config = new HashMap<>();

    public static void main(String[] args) {
        config.put("isRunning", isRunning);
        System.out.println("Welcome to the Event Ticket Managing System");
        System.out.println("Setup configuration\n");

        prompt("Total ticket capacity for the ticket pool: ");
        config.put("totalTickets", response);

        prompt("Ticket releasing rate for vendors(set in seconds): ");
        config.put("ticketReleaseRate", response);

        prompt("Ticket retrieving rate for customers(set in seconds): ");
        config.put("customerRetrievalRate", response);

        prompt("Maximum ticket capacity for the ticket pool at a time: ");
        config.put("maxTicketCapacity", response);

        input.close();
        saveConfig();
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