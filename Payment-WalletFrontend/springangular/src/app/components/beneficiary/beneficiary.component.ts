import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { Beneficiary } from '../../models/beneficiary.model';

@Component({
  selector: 'app-beneficiary',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficiary.component.html'
})
export class BeneficiaryComponent implements OnInit {
  beneficiaries: Beneficiary[] = [];
  loading = true;
  error = '';
  successMsg = '';
  showForm = false;
  sendModal: Beneficiary | null = null;
  sendAmount = 0;

  form = { beneficiaryName: '', mobileNumber: '' };

  constructor(private beneficiaryService: BeneficiaryService) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.beneficiaryService.getAll().subscribe({
      next: (data) => { this.beneficiaries = data; this.loading = false; },
      error: () => { this.error = 'Failed to load beneficiaries.'; this.loading = false; }
    });
  }

  addBeneficiary() {
    if (!/^\d{10}$/.test(this.form.mobileNumber)) {
      this.error = 'Mobile number must be exactly 10 digits.';
      return;
    }
    this.beneficiaryService.add(this.form).subscribe({
      next: (msg) => {
        this.successMsg = "Beneficiary Added";
        this.showForm = false;
        this.form = { beneficiaryName: '', mobileNumber: '' };
        this.load();
      },
      error: () => { this.error = 'Failed to add beneficiary.'; }
    });
  }

  delete(id: number) {
    if (!confirm('Delete this beneficiary?')) return;
    this.beneficiaryService.delete(id).subscribe({
      next: (msg) => { this.successMsg = "Beneficiary Deleted"; this.load(); },
      error: () => { this.error = 'Failed to delete.'; }
    });
  }

  openSend(b: Beneficiary) {
    this.sendModal = b;
    this.sendAmount = 0;
  }

  doSend() {
    if (!this.sendModal) return;
    this.beneficiaryService.sendMoney(this.sendModal.mobileNumber, this.sendAmount).subscribe({
      next: (msg) => { this.successMsg = "Sent"; this.sendModal = null; },
      error: () => { this.error = 'Transfer failed.'; }
    });
  }
}
