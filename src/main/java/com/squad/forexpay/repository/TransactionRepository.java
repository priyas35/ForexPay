package com.squad.forexpay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.forexpay.entity.Transaction;
import com.squad.forexpay.entity.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
