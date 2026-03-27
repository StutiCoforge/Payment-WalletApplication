package com.coforge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coforge.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
