package com.training.userservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.training.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByName(String name);
	
	@Query(value = "select * from user where adress =:add",nativeQuery = true)
	public List<User> getusersbyhyd(@Param("add") String add);

}
