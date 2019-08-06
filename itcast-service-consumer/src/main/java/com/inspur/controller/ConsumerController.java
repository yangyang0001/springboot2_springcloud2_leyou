package com.inspur.controller;

import com.inspur.client.UserClient;
import com.inspur.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: YANG
 * Date: 2019/8/5-19:02
 * Description: No Description
 */
@RestController
@RequestMapping("/consumer")
//@DefaultProperties(defaultFallback = "queryUserByUserIdFallBack")   //全局的熔断方法处理
public class ConsumerController {

//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Autowired
//	private DiscoveryClient discoveryClient;
//
//	@GetMapping("{id}")
//	@HystrixCommand
//	public User queryUserByUserId(@PathVariable("id") Long id) {
//		List<ServiceInstance> instanceInfoList = discoveryClient.getInstances("ITCAST-SERVICE-PROVIDER");
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		instanceInfoList.forEach(instance -> {
//			System.out.println("instance.getClass -------------:" + instance.getClass());
//			System.out.println("instance.getHost --------------:" + instance.getHost());
//			System.out.println("instance.getPort --------------:" + instance.getPort());
//			System.out.println("instance.getServiceId ---------:" + instance.getServiceId());
//			System.out.println("instance.getUri ---------------:" + instance.getUri());
//			System.out.println("-----------------------------------------------------------------------------------");
//		});
//
//		System.out.println("ConsumerController queryUserByUserId invoke ...");
//		return restTemplate.getForObject("http://itcast-service-provider/user/" + id, User.class);
//	}
//
//	/**
//	 * 配置全局的也可以设置为局部的熔断方法
//	 * 全局的用@DefaultProperties(defaultFallBack="")
//	 */
//	public User queryUserByUserIdFallBack() {
//		User user = new User();
//		user.setName("熔断降级...,请稍等再继续重试...");
//		return user;
//	}


	@Autowired
	private UserClient userClient;

	@GetMapping("{id}")
	public User queryUserByUserId(@PathVariable("id") Long id) {
		System.out.println("ConsumerController queryUserByUserId invoke ...");
		User user = userClient.queryUserById(id);
		return user;
	}




}
