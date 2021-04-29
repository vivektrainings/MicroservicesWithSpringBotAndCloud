package com.training.userservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.userservice.model.User;
import com.training.userservice.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	RestTemplate restemplate;
	
	@Autowired
	private UserService userservice;
	
	@Value("${order.serviceurl}")
	private String orderurl;

	public UserController() {
		
	}
	
	@HystrixCommand(fallbackMethod = "mymethode",commandKey = "demo")
	@GetMapping("/hello")
	public String hello() {
		//http://orderservice/hello
		String resp = restemplate.getForObject(orderurl+"/hello", String.class);
		return resp+ " Hitted from UserService";
	}

	
	
	public String mymethode() {
		return "FALL BACK HAPPENED at /hello";
	}
	
	@GetMapping("/getallusers/{pagesize}/{pageno}")
	public ResponseEntity<List<User>> getAllUsers(@PathVariable int pageno, @PathVariable int pagesize) {
		HttpHeaders  headers = new HttpHeaders();		
		Page<User> page = userservice.getAllUsers(pageno,pagesize);
		int pag =  page.getTotalPages();
		headers.add("totalpages",pag+"");		
		return new ResponseEntity<List<User>>(page.toList(),headers,HttpStatus.OK);
	}
	
	@GetMapping("/gethydusers/{add}")
	public ResponseEntity<List<User>> gethydUsers(@PathVariable String add) {
		return new ResponseEntity<List<User>>(userservice.getallhydusers(add),HttpStatus.OK);
	}
	
	@GetMapping("/getuser/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id){
		return new ResponseEntity<User>(userservice.getUserById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getbyname/{name}")
	public ResponseEntity<?> getUserByName(@PathVariable String name){
		return new ResponseEntity<User>(userservice.getUserByName(name),HttpStatus.OK);
	}
	
	
	@PostMapping("/adduser")
	public ResponseEntity<User> createUser(@RequestBody User u) {
		return new ResponseEntity<User>(userservice.createUser(u),HttpStatus.CREATED);
	}
	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<User> updateuser(@PathVariable Integer id,@RequestBody User u){
		return new ResponseEntity<User>(userservice.updateuser(id, u),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
		return new ResponseEntity<String>(userservice.deleteUser(id),HttpStatus.OK);
	}
	
	
}
