package com.oop.backend;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oop.config.Configuration;

public class Vendor implements Runnable {
    Logger log = LoggerFactory.getLogger(Vendor.class);
    private int vendorId;
    private int ticketsPerRelease;
    private int ticketReleaseRate;

    public Vendor(int ticketsPerRelease, int ticketReleaseRate) {
        this.vendorId = new Random().nextInt(1000000);
        this.ticketsPerRelease = ticketsPerRelease;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!Configuration.getInstance().getIsRunning()) {
                    continue;
                }
                if (TicketPool.getInstance().getTicketCount() >= Configuration.getInstance().getTotalTickets()) {
                    continue;
                }
                for (int i = 0; i < ticketsPerRelease; i++) {
                    TicketPool.getInstance().addTicket(new Ticket());
                    if (TicketPool.getInstance().getTicketCount() >= Configuration.getInstance().getTotalTickets()) {
                        log.info("Total amount of ticket capacity reached.");
                    }
                }
                log.info("Vendor {} released {} tickets.", this.vendorId, this.ticketsPerRelease);
                Thread.sleep(ticketReleaseRate * 1000);
            }
        } catch (InterruptedException e) {
            log.warn("Vendor {} stopped.", this.vendorId);
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
}
