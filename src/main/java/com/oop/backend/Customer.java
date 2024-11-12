package com.oop.backend;

import java.util.Random;

public class Customer implements Runnable {
    private int customerId;
    private int retrievalInterval;

    public Customer(int retrievalInterval) {
        this.customerId = new Random().nextInt(1000000);
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        while (Configuration.getInstance().getIsRunning()) {
            TicketPool.getInstance().removeTicket();
            System.out.printf("Customer %d purchased ticket.%n", this.customerId);
            try {
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public double retrievalInterval() {
        return this.retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }
}
