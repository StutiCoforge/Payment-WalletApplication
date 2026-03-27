package com.coforge.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.coforge.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
