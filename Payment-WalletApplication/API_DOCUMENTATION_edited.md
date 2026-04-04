# Payment-WalletApplication API Documentation

## Notes
- `@auth` endpoints are intended for authenticated customer access.
- `@admin` endpoints are intended for admin-level access.
- Some controllers return entity classes directly while others use DTOs.
- Validation rules are taken from JSR-380 annotations found in DTO/entity classes.

---

## 1. Customer APIs

### 1.1 Signup
- URL: `POST /customers/signup`
- Description: Register a new customer and return a JWT token.
- Request Body:
  ```json
  {
    "custName": "John Doe",
    "mobileNumber": "9876543210",
    "email": "john.doe@example.com",
    "pwd": "SecurePass123"
  }
  ```
- Fields:
  - `custName` (string, required)
  - `mobileNumber` (string, required, exactly 10 digits)
  - `email` (string, required, valid email format)
  - `pwd` (string, required)
- Response Body:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsIn...",
    "email": "john.doe@example.com"
  }
  ```
- Validation:
  - `mobileNumber` must be 10 digits
  - `email` must match valid email pattern

---

### 1.2 Login
- URL: `POST /customers/login`
- Description: Authenticate a customer and return a JWT token.
- Request Body:
  ```json
  {
    "email": "john.doe@example.com",
    "pwd": "SecurePass123"
  }
  ```
- Fields:
  - `email` (string, required)
  - `pwd` (string, required)
- Response Body:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsIn...",
    "email": "john.doe@example.com"
  }
  ```

---

### 1.3 Get current customer details
- URL: `GET /auth/customers/getDetails`
- Description: Return details of the authenticated customer.
- Request Body: none
- Response Body:
  ```json
  {
    "custId": 1,
    "custName": "John Doe",
    "mobileNumber": "9876543210",
    "email": "john.doe@example.com",
    "wallet": {
      "walletId": 10,
      "balance": 250.75,
      "beneficiary": []
    }
  }
  ```

---

### 1.4 Get all customers (admin)
- URL: `GET /admin/customers`
- Description: Return all customers.
- Response Body:
  ```json
  [
    {
      "custId": 1,
      "custName": "John Doe",
      "mobileNumber": "9876543210",
      "email": "john.doe@example.com",
      "wallet": { "walletId": 10, "balance": 250.75, "beneficiary": [] }
    }
  ]
  ```

---

### 1.5 Get customer by ID (admin)
- URL: `GET /admin/customers/{customerId}`
- Description: Return a specific customer.
- Path Parameter:
  - `customerId` (long)
- Response Body:
  ```json
  {
    "custId": 1,
    "custName": "John Doe",
    "mobileNumber": "9876543210",
    "email": "john.doe@example.com",
    "wallet": { "walletId": 10, "balance": 250.75, "beneficiary": [] }
  }
  ```

---

### 1.6 Update customer (admin)
- URL: `PUT /admin/customers/{customerId}`
- Description: Update a customer record.
- Request Body example:
  ```json
  {
    "custName": "John Doe Jr.",
    "mobileNumber": "9876543210",
    "email": "john.doe@example.com",
  }
  ```
- Path Parameter:
  - `customerId` (long)
- Response Body:
  ```json
  {
    "custId": 1,
    "custName": "John Doe Jr.",
    "mobileNumber": "9876543210",
    "email": "john.doe@example.com",
    "wallet": { "walletId": 10, "balance": 300.00, "beneficiary": [] }
  }
  ```

---

### 1.7 Delete customer (admin)
- URL: `DELETE /admin/customers/{customerId}`
- Description: Remove a customer.
- Response Body:
  ```json
  "Customer Deleted Successfully"
  ```

---

### 1.8 Search customers (admin)
- URL: `GET /admin/customers/search?query={query}`
- Description: Search customers by query string.
- Query Parameter:
  - `query` (string)
- Response Body:
  ```json
  [
    {
      "custId": 1,
      "custName": "John Doe",
      "mobileNumber": "9876543210",
      "email": "john.doe@example.com",
      "wallet": { "walletId": 10, "balance": 250.75, "beneficiary": [] }
    }
  ]
  ```

---

## 2. Wallet APIs

### 2.1 Get wallet
- URL: `GET /auth/wallets`
- Description: Return the authenticated customer's wallet.
- Response Body:
  ```json
  {
    "walletId": 10,
    "balance": 250.75,
    "beneficiary": [
      {
        "beneficiaryId": 5,
        "beneficiaryName": "Alice",
        "mobileNumber": "9123456789"
      }
    ]
  }
  ```

---

<!-- ### 2.2 Create wallet
- URL: `POST /auth/wallets`
- Description: Create a wallet for the authenticated customer.
- Request Body:
  ```json
  {
    "balance": 100.00
  }
  ```
- Fields:
  - `balance` (number, required, positive)
- Response Body:
  ```json
  {
    "walletId": 11,
    "balance": 100.00,
    "customer": {
      "custId": 1,
      "custName": "John Doe",
      "email": "john.doe@example.com",
      "mobileNumber": "9876543210"
    },
    "beneficiary": []
  }
  ```

--- -->

### 2.2 Get wallet balance
- URL: `GET /auth/wallets/{walletId}/balance`
- Description: Fetch wallet balance only.
- Response Body:
  ```json
  {
    "balance": 250.75
  }
  ```

---

<!-- ### 2.3 Credit wallet
- URL: `POST /auth/wallets/{walletId}/credit`
- Description: Add funds to a wallet.
- Request Body:
  ```json
  {
    "amount": 50.00
  }
  ```
- Fields:
  - `amount` (number, required, positive)
- Response Body:
  ```json
  {
    "walletId": 10,
    "balance": 300.75,
    "customer": { ... },
    "beneficiary": [...]
  }
  ```

---

### 2.5 Debit wallet
- URL: `POST /auth/wallets/{walletId}/debit`
- Description: Deduct funds from a wallet.
- Request Body:
  ```json
  {
    "amount": 30.00
  }
  ```
- Fields:
  - `amount` (number, required, positive)
- Response Body:
  ```json
  {
    "walletId": 10,
    "balance": 220.75,
    "customer": { ... },
    "beneficiary": [...]
  }
  ``` -->

---

## 2.6 Admin Wallet APIs

### 2.6.1 Get all wallets
- URL: `GET /admin/wallets`
- Description: Return all wallets.
- Response Body:
  ```json
  [
    {
      "walletId": 10,
      "balance": 250.75,
      "beneficiary": [...]
    }
  ]
  ```

---

### 2.6.2 Get wallet by ID
- URL: `GET /admin/wallets/get/{walletId}`
- Response Body:
  ```json
  {
    "walletId": 10,
    "balance": 250.75,
    "beneficiary": [...]
  }
  ```

---

### 2.6.3 Credit wallet (admin)
- URL: `POST /admin/wallets/{walletId}/credit`
- Request Body:
  ```json
  {
    "amount": 100.00
  }
  ```
- Response Body: same structure as wallet entity.

---

### 2.6.4 Debit wallet (admin)
- URL: `POST /admin/wallets/{walletId}/debit`
- Request Body:
  ```json
  {
    "amount": 20.00
  }
  ```

---

### 2.6.5 Search wallets
- URL: `GET /admin/wallets/search?query={query}`
- Response Body: list of `WalletDto`.

---

## 3. Bank Account APIs

### 3.1 Get authenticated customer bank accounts
- URL: `GET /auth/bankAccount`
- Response Body:
  ```json
  [
    {
      "bankAccountId": 5,
      "accountNo": "123456789012",
      "ifscCode": "SBIN0001234",
      "bankname": "State Bank",
      "balance": 5000.00
    }
  ]
  ```

---

### 3.2 Get bank account by ID
- URL: `GET /auth/bankAccount/{bankAccountId}`
- Response Body:
  ```json
  {
    "bankAccountId": 5,
    "accountNo": "123456789012",
    "ifscCode": "SBIN0001234",
    "bankname": "State Bank",
    "balance": 5000.00
  }
  ```

---

### 3.3 Add bank account
- URL: `POST /auth/bankAccount/add`
- Request Body:
  ```json
  {
    "accountNo": "123456789012",
    "ifscCode": "SBIN0001234",
    "bankname": "State Bank",
    "balance": 1000.00
  }
  ```
- Fields:
  - `accountNo` (string, required)
  - `ifscCode` (string, required)
  - `bankname` (string, required)
  - `balance` (number, optional, minimum 0)
- Response Body:
  ```json
  {
    "bankAccountId": 5,
    "accountNo": "123456789012",
    "ifscCode": "SBIN0001234",
    "bankname": "State Bank",
    "balance": 1000.00
  }
  ```

---

### 3.4 Update bank account
- URL: `PUT /auth/bankAccount/{bankAccountId}`
- Request Body:
  ```json
  {
    "accountNo": "123456789012",
    "ifscCode": "SBIN0001234",
    "bankname": "State Bank",
    "balance": 1500.00
  }
  ```
- Response Body: updated `BankAccountDto`.

---

### 3.5 Delete bank account
- URL: `DELETE /auth/bankAccount/{bankAccountId}`
- Response Body:
  ```json
  "Bank Account Deleted"
  ```

---

### 3.6 Transfer bank account balance to wallet
- URL: `POST /auth/bankAccount/transferToWallet/{bankAccountId}?amount=100.00`
- Description: Move funds from a bank account into the wallet.
- Query Parameter:
  - `amount` (number)
- Response Body:
  ```json
  "100.0 Rs. Transferred to wallet"
  ```

---

## 3.7 Admin Bank Account APIs

### 3.7.1 Get all bank accounts
- URL: `GET /admin/bankAccount`
- Response Body: list of `BankAccountDto`.

---

### 3.7.2 Add bank account (admin)
- URL: `POST /admin/bankAccount/add`
- Request Body:
  ```json
  {
    "accountNo": "123456789012",
    "ifscCode": "SBIN0001234",
    "bankname": "State Bank",
    "balance": 1000.00,
    "custId": 1
  }
  ```
- Response Body: `BankAccountDto`.

---

### 3.7.3 Update bank account (admin)
- URL: `PUT /admin/bankAccount/{bankAccountId}`
- Request Body:
  ```json
  {
    "accountNo": "123456789012",
    "ifscCode": "SBIN0001234",
    "bankname": "State Bank",
    "balance": 1500.00
  }
  ```

---

### 3.7.4 Delete bank account (admin)
- URL: `DELETE /admin/bankAccount/{bankAccountId}`

---

<!-- ### 3.7.5 Transfer money from bank account to wallet (admin)
- URL: `POST /admin/bankAccount/transferToWallet/{bankAccountId}?amount=100.00`

--- -->

### 3.7.5 Search bank accounts
- URL: `GET /admin/bankAccount/search?query={query}`

---

## 4. Beneficiary APIs

### 4.1 List beneficiaries
- URL: `GET /auth/beneficiary`
- Response Body:
  ```json
  [
    {
      "beneficiaryId": 7,
      "beneficiaryName": "Alice",
      "mobileNumber": "9123456789"
    }
  ]
  ```

---

### 4.2 Add beneficiary
- URL: `POST /auth/beneficiary`
- Request Body:
  ```json
  {
    "beneficiaryName": "Alice",
    "mobileNumber": "9123456789"
  }
  ```
- Validation:
  - `mobileNumber` must be exactly 10 digits
- Response Body:
  ```json
  "Beneficiary added successfully"
  ```

---

### 4.3 Get beneficiary by ID
- URL: `GET /auth/beneficiary/{cid}`
- Response Body:
  ```json
  {
    "beneficiaryId": 7,
    "beneficiaryName": "Alice",
    "mobileNumber": "9123456789"
  }
  ```

---

### 4.4 Delete beneficiary
- URL: `DELETE /auth/beneficiary/{bid}`
- Response Body:
  ```json
  "Beneficiary deleted successfully"
  ```

---

### 4.5 Get beneficiary by mobile
- URL: `GET /auth/mobile/{mobileNumber}`
- Response Body:
  ```json
  {
    "beneficiaryId": 7,
    "beneficiaryName": "Alice",
    "mobileNumber": "9123456789"
  }
  ```

---

### 4.6 Get beneficiary by name
- URL: `GET /auth/beneficiary/name/{name}`
- Response Body: same as above.

---

### 4.7 Send money by beneficiary mobile
- URL: `POST /auth/beneficiary/mobile/sendMoney/{mobileNumber}?amount=100.00`
- Description: Send funds to a beneficiary by mobile number.
- Query Parameter:
  - `amount` (string, parsed as number)
- Response Body:
  ```json
  "100.0 Rs. transferred"
  ```

---

## 4.8 Admin Beneficiary APIs

### 4.8.1 List all beneficiaries
- URL: `GET /admin/beneficiaries`
- Response Body: list of `Beneficiary` objects.

---

### 4.8.2 Get beneficiary by ID
- URL: `GET /admin/beneficiaries/{beneficiaryId}`

---

### 4.8.3 Get beneficiaries by customer
- URL: `GET /admin/beneficiaries/customer/{customerId}`

---

### 4.8.4 Delete beneficiary (admin)
- URL: `DELETE /admin/beneficiaries/{beneficiaryId}`

---

## 5. Bill Payment APIs

### 5.1 Create bill payment
- URL: `POST /auth/billPayments/create`
- Request Body:
  ```json
  {
    "amount": 350.00,
    "billType": "ELECTRICITY",
    "billData": {
      "consumerNumber": "ABCD1234",
      "billingMonth": "2026-03"
    }
  }
  ```
- Fields:
  - `amount` (number, required; minimum 1)
  - `billType` (enum, required)
  - `billData` (object, required)
- Allowed `billType` values:
  - `ELECTRICITY`
  - `MOBILE_RECHARGE`
  - `GAS_BOOKING`
- Response Body:
  ```json
  {
    "status": "success",
    "message": "Bill Paid successfully",
    "timestamp": 1712350000000
  }
  ```

---

### 5.2 Get all bill payments
- URL: `GET /auth/billPayments/getAll`
- Response Body:
  ```json
  [
    {
      "billId": 12,
      "paymentDate": "2026-04-01T10:15:30",
      "amount": 350.0,
      "billType": "ELECTRICITY",
      "billData": {
        "consumerNumber": "ABCD1234"
      }
    }
  ]
  ```

---

### 5.3 Get bill payments between dates
- URL: `GET /auth/billPayments/getBetween?start=2026-01-01&end=2026-03-31`
- Response Body: list of `BillPaymentResponseDto`.

---

### 5.4 Get bill payments by type
- URL: `GET /auth/billPayments/getByType/{billType}`
- Path Parameter:
  - `billType` = `ELECTRICITY`, `MOBILE_RECHARGE`, or `GAS_BOOKING`

---

### 5.5 Get bill payment by ID
- URL: `GET /auth/billPayments/get/{billId}`

---

### 5.6 Delete bill payment
- URL: `DELETE /auth/billPayments/delete/{billId}`
- Response Body:
  ```json
  "Bill Deleted Successfully"
  ```

---

## 5.7 Admin Bill Payment APIs

### 5.7.1 Get all bill payments
- URL: `GET /admin/billPayments/getAll`

---

### 5.7.2 Get bill payment by ID
- URL: `GET /admin/billPayments/get/{billId}`

---

### 5.7.3 Get bills between dates
- URL: `GET /admin/billPayments/getBetween?start=2026-01-01&end=2026-03-31`

---

### 5.7.4 Get bills by type
- URL: `GET /admin/billPayments/getByType/{billType}`

---

### 5.7.5 Search bills
- URL: `GET /admin/billPayments/search?query={query}`

---

### 5.7.6 Delete bill payment
- URL: `DELETE /admin/billPayments/delete/{billId}`

---

## 6. Transaction APIs

<!-- ### 6.1 Add transaction
- URL: `POST /auth/transactions`
- Request Body example:
  ```json
  {
    "transactionType": "DEBIT",
    "transactionStatus": "SUCCESS",
    "transactionAmount": 150.00,
    "transactionDate": "2026-04-05",
    "description": "Utility payment",
    "category": "BILL_PAYMENT",
    "subCategory": "ELECTRICITY",
    "customer": {
      "custId": 1
    }
  }
  ```
- Fields:
  - `transactionType` (string, required)
  - `transactionStatus` (string, required)
  - `transactionAmount` (number)
  - `transactionDate` (date string)
  - `description` (string)
  - `category` (enum)
  - `subCategory` (enum)
  - `customer` (object with `custId`)
- Response Body: `Transaction` entity with generated `transactionId`. -->

<!-- ---

### 6.2 Update transaction
- URL: `PUT /auth/transactions`
- Request Body: same structure as add transaction.
- Description: Update an existing transaction, including status.
- Response Body: updated `Transaction`.

--- -->

### 6.1 Get all customer transactions
- URL: `GET /auth/transactions/all`
- Response Body:
  ```json
  [
    {
      "transactionId": 20,
      "transactionType": "DEBIT",
      "transactionStatus": "SUCCESS",
      "transactionAmount": 150.0,
      "customerId": 1,
      "category": "BILL_PAYMENT",
      "subCategory": "ELECTRICITY",
      "description": "Utility payment",
      "transactionDate": "2026-04-05"
    }
  ]
  ```

---

### 6.2 Get transactions by date range
- URL: `GET /auth/transactions/dates?from=2026-04-01&to=2026-04-30`

---

### 6.3 Get transactions by category
- URL: `GET /auth/transactions/category/{category}`
- Allowed `category` values:
  - `BENEFICIARY_TRANSFER`
  - `BILL_PAYMENT`
  - `WALLET_TOP_UP`

---

### 6.4 Get transactions by subcategory
- URL: `GET /auth/transactions/subcategory/{sub}`
- Allowed `sub` values:
  - `ELECTRICITY`
  - `MOBILE_RECHARGE`
  - `GAS`
  - `WATER`
  - `BROADBAND`
  - `NONE`

---

### 6.5 Get transactions by month
- URL: `GET /auth/transactions/month?month=4&year=2026`

---

### 6.6 Delete transaction
- URL: `DELETE /auth/transactions/{id}`
- Response Body:
  ```json
  "Transaction deleted successfully"
  ```

---

## 6.9 Admin Transaction APIs

### 6.9.1 Get all transactions
- URL: `GET /admin/transactions/all`

---

### 6.9.2 Get admin transactions by date
- URL: `GET /admin/transactions/dates?from=2026-04-01&to=2026-04-30`

---

### 6.9.3 Get admin transactions by category
- URL: `GET /admin/transactions/category/{category}`

---

### 6.9.4 Get admin transactions by subcategory
- URL: `GET /admin/transactions/subcategory/{sub}`

---

### 6.9.5 Get admin transactions by month
- URL: `GET /admin/transactions/month?month=4&year=2026`

---

## 7. Enums and Allowed Values

### BillType
- `ELECTRICITY`
- `MOBILE_RECHARGE`
- `GAS_BOOKING`

### TransactionCategory
- `BENEFICIARY_TRANSFER`
- `BILL_PAYMENT`
- `WALLET_TOP_UP`

### TransactionSubCategory
- `ELECTRICITY`
- `MOBILE_RECHARGE`
- `GAS`
- `WATER`
- `BROADBAND`
- `NONE`

---

## 8. Payload Reference Schemas

### Customer entity fields
- `custId` (long, generated)
- `custName` (string)
- `mobileNumber` (string, 10 digits)
- `email` (string, valid email)
- `pwd` (string)
- `role` set to `USER`
- `wallet` (nested wallet object)
- `bankAccounts` (optional list)

### Wallet objects
- `walletId` (long)
- `balance` (decimal, non-null, positive)
- `beneficiary` (list of beneficiary objects)

### BankAccountDto
- `bankAccountId`
- `accountNo`
- `ifscCode`
- `bankname`
- `balance` (min 0)

### Beneficiary object
- `beneficiaryId`
- `beneficiaryName`
- `mobileNumber` (10 digits)

### BillPaymentRequestDto
- `amount` (double, min 1)
- `billType` (enum)
- `billData` (JSON map)

### Transaction object
- `transactionId`
- `transactionType`
- `transactionStatus`
- `transactionAmount`
- `transactionDate`
- `customer` (nested object)
- `description`
- `category`
- `subCategory`
