import { Component, OnInit } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { AppComponent } from '../app.component';

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
  title = 'oop-frontend';

  config: any | undefined = undefined;

  totalTickets: number | undefined = undefined;
  ticketReleaseRate: number | undefined = undefined;
  customerRetrievalRate: number | undefined = undefined;
  maxTicketCapacity: number | undefined = undefined;
  tickets: any;

  constructor(private host: AppComponent) { }

  async ngOnInit(): Promise<void> {
    setInterval(() => 1000);
  }

  async getConfig(): Promise<void> {
    try {
      const response = await this.host.client.get('/configuration');
      console.log(response.data);
      this.config = response.data;
    } catch (error) {
      this.config = 0;
    }
  }

  async saveConfig(
    total_tickets?: number,
    release_rate?: number,
    retrieval_rate?: number,
    max_tickets?: number
  ): Promise<void> {
    console.log(total_tickets, release_rate, retrieval_rate, max_tickets);
    this.host.client.post('/configuration',
      {
        total_tickets,
        release_rate,
        retrieval_rate,
        max_tickets,
      })
  }
}
