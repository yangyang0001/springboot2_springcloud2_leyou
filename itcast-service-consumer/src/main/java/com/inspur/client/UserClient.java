package com.inspur.client;

import com.inspur.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User: YANG
 * Date: 2019/8/6-20:10
 * Description: No Description
 */
@FeignClient(name = "itcast-service-provider", fallback = UserClientFallBack.class)
public interface UserClient {

	@GetMapping("/user/{id}")
	public User queryUserById(@PathVariable("id") Long id);
}
