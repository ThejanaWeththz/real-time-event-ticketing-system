package com.oop.backend;

import java.util.Random;

public class Customer implements Runnable {
    private int customerId;
    private int customerRetrievalRate;

    public Customer(int customerRetrievalRate) {
        this.customerId = new Random().nextInt(1000000);
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (Configuration.getInstance().getIsRunning()) {
            TicketPool.getInstance().removeTicket();
            System.out.printf("Customer %d purchased ticket.%n", this.customerId);
            try {
                Thread.sleep(customerRetrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
