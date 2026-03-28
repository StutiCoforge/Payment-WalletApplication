package com.coforge.services;

import java.math.BigDecimal;
import java.util.List;

import com.coforge.entities.Beneficiary;
import com.coforge.entities.Wallet;

public interface WalletService {

    Wallet createWallet(BigDecimal balance);
    
    Wallet getWallet();
    
    Wallet getWalletByCustomerId(long custId);
    
    Wallet addBeneficiary(long walletId, Beneficiary beneficiary);
    
    List<Beneficiary> getWalletBeneficiaries(long walletId);
    
    BigDecimal getBalance(Long walletId);

    Wallet credit(Long walletId, BigDecimal amount);
//    Wallet topUpWallet(BigDecimal amount, long bankAccountId);

    Wallet debit(Long walletId, BigDecimal amount);
}
