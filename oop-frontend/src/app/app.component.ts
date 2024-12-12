import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import axios, { AxiosInstance } from 'axios';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
})
export class AppComponent {
  private instance: AxiosInstance;

  constructor() {
    this.instance = axios.create({
      baseURL: 'http://localhost:8080',
    });
  }

  get client() {
    return this.instance;
  }
}
