package com.mgt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgt.model.Transaction;
import com.mgt.model.User;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByTransactionId(String transactionId);

    List<Transaction> findByUser(User user);


}
