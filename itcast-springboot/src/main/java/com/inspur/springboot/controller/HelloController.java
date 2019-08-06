package com.inspur.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * User: YANG
 * Date: 2019/8/4-17:52
 * Description: No Description
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private DataSource dataSource;

	@GetMapping("/show")
	public String test() {
		return "hello springboot-2.0.6";
	}


}
