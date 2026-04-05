import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private base = 'http://localhost:8080';

  constructor(private http: HttpClient, private auth: AuthService) {}

  getDetails(): Observable<Customer> {
    return this.http.get<Customer>(`${this.base}/auth/customers/getDetails`, {
      headers: this.auth.getAuthHeaders()
    });
  }
}
