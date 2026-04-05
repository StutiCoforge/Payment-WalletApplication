package com.coforge.entities;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "customers")
public class Customer{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custId;
	
	@Value(value = "USER")
	@Setter(value = AccessLevel.NONE)
	@Builder.Default
	private String role="USER";
	@NotEmpty
	private String custName;
	@NotEmpty
	@Column(unique=true)
	@Pattern(regexp = "[6-9][0-9]{9}", message = "Mobile number must be exactly 10 digits")
	private String mobileNumber;
	@NotEmpty
	@Column(unique=true)
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email should be of valid format")
	private String email;
	
	@NotEmpty
	private String pwd;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "wallet_id", referencedColumnName = "walletId")
	private Wallet wallet;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankAccount> bankAccounts;
	public Customer(String custName, String mobileNumber, String email, String pwd)
	{
		super();
		this.custName = custName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.pwd = pwd;
		this.wallet = new Wallet(BigDecimal.ZERO,this);
	}

	public void addBankAccount(BankAccount bank)
	{
		this.bankAccounts.add(bank);
	}

	public void removeBankAccount(BankAccount bank)
	{
		this.bankAccounts.remove(bank);
	}
	
}