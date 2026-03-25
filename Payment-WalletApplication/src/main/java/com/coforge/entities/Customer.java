package com.coforge.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custId;
	private String custName;
	private String mobileNumber;
	private String pwd;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Wallet wallet;
	public Customer(String custName, String mobileNumber, String pwd)
	{
		super();
		this.custName = custName;
		this.mobileNumber = mobileNumber;
		this.pwd = pwd;
	}
}