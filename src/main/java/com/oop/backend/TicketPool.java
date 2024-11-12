package com.oop.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private static TicketPool instance;

    private List<Ticket> tickets = Collections.synchronizedList(new ArrayList<Ticket>());
    private int size = Configuration.getInstance().getMaxTicketCapacity();
    private int ticketCount;

    public TicketPool() {
    }

    public static TicketPool getInstance() {
        if (instance == null) {
            instance = new TicketPool();
        }
        return instance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public int getTicketCount() {
        return this.ticketCount;
    }

    public synchronized void addTicket(Ticket ticket) {
        try {
            if (tickets.size() >= size) {
                System.out.printf("Pool size full. Ticket %d in queue.%n", ticket.getTicketId());
                wait();
            }
            ticketCount++;
            tickets.add(ticket);
            System.out.println(ticketCount);
            System.out.printf("Ticket %d added.%n", ticket.getTicketId());
            notifyAll();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void removeTicket(Ticket ticket) {
        try {
            if (tickets.isEmpty()) {
                System.out.println("Tickets unavailable. On queue to be removed.");
                wait();
            }
            Thread.sleep(3000);
            System.out.printf("Ticket %d removed.%n", tickets.get(0).getTicketId());
            tickets.remove(0);
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
