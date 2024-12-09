import { Routes } from '@angular/router';
import { ConfigurationFormComponent } from './configuration-form/configuration-form.component';
import { TicketDisplayComponent } from './ticket-display/ticket-display.component';

export const routes: Routes = [
  { path: '', component: ConfigurationFormComponent },
  { path: 'tickets', component: TicketDisplayComponent },
];
