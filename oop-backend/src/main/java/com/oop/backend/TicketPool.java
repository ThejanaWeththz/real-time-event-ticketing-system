package com.oop.backend;

// importing libraries
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oop.config.Configuration;
import com.oop.logger.LogHelper;

public class TicketPool {
    // Logger for logging
    Logger log = LoggerFactory.getLogger(TicketPool.class);
    // ReentrantLock for thread-safety
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    // Singleton pattern
    private static TicketPool instance;

    // List of tickets
    private List<Ticket> tickets = Collections.synchronizedList(new ArrayList<Ticket>());
    private int size = Configuration.getInstance().getMaxTicketCapacity();
    private int ticketCount; // ticket count tracker

    // Private constructor for singleton
    private TicketPool() {
    }

    // getInstance method for singleton
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

    // addTicket method for adding
    public void addTicket(Ticket ticket) {
        lock.lock();
        try {
            while (tickets.size() >= size) {
                LogHelper.getInstance().addLog(
                        String.format("Pool size full. Ticket %d in queue.", ticket.getTicketId()));
                notEmpty.await();
            }
            ticketCount++;
            tickets.add(ticket);
            System.out.println(ticketCount);
            LogHelper.getInstance().addLog(
                    String.format("Ticket %d added. TicketPool size is %d", ticket.getTicketId(), tickets.size()));
            notFull.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    // removeTicket method for adding
    public void removeTicket() {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                LogHelper.getInstance().addLog(
                        String.format("Tickets unavailable. On queue to be removed."));
                notFull.await();
            }
            LogHelper.getInstance().addLog(
                    String.format("Ticket %d removed.", tickets.get(0).getTicketId()));
            tickets.remove(0);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
