package com.oop.backend;

import java.util.Random;

public class Vendor implements Runnable {
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    public Vendor(int ticketsPerRelease, int releaseInterval) {
        this.vendorId = new Random().nextInt(1000000);
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {

        while (true) {
            while (Configuration.getInstance().getIsRunning() == false) {
                continue;
            }
            while (TicketPool.getInstance().getTicketCount() >= Configuration.getInstance().getTotalTickets()) {
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
                Thread.sleep(releaseInterval);
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

    public double getReleaseInterval() {
        return this.releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }
}
