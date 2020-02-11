package com.squad.forexpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.forexpay.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
