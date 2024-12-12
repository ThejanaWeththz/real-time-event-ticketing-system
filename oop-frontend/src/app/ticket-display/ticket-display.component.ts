import { Component, OnInit } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { AppComponent } from '../app.component';

interface Ticket {
  ticketId: number;
  ticket: number;
}

@Component({
  selector: 'app-ticket-display',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './ticket-display.component.html',
})
export class TicketDisplayComponent implements OnInit {
  isTicketsLoading: boolean = false;

  tickets: any[] = [];

  constructor(private host: AppComponent) { }

  ngOnInit(): void {
    this.loadTickets();
    setInterval(() => (this.loadTickets(), this.getTicketSize()), 1000);
  }

  getTicketSize() {
    return this.tickets.length;
  }

  async loadTickets() {
    try {
      const response = await this.host.client.get('/tickets');
      console.log(response.data);
      this.tickets = response.data;
    } catch (error) {
      this.tickets = [];
    }
  }
}
