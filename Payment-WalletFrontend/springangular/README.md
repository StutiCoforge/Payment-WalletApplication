# PayWallet - Angular 19 Application

## Setup & Run

### Prerequisites
- Node.js v24+
- Angular CLI v19: `npm install -g @angular/cli`

### Install & Start
```bash
cd wallet-app
npm install
ng serve
```

App runs at: http://localhost:4200

### API Base URL
The app points to `http://localhost:8080` by default.
Update the `base` URL in each service file if your backend runs elsewhere.

### Features
- Login / Signup with JWT auth
- Dashboard with customer info and wallet balance
- Wallet view
- Bank Accounts (add, delete, transfer to wallet)
- Beneficiaries (add, delete, send money)
- Bill Payments (create, list, delete)
- Transactions (list, filter by category/date)
