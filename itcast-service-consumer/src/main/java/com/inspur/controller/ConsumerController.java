package com.inspur.controller;

import com.inspur.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * User: YANG
 * Date: 2019/8/5-19:02
 * Description: No Description
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("{id}")
	@HystrixCommand(fallbackMethod = "queryUserByUserIdFallBack")
	public User queryUserByUserId(@PathVariable("id") Long id) {
		List<ServiceInstance> instanceInfoList = discoveryClient.getInstances("ITCAST-SERVICE-PROVIDER");
		instanceInfoList.forEach(instance -> {
			System.out.println("instance.getClass -------------:" + instance.getClass());
			System.out.println("instance.getHost --------------:" + instance.getHost());
			System.out.println("instance.getPort --------------:" + instance.getPort());
			System.out.println("instance.getServiceId ---------:" + instance.getServiceId());
			System.out.println("instance.getUri ---------------:" + instance.getUri());
			System.out.println("-----------------------------------------------------------------------------------");
		});

		System.out.println("ConsumerController queryUserByUserId invoke ...");
		return restTemplate.getForObject("http://itcast-service-provider/user/" + id, User.class);
	}


	public User queryUserByUserIdFallBack(Long id) {
		User user = new User();
		user.setId(id);
		user.setName("熔断降级...,请稍等再继续重试...");
		return user;
	}
}
