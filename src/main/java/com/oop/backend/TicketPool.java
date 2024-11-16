package com.oop.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

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

    public void addTicket(Ticket ticket) {
        lock.lock();
        try {
            while (tickets.size() >= size) {
                System.out.printf("Pool size full. Ticket %d in queue.%n", ticket.getTicketId());
                notEmpty.await();
            }
            ticketCount++;
            tickets.add(ticket);
            System.out.println(ticketCount);
            System.out.printf("Ticket %d added.%n", ticket.getTicketId());
            notFull.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void removeTicket() {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                System.out.println("Tickets unavailable. On queue to be removed.");
                notFull.await();
            }
            Thread.sleep(3000);
            System.out.printf("Ticket %d removed.%n", tickets.get(0).getTicketId());
            tickets.remove(0);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
