package com.training.userservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.training.userservice.dao.UserRepository;
import com.training.userservice.exception.UserNotFoundExceprion;
import com.training.userservice.model.User;

@Service
public class UserService {

	@Autowired
	UserRepository userepo;

	public Page<User> getAllUsers(int pageno,int pagesize) {
		
		Pageable pageable =  PageRequest.of(pageno,pagesize);
		
		Page<User> userpage = userepo.findAll(pageable);
		
		return userpage;
	}
	
	public User getUserById(int id){
		return userepo.findById(id).orElseThrow(()->new UserNotFoundExceprion("user not found"));
	}
	
	public User createUser(User u) {
		return userepo.save(u);
	}
	
	public User updateuser(Integer id, User u){
		
		User existinguser  = userepo.findById(id).orElse(null);
		
		if(existinguser == null)
			throw new UserNotFoundExceprion("cannot dind user id "+id);
		
		if(u.getName()!=null)
			existinguser.setName(u.getName());
		
		if(u.getAdress()!=null)
			existinguser.setAdress(u.getAdress());
		
		return userepo.save(existinguser);
	}
	
	public String deleteUser(Integer id) {
		userepo.deleteById(id);
		return "User deleted id :" + id;
	}
	
	
	public User getUserByName(String name) {
		return userepo.findByName(name);
	}
	
	public List<User> getallhydusers(String add){
		return userepo.getusersbyhyd(add);
	}
	
	public List<User> getSortedUser(){
		
		return (List<User>) userepo.findAll(Sort.by("name"));
	}
}
