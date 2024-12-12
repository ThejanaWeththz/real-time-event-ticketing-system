package com.oop.config;

// importing libraries
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Configuration {

    // Singleton pattern
    private static Configuration instance;

    private boolean appStatus = false; // System Status

    private int totalTickets = 10;
    private int ticketReleaseRate = 4000;
    private int customerRetrievalRate = 4000;
    private int maxTicketCapacity = 5;
    private static Gson file = new Gson();

    // Private constructor for singleton
    private Configuration() {
    }

    // getInstance method for singleton
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

    public boolean getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(boolean appStatus) {
        this.appStatus = appStatus;
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
