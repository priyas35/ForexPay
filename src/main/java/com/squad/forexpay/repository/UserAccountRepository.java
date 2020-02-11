package com.squad.forexpay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.forexpay.entity.User;
import com.squad.forexpay.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{
	
	Optional<UserAccount> findByUser(User user);


}
