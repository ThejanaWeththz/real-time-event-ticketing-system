package com.oop.cli.models;

import java.util.Random;

public class Vendor {
    private Integer vendorId;
    private Integer ticketsPerRelease;
    private Integer ticketReleaseRate;

    private boolean runStatus;

    public Vendor(int ticketsPerRelease, int ticketReleaseRate) {
        this.vendorId = new Random().nextInt(1000000);
        this.ticketsPerRelease = ticketsPerRelease;
        this.ticketReleaseRate = ticketReleaseRate;
        this.runStatus = true;
    }

    public Integer getVendorId() {
        return this.vendorId;
    }

    public Integer getTicketsPerRelease() {
        return this.ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public Integer getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public boolean getRunStatus() {
        return this.runStatus;
    }

    public void setRunStatus(boolean runStatus) {
        this.runStatus = runStatus;
    }
}
