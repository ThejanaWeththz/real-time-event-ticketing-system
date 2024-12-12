import { Routes } from '@angular/router';
import { ConfigurationFormComponent } from './configuration-form/configuration-form.component';
import { TicketDisplayComponent } from './ticket-display/ticket-display.component';
import { ControlPanelComponent } from './control-panel/control-panel.component';
import { LogDisplayComponent } from './log-display/log-display.component';
import { HomeComponent } from './home/home.component';
import { VendorComponent } from './vendor/vendor.component';
import { CustomerComponent } from './customer/customer.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'configuration', component: ConfigurationFormComponent },
  { path: 'tickets', component: TicketDisplayComponent },
  { path: 'control', component: ControlPanelComponent },
  { path: 'logs', component: LogDisplayComponent },
  { path: 'vendors', component: VendorComponent },
  { path: 'customers', component: CustomerComponent },
];
