package com.oop.backend;

import java.util.Random;

import com.oop.config.Configuration;

public class Vendor implements Runnable {
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
                    System.out.println("Total amount of ticket capacity reached");
                }
            }
            System.out.printf("Vendor %d released %d tickets.%n", this.vendorId,
                    this.ticketsPerRelease);
            try {
                Thread.sleep(ticketReleaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

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
