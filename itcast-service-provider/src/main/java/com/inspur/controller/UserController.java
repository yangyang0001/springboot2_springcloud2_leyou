package com.inspur.controller;

import com.inspur.entity.User;
import com.inspur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: YANG
 * Date: 2019/8/5-18:20
 * Description: No Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("{id}")
	public User queryUserById(@PathVariable("id") Long id) {
		System.out.println("UserController queryUserById invoke ------------------------------------");
		User user = userService.queryUserByUserId(id);
		return user;
	}
}
