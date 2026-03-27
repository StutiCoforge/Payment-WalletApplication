package com.coforge.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transaction {
	@Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
  private Long transactionId;
  @NotNull
  String transactionType;
  @NotNull
  String transactionStatus;
  double transactionAmount;
  LocalDate transactionDate;
  @ManyToOne
  @JoinColumn(name = "custId")
  Customer customer;
 
  
  
  String description;




  public Transaction(@NotNull String transactionType, double transactionAmount, Customer customer, String description) {
	super();
	this.transactionType = transactionType;
	this.transactionAmount = transactionAmount;
	this.customer = customer;
	this.description = description;
  }
  

}