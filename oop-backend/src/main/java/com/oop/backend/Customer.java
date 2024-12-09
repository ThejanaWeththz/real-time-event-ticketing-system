package com.oop.backend;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oop.config.Configuration;

public class Customer implements Runnable {
    Logger log = LoggerFactory.getLogger(Customer.class);
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
                log.info("Customer {} purchased ticket.", this.customerId);
                Thread.sleep(customerRetrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            log.warn("Customer {} stopped.", this.customerId);
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
