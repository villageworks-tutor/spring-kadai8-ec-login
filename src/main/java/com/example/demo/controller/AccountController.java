package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		// 入力値チェック
		List<String> errors = new ArrayList<String>();
		if (name.isEmpty()) {
			// 名前チェック：必須入力チェック
			errors.add("名前は必須です。");
		}
		if (address.isEmpty()) {
			// 住所チェック：必須入力チェック
			errors.add("住所は必須です。");
		}
		if (tel.isEmpty()) {
			// 電話番号チェック：必須入力チェック
			errors.add("電話番号は必須です。");
		}
		if (email.isEmpty()) {
			// 電子メールアドレスチェック：必須入力チェック
			errors.add("メールアドレスは必須です。");
		} else {
			// 電子メールアドレスの登録済みチェック
			Customer existedCustomer = customerRepository.findByEmail(email);
			if (existedCustomer != null) {
				errors.add("登録済みのメールアドレスです。");
			}
		}
		if (password.isEmpty()) {
			errors.add("パスワードは必須です。");
		}
		
		// 入力値エラーがある場合
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			model.addAttribute("name", name);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			return "accountForm";
		}
		
		// リクエストパラメータを元にして登録する顧客をインスタンス化
		Customer customer = new Customer(name, address, tel, email, password);
		// 顧客のインスタンスを永続化
		customerRepository.save(customer);
		// ログイン画面表示にリダイレクト
		return "redirect:/";
	}
}
