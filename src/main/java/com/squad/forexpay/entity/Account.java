package com.squad.forexpay.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Account {
   @Id
   private Long accountNumber;
   private String bankName;
   private String branchName;
   private Double balance;
   @ManyToOne
   @JoinColumn(name = "currency_code")
   private Currency currency;
   

}
