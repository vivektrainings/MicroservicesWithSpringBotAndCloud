package com.training.orderservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	@GetMapping("/hello")
	public String greet() {
		return "Hello from OrderService";
	}
}
