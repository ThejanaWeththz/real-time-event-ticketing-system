package com.oop.backend;

// importing libraries
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oop.config.Configuration;
import com.oop.logger.LogHelper;

public class Vendor implements Runnable {
    // Logger for logging
    Logger log = LoggerFactory.getLogger(Vendor.class);
    private int vendorId;
    private int ticketsPerRelease;
    private int ticketReleaseRate;
    private boolean message;

    private boolean runStatus;

    // Vendor Constructor
    public Vendor(int ticketsPerRelease, int ticketReleaseRate) {
        this.vendorId = new Random().nextInt(1000000);
        this.ticketsPerRelease = ticketsPerRelease;
        this.ticketReleaseRate = ticketReleaseRate;
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
                if (TicketPool.getInstance().getTicketCount() >= Configuration.getInstance().getTotalTickets()) {
                    continue;
                }
                for (int i = 0; i < ticketsPerRelease; i++) {
                    TicketPool.getInstance().addTicket(new Ticket());
                    if (TicketPool.getInstance().getTicketCount() >= Configuration.getInstance().getTotalTickets()) {
                        LogHelper.getInstance().addLog(String.format("Total amount of ticket capacity reached."));
                        message = true;
                    }
                }
                if ((Configuration.getInstance().getMaxTicketCapacity() == TicketPool.getInstance().getTicketCount())) {
                    message = true;
                } else {
                    message = false;
                }
                if (!message) {
                    LogHelper.getInstance().addLog(
                            String.format("Vendor %d released %d tickets.", this.vendorId, this.ticketsPerRelease));
                }
                Thread.sleep(ticketReleaseRate * 1000);
            }
        } catch (InterruptedException e) {
            LogHelper.getInstance().addLog(
                    String.format("Vendor %d stopped.", this.vendorId));
            Thread.currentThread().interrupt();
        }
    }

    public int getVendorId() {
        return this.vendorId;
    }

    public int getTicketsPerRelease() {
        return this.ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public double getTicketReleaseRate() {
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
