import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

interface NavItem {
  label: string;
  route: string;
  icon: string;
}

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './layout.component.html'
})
export class LayoutComponent {
  sidebarOpen = false;

  navItems: NavItem[] = [
    { label: 'Dashboard', route: '/dashboard', icon: 'grid' },
    { label: 'Wallet', route: '/wallet', icon: 'wallet' },
    { label: 'Bank Accounts', route: '/bank-account', icon: 'bank' },
    { label: 'Beneficiaries', route: '/beneficiary', icon: 'users' },
    { label: 'Bill Payments', route: '/bill-payment', icon: 'receipt' },
    { label: 'Transactions', route: '/transactions', icon: 'chart' },
  ];

  constructor(private auth: AuthService, private router: Router) {}

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
