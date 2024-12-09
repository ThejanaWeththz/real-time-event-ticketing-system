import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgFor, NgIf } from '@angular/common';

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
  private http: HttpClient;

  isTicketsLoading: boolean = false;

  tickets: Ticket[] = [];

  constructor(http: HttpClient) {
    this.http = http;
  }

  ngOnInit(): void {
    this.loadProducts();
    setInterval(() => this.loadProducts(), 1000);
  }

  loadProducts(): void {
    this.isTicketsLoading = true;
    this.http
      .get<Ticket[]>('http://localhost:8080/tickets')
      .subscribe((tickets) => {
        this.tickets = tickets;
        this.isTicketsLoading = false;
        console.log(tickets);
      });
  }
}
