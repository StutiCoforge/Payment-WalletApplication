package com.coforge.entities;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custId;
	@NotEmpty
	private String custName;
	@NotEmpty
	private String mobileNumber;
	@NotEmpty
	private String email;
	private String pwd;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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
		this.wallet = new Wallet();
	}
