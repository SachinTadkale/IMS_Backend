package com.mgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgt.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByTransactionId(String transactionId);

}
