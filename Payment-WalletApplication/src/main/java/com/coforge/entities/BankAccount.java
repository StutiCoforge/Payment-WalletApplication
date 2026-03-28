package com.coforge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bankAccountId;
	@NotEmpty
	private String accountNo;
	@NotEmpty
	private String ifscCode;
	@NotEmpty
	private String bankname;
	@Min(value=0,message="Min balance is 0")
	private double balance;
	
	@ManyToOne
	@JoinColumn(name="customer_id",referencedColumnName = "custId")
	private Customer customer;

	public BankAccount(String accountNo, String ifscCode, String bankname, double balance, Customer customer) {
		super();
		this.accountNo = accountNo;
		this.ifscCode = ifscCode;
		this.bankname = bankname;
		this.balance = balance;
		this.customer = customer;
	}

	public BankAccount(@NotEmpty String accountNo, @NotEmpty String ifscCode, @NotEmpty String bankname,
			Customer customer) {
		super();
		this.accountNo = accountNo;
		this.ifscCode = ifscCode;
		this.bankname = bankname;
		this.customer = customer;
	}
	
	public BankAccount(@NotEmpty String accountNo, @NotEmpty String ifscCode, @NotEmpty String bankname) {
		super();
		this.accountNo = accountNo;
		this.ifscCode = ifscCode;
		this.bankname = bankname;
	}
	
	
	
	
	
}