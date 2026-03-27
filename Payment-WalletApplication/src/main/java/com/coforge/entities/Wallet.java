

package com.coforge.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @NotNull(message = "Wallet balance cannot be null")
    @Positive(message = "Wallet balance must be greater than zero")
    @Column(nullable = false)
    private BigDecimal balance;

    @OneToOne(mappedBy = "wallet")
    private Customer customer;

    @OneToMany(mappedBy = "wallet")
    private List<BankAccount> bankAccounts;

    @OneToMany(mappedBy = "wallet")
    private List<Beneficiary> beneficiary;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;
}



