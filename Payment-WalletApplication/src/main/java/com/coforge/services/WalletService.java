package com.coforge.services;

import java.math.BigDecimal;
import com.coforge.entities.Wallet;

public interface WalletService {

    Wallet createWallet(BigDecimal balance);

    BigDecimal getBalance(Long walletId);

    Wallet credit(Long walletId, BigDecimal amount);

    Wallet debit(Long walletId, BigDecimal amount);
}
