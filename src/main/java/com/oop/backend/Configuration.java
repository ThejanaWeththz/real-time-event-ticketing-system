package com.oop.backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Configuration {

    private static Configuration instance;

    private boolean isRunning = false;

    private int totalTickets = 10;
    private int ticketReleaseRate = 4000;
    private int customerRetrievalRate = 4000;
    private int maxTicketCapacity = 5;
    private static Gson file = new Gson();

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public void saveConfig() {
        try {
            FileWriter writer = new FileWriter("config.json");
            file.toJson(instance, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("JSON error");
        }
    }

    public void loadConfig() {
        try {
            FileReader reader = new FileReader("config.json");
            instance = file.fromJson(reader, Configuration.class);
        } catch (FileNotFoundException e) {
            System.out.println("config.json not found.");
        }
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
