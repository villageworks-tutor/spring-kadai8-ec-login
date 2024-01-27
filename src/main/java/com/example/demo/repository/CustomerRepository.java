package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	/**
	 * 電子メールアドレス検索
	 * SELECT * FROM customers WHERE email = ?
	 */
	Customer findByEmail(String email);

	/**
	 * 電子メールアドレスとパスワードの組合せ検索
	 * SELECT * FROM customers WHERE email = ? AND password = ?
	 */
	Customer findByEmailAndPassword(String email, String password);

}
