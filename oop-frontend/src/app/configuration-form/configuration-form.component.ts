import { Component, OnInit } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Configuration {
  total_tickets?: number;
  release_rate?: number;
  retrieval_rate?: number;
  max_tickets?: number;
}

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './configuration-form.component.html',
})
export class ConfigurationFormComponent implements OnInit {
  private http: HttpClient;
  title = 'oop-frontend';

  config: Configuration | undefined = undefined;

  totalTickets: number | undefined = undefined;
  ticketReleaseRate: number | undefined = undefined;
  customerRetrievalRate: number | undefined = undefined;
  maxTicketCapacity: number | undefined = undefined;
  tickets: any;

  constructor(http: HttpClient) {
    this.http = http;
  }

  async ngOnInit(): Promise<void> {
    setInterval(() => 1000);
  }

  getConfig(): void {
    this.http
      .get<Configuration>('http://localhost:8080/configuration')
      .subscribe((config) => {
        this.config = config;
        console.log(config);
      });
  }

  savesConfig(
    total_tickets?: number,
    release_rate?: number,
    retrieval_rate?: number,
    max_tickets?: number
  ): void {
    console.log(total_tickets, release_rate, retrieval_rate, max_tickets);
    this.http
      .post('http://localhost:8080/configuration', {
        max_tickets,
        release_rate,
        retrieval_rate,
        total_tickets,
      })
      .subscribe((config) => {
        this.getConfig();
      });
  }
}
