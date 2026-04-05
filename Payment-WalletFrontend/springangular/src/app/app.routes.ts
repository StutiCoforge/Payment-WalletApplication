import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { LoginComponent } from './components/auth/login/login.component';
import { SignupComponent } from './components/auth/signup/signup.component';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { LayoutComponent } from './components/layout/layout.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { WalletComponent } from './components/wallet/wallet.component';
import { BankAccountComponent } from './components/bank-account/bank-account.component';
import { BillPaymentComponent } from './components/bill-payment/bill-payment.component';
import { BeneficiaryComponent } from './components/beneficiary/beneficiary.component';
import { TransactionComponent } from './components/transaction/transaction.component';
import { AdminLayoutComponent } from './components/admin/admin-layout/admin-layout.component';
import { AdminOverviewComponent } from './components/admin/admin-overview/admin-overview.component';
import { AdminCustomersComponent } from './components/admin/admin-customers/admin-customers.component';
import { AdminWalletsComponent } from './components/admin/admin-wallets/admin-wallets.component';
import { AdminBankAccountsComponent } from './components/admin/admin-bank-accounts/admin-bank-accounts.component';
import { AdminBeneficiariesComponent } from './components/admin/admin-beneficiaries/admin-beneficiaries.component';
import { AdminBillPaymentsComponent } from './components/admin/admin-bill-payments/admin-bill-payments.component';
import { AdminTransactionsComponent } from './components/admin/admin-transactions/admin-transactions.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    component:LoginComponent
    // loadComponent: () => import('./components/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'signup',
    component:SignupComponent
    // loadComponent: () => import('./components/auth/signup/signup.component').then(m => m.SignupComponent)
  },
  {
    path: 'admin-login',
    component:AdminLoginComponent
    // loadComponent: () => import('./components/admin/admin-login/admin-login.component').then(m => m.AdminLoginComponent)
  },
  {
    path: '',
    canActivate: [authGuard],
    component:LayoutComponent,
    // loadComponent: () => import('./components/layout/layout.component').then(m => m.LayoutComponent),
    children: [
      {
        path: 'dashboard',
        component:DashboardComponent
        // loadComponent: () => import('./components/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'wallet',
        component:WalletComponent
        // loadComponent: () => import('./components/wallet/wallet.component').then(m => m.WalletComponent)
      },
      {
        path: 'bank-account',
        component:BankAccountComponent
        // loadComponent: () => import('./components/bank-account/bank-account.component').then(m => m.BankAccountComponent)
      },
      {
        path: 'beneficiary',
        component: BeneficiaryComponent
        // loadComponent: () => import('./components/beneficiary/beneficiary.component').then(m => m.BeneficiaryComponent)
      },
      {
        path: 'bill-payment',
        component:BillPaymentComponent
        // loadComponent: () => import('./components/bill-payment/bill-payment.component').then(m => m.BillPaymentComponent)
      },
      {
        path: 'transactions',
        component: TransactionComponent
        // loadComponent: () => import('./components/transaction/transaction.component').then(m => m.TransactionComponent)
      }
    ]
  },
  {
    path: 'admin',
    canActivate: [adminGuard],
    component:AdminLayoutComponent,
    // loadComponent: () => import('./components/admin/admin-layout/admin-layout.component').then(m => m.AdminLayoutComponent),
    children: [
      {
        path: '',
        component: AdminOverviewComponent
        // loadComponent: () => import('./components/admin/admin-overview/admin-overview.component').then(m => m.AdminOverviewComponent)
      },
      {
        path: 'customers',
        component: AdminCustomersComponent
        // loadComponent: () => import('./components/admin/admin-customers/admin-customers.component').then(m => m.AdminCustomersComponent)
      },
      {
        path: 'wallets',
        component: AdminWalletsComponent
        // loadComponent: () => import('./components/admin/admin-wallets/admin-wallets.component').then(m => m.AdminWalletsComponent)
      },
      {
        path: 'bank-accounts',
        component:AdminBankAccountsComponent
        // loadComponent: () => import('./components/admin/admin-bank-accounts/admin-bank-accounts.component').then(m => m.AdminBankAccountsComponent)
      },
      {
        path: 'beneficiaries',
        component: AdminBeneficiariesComponent
        // loadComponent: () => import('./components/admin/admin-beneficiaries/admin-beneficiaries.component').then(m => m.AdminBeneficiariesComponent)
      },
      {
        path: 'bill-payments',
        component: AdminBillPaymentsComponent
        // loadComponent: () => import('./components/admin/admin-bill-payments/admin-bill-payments.component').then(m => m.AdminBillPaymentsComponent)
      },
      {
        path: 'transactions',
        component: AdminTransactionsComponent
        // loadComponent: () => import('./components/admin/admin-transactions/admin-transactions.component').then(m => m.AdminTransactionsComponent)
      }
    ]
  },

  { path: '**', redirectTo: 'login' }
];
