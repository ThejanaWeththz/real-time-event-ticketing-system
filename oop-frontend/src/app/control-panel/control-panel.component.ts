import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

interface Configuration {
  status: boolean;
}

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './control-panel.component.html',
})
export class ControlPanelComponent implements OnInit {
  status: boolean;
  config: any;

  constructor(private host: AppComponent) {
    this.status = true;
  }

  ngOnInit(): void {
    this.getStatus();
  }

  async getStatus(): Promise<void> {
    try {
      const response = await this.host.client.get('/configuration')
      this.config = response.data;
    } catch (error) {
      this.status = false;
    }
  }

  async setStatus(): Promise<void> {
    this.status = this.config.status;
    if (this.status == false) {
      this.status = true;
    } else {
      this.status = false;
    }
    this.host.client.post('/configuration', {
      status: this.status,
    })
  }
}
