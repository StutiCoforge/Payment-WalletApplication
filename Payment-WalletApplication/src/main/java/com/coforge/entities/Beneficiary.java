package com.coforge.entities;
//import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ValueGenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table

public class Beneficiary {
	@Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	private long beneficiaryId;
	 private String beneficiaryName;

	 @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
	 private String mobileNumber;
	 
//	    @ManyToOne
//	    @JoinColumn(name = "walletId")
//	    private Wallet wallet;

//       @OneToOne
//       @JoinColumn(name = "bankAccountId")  
//       private BankAccount bankAccount;

	    
        
		public Beneficiary(String beneficiaryName, String mobileNumber) {
			super();
			this.beneficiaryName = beneficiaryName;
			this.mobileNumber = mobileNumber;
		}
	    
	 
}
	 

