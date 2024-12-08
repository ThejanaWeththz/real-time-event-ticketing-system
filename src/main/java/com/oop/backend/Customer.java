package com.oop.backend;

import java.util.Random;

import com.oop.config.Configuration;

public class Customer implements Runnable {
    private int customerId;
    private int customerRetrievalRate;

    public Customer(int customerRetrievalRate) {
        this.customerId = new Random().nextInt(1000000);
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!Configuration.getInstance().getIsRunning()) {
                    continue;
                }
                TicketPool.getInstance().removeTicket();
                System.out.printf("Customer %d purchased ticket.%n", this.customerId);
                Thread.sleep(customerRetrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " stopped.");
            Thread.currentThread().interrupt();
        }
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public double getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public void setcustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
}
