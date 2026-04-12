import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WalletService } from '../../services/wallet.service';
import { Wallet } from '../../models/wallet.model';
import { BankAccount } from '../../models/bank-account.model';
import { BankAccountService } from '../../services/bank-account.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './wallet.component.html'
})
export class WalletComponent implements OnInit {
  wallet: Wallet | null = null;
  loading = true;
  error = '';
  showTopUpAccount=false;

  accounts: BankAccount[] = [];
  transferModal: BankAccount | null = null;
  transferAmount = 0;
  bankloading=false;
  bankError=""
  successMsg="";
  
  constructor(private walletService: WalletService,private bankService: BankAccountService) {}
  
  ngOnInit() {
    this.load();
  }

  load(){
    this.loading=true;
    this.error = '';
    this.walletService.getWallet().subscribe({
      next: (data) => { this.wallet = data; this.loading = false; },
      error: () => { this.error = 'Failed to load wallet.'; this.loading = false; }
    });
  }

  openTransfer(account: BankAccount) {
    this.transferModal = account;
    this.transferAmount = 0;
  }

  showBankAccounts(){
    this.showTopUpAccount = true;
    this.bankError="";
    this.loadBanks();
  }

  loadBanks(){
    this.bankloading = true;
    this.bankService.getAll().subscribe({
      next: (data) => { this.accounts = data; this.bankloading = false; },
      error: () => { this.bankError = 'Failed to load accounts.'; this.bankloading = false; }
    });
  }

  doTransfer() {
    if (!this.transferModal) return;
    this.bankService.transferToWallet(this.transferModal.bankAccountId, this.transferAmount).subscribe({
      next: (msg) => {
        this.successMsg = "Transfer Complete";
        this.transferModal = null;
        this.load();
        this.loadBanks();
        this.bankError="";
      },
      error: () => { this.bankError = 'Transfer failed. Insufficent Balance';this.transferModal = null;this.loadBanks();this.load(); }
    });
  }
}
