package com.inspur.client;

import com.inspur.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User: YANG
 * Date: 2019/8/6-20:12
 * Description: No Description
 * 必须实现FeignClient的接口才能认识是熔断
 */
@Component
public class UserClientFallBack implements UserClient{

	public User queryUserById(@PathVariable("id") Long id) {
		User user = new User();
		user.setId(id);
		user.setUserName("服务降级熔断, 请稍后重试!");
		return user;
	}
}
