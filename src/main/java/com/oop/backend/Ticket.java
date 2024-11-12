package com.oop.backend;

import java.util.Random;

public class Ticket {

    private int ticketId;

    public Ticket() {
        this.ticketId = new Random().nextInt(10000);
    }

    public int getTicketId() {
        return ticketId;
    }
}
