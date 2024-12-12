import { NgClass, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from '../app.component';

interface Customer {
  customerId: number;
  runStatus: boolean;
  retrievalInterval: number;
}

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [FormsModule, NgIf, NgFor, NgClass],
  templateUrl: './customer.component.html',
})
export class CustomerComponent implements OnInit {
  retrievalInterval: number | undefined = undefined;
  customerId: number | undefined;
  isCustomersLoading: boolean = false;

  customers: any[] = [];

  constructor(private host: AppComponent) { }


  async ngOnInit(): Promise<void> {
    this.loadCustomer();
    setInterval(() => this.loadCustomer(), 1000);
  }

  async addCustomer(retrieval_interval?: number): Promise<void> {
    if (retrieval_interval === undefined || retrieval_interval < 0) {
      console.error('Retrieval Interval cannot be negative!');
      return;
    }
    this.host.client.post("/customers", {
      retrieval_interval
    })
  }

  async customerStatus(customer_id?: number): Promise<void> {
    this.host.client.post(`/customers/${customer_id}`, {
    })
  }

  getCustomerSize() {
    return this.customers.length;
  }

  async loadCustomer(): Promise<void> {
    try {
      const response = await this.host.client.get('/customers');
      console.log(response.data);
      this.customers = response.data;
    } catch (error) {
      this.customers = [];
    }
  }
}
