package com.oop.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oop.config.Configuration;

public class TicketPool {
    Logger log = LoggerFactory.getLogger(TicketPool.class);
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    private static TicketPool instance;

    private List<Ticket> tickets = Collections.synchronizedList(new ArrayList<Ticket>());
    private int size = Configuration.getInstance().getMaxTicketCapacity();
    private int ticketCount;

    private TicketPool() {
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
                log.warn("Pool size full. Ticket {} in queue.", ticket.getTicketId());
                notEmpty.await();
            }
            ticketCount++;
            tickets.add(ticket);
            System.out.println(ticketCount);
            log.info("Ticket {} added. TicketPool size is {}", ticket.getTicketId(), tickets.size());
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
                log.warn("Tickets unavailable. On queue to be removed.");
                notFull.await();
            }
            log.info("Ticket {} removed.", tickets.get(0).getTicketId());
            tickets.remove(0);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
