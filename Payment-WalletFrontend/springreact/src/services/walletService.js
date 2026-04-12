import api from './api';

/* EXISTING */
export const getWallet = () =>
  api.get('/auth/wallets');

export const creditWallet = (walletId, payload) =>
  api.post(`/auth/wallet/credit/${walletId}`, payload);

export const debitWallet = (walletId, payload) =>
  api.post(`/auth/wallet/debit/${walletId}`, payload);

/* ✅ ADD THESE TWO (matches your BankAccountController) */

export const getBankAccounts = () =>
  api.get('/auth/bankAccount');

export const topUpFromBank = (bankAccountId,amount) =>
  api.post(
    `/auth/bankAccount/transferToWallet/${bankAccountId}`,
    null,
    {
      params: { amount }
    }
  );
  export const getBeneficiaries = () => api.get('/auth/beneficiary');