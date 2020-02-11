package com.squad.forexpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.forexpay.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String>{

}
