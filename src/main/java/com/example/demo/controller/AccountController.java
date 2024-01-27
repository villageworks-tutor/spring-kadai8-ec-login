package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@Controller
public class AccountController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	// 会員登録画面表示
	@GetMapping("/account")
	public String create() {
		return "accountForm";
	}
	
	// 会員登録処理
	@PostMapping("/account")
	public String store(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password) {
		// リクエストパラメータを元にして登録する顧客をインスタンス化
		Customer customer = new Customer(name, address, tel, email, password);
		// 顧客のインスタンスを永続化
		customerRepository.save(customer);
		// ログイン画面表示にリダイレクト
		return "redirect:/";
	}
}
