package com.inspur.controller;

import com.inspur.entity.User;
import com.inspur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: YANG
 * Date: 2019/8/4-23:01
 * Description: No Description
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("{id}")
	@ResponseBody
	public User queryUserByUserId(@PathVariable("id") Long id) {
		User user = userService.queryUserByUserId(id);
		return user;
	}

	@GetMapping("/queryUserList")
	public String queryUserList(Model model) {
		List<User> userList = userService.queryUserList();
		model.addAttribute("users", userList);
		return "userList";
	}



	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "UserController";
	}
}
