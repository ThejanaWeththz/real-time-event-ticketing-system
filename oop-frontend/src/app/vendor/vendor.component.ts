import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { FormsModule } from '@angular/forms';
import { NgClass, NgFor, NgIf } from '@angular/common';

interface Vendor {
  vendorId: number;
  runStatus: boolean;
  ticketsPerRelease: number;
}

@Component({
  selector: 'app-vendor',
  standalone: true,
  imports: [FormsModule, NgIf, NgFor, NgClass],
  templateUrl: './vendor.component.html',
})
export class VendorComponent implements OnInit {
  ticketsPerRelease: number | undefined = undefined;
  vendorId: number | undefined;
  isVendorsLoading: boolean = false;

  vendors: any[] = [];

  constructor(private host: AppComponent) { }

  async ngOnInit(): Promise<void> {
    this.loadVendor();
    setInterval(() => this.loadVendor(), 1000);
  }

  async addVendor(tickets_per_release?: number): Promise<void> {
    if (tickets_per_release === undefined || tickets_per_release < 0) {
      console.error('Tickets per release cannot be negative!');
      return;
    }
    this.host.client.post("/vendors", {
      tickets_per_release
    })
  }

  async vendorStatus(vendorId?: number): Promise<void> {
    this.host.client.post(`/vendors/${vendorId}`, {
    })
  }

  getVendorSize() {
    return this.vendors.length;
  }

  async loadVendor(): Promise<void> {
    try {
      const response = await this.host.client.get('/vendors');
      console.log(response.data);
      this.vendors = response.data;
    } catch (error) {
      this.vendors = [];
    }
  }

}
