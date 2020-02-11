package com.squad.forexpay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.forexpay.entity.Account;
import com.squad.forexpay.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Optional<List<Transaction>> findByStatus(String transactionStatusPending);
	
	List<Transaction> findBySourceAccountNumber(Account sourceAccountNumber);

}
