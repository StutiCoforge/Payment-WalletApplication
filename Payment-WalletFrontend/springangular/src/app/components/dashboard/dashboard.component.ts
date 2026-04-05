import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/customer.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  customer: Customer | null = null;
  loading = true;
  error = '';

  constructor(private customerService: CustomerService) {}

  ngOnInit() {
    this.customerService.getDetails().subscribe({
      next: (data) => { this.customer = data; this.loading = false; },
      error: () => { this.error = 'Failed to load customer details.'; this.loading = false; }
    });
  }
}
