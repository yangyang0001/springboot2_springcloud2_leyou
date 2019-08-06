package com.inspur.config;

import com.inspur.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * User: YANG
 * Date: 2019/8/5-11:57
 * Description: No Description
 * 1.首先声明当前类是一个XML配置类
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Autowired
	private MyInterceptor myInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor)
				.addPathPatterns("/**");    //拦截任意级别的请求
	}
}
