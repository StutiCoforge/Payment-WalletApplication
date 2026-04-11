import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/customer.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink,FormsModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  customer: Customer | null = null;
  loading = true;
  error = '';

  resetPwdLoading = false;
  resetPwdForm = false;
  pwd = "";
  newPwd = "";
  resetPwdSuccessMessage = "";
  resetPwdError = "";

  constructor(private customerService: CustomerService) { }

  ngOnInit() {
    this.customerService.getDetails().subscribe({
      next: (data) => { this.customer = data; this.loading = false; },
      error: () => { this.error = 'Failed to load customer details.'; this.loading = false; }
    });
  }

  showResetPasswordForm() {
    this.resetPwdForm = !this.resetPwdForm;
    this.resetPwdLoading = false;
    this.pwd = "";
    this.newPwd = "";
    this.resetPwdSuccessMessage = "";
    this.resetPwdError = "";
  }

  resetPassword() {
    this.resetPwdLoading = true;
    this.resetPwdSuccessMessage = "";
    this.resetPwdError = "";
    this.customerService.resetPassword({ pwd: this.pwd, newPwd: this.newPwd }).subscribe({
      next: (data) => { this.resetPwdSuccessMessage = "Password Updated Successfully"; this.customer = data; this.resetPwdLoading = false; this.resetPwdForm = false },
      error: () => { this.resetPwdError = 'Invalid Current Password'; this.resetPwdLoading = false; }
    })
  }
}
