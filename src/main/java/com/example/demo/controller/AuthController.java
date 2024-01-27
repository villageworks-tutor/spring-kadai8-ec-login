package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {
	
	@Autowired // 13.1
	HttpSession session;
	
	@Autowired // seq:12.1
	Account account;
	
	@Autowired
	CustomerRepository customerRepository;
	
	// ログイン画面表示
	@GetMapping("/")	// seq:11.1
	public String index() {
		return "login"; // seq:11.2
	}
	
	// ログイン実行
	@PostMapping("/login") // seq.12.2改
	public String login(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		// リクエストパラメータをもとにデータベースから顧客を取得
		Customer customer = customerRepository.findByEmailAndPassword(email, password);
		// ユーザ認証
		if (customer == null) {
			// 顧客を取得できなかった場合：ユーザ認証失敗としてログイン画面に遷移
			model.addAttribute("error", "メールアドレスとパスワードが一致しませんでした。");
			return "login";
		} else {
			// 顧客を取得できた場合：ユーザ認証成功として商品一覧画面表示にリダイレクト
			account.setId(customer.getId());
			account.setName(customer.getName()); // seq.12.3改
			// 商品一覧画面表示にリダイレクト
			return "redirect:/items"; // seq.12.4
		}
	}
	
	// ログアウト処理
	@GetMapping("/logout")	  // seq:13.2
	public String logout() {
		session.invalidate(); // seq:13.3
		return "login"; 	  // seq:13.4
	}
	
}
