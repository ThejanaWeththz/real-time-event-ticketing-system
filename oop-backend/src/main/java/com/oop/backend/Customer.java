package com.oop.backend;

// importing libraries
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oop.config.Configuration;
import com.oop.logger.LogHelper;

public class Customer implements Runnable {
    // Logger for logging
    Logger log = LoggerFactory.getLogger(Customer.class);
    private int customerId;
    private int customerRetrievalRate;

    private boolean runStatus;

    // Customer Constructor
    public Customer(int customerRetrievalRate) {
        this.customerId = new Random().nextInt(1000000);
        this.customerRetrievalRate = customerRetrievalRate;
        this.runStatus = true;
    }

    // Overriding run method
    @Override
    public void run() {
        try {
            while (true) {
                if (!Configuration.getInstance().getAppStatus()) {
                    continue;
                }
                TicketPool.getInstance().removeTicket();
                LogHelper.getInstance().addLog(String.format("Customer %d purchased ticket.", this.customerId));
                Thread.sleep(customerRetrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            LogHelper.getInstance().addLog(String.format("Customer %d stopped.", this.customerId));
            Thread.currentThread().interrupt();
        }
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public double getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public boolean getRunStatus() {
        return this.runStatus;
    }

    public void setRunStatus(boolean runStatus) {
        this.runStatus = runStatus;
    }
}
