import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WalletService } from '../../services/wallet.service';
import { Wallet } from '../../models/wallet.model';

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wallet.component.html'
})
export class WalletComponent implements OnInit {
  wallet: Wallet | null = null;
  loading = true;
  error = '';

  constructor(private walletService: WalletService) {}

  ngOnInit() {
    this.walletService.getWallet().subscribe({
      next: (data) => { this.wallet = data; this.loading = false; },
      error: () => { this.error = 'Failed to load wallet.'; this.loading = false; }
    });
  }
}
