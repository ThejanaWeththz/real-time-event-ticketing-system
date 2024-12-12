import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AppComponent } from '../app.component';
import { NgClass } from '@angular/common';

interface Configuration {
  status: boolean;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, NgClass],
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  status: boolean;

  config: any | undefined = undefined;

  constructor(private host: AppComponent) {
    this.status = false;
  }

  ngOnInit(): void {
    this.getStatus();
    setInterval(() => this.getStatus(), 1000);
  }

  async getStatus(): Promise<void> {
    try {
      const response = await this.host.client.get('/configuration')
      this.config = response.data;
      console.log(this.config.status);
      this.status = this.config.status;
    } catch (error) {
      this.status = false;
    }
  }

  async setStatus() {
    if (this.status == false) {
      this.status = true;
    } else {
      this.status = false;
    }
    this.host.client.post('/configuration', {
      this: this.status,
    })
  }
}

