package com.oop.cli.models;

import java.util.Random;

public class Customer {
    private int customerId;
    private int customerRetrievalRate;

    private boolean runStatus;

    public Customer(int customerRetrievalRate) {
        this.customerId = new Random().nextInt(1000000);
        this.customerRetrievalRate = customerRetrievalRate;
        this.runStatus = true;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public Integer getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public void setcustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public boolean getRunStatus() {
        return this.runStatus;
    }

    public void setRunStatus(boolean runStatus) {
        this.runStatus = runStatus;
    }
}
