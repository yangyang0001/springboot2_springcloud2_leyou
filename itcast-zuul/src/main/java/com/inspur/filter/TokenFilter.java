package com.inspur.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * User: YANG
 * Date: 2019/7/21-17:30
 * Description: No Description
 *
 * 网关中自定义Filter
 *
 */
@Component
public class TokenFilter extends ZuulFilter {

	@Value("${server.port}")
	private String serverPort;

	//过滤器的类型,这里pre 表示请求之前的过滤器
	@Override
	public String filterType() {
		return "pre";
	}

	//存在多个Filter的使用,这个是用来指定filter的调用顺序的! 数值越小 优先级越高
	@Override
	public int filterOrder() {
		return 0;
	}

	//是否要进行过滤,这里返回为true,表示要进行过滤,执行run() 方法
	@Override
	public boolean shouldFilter() {
		return true;
	}

	//
	@Override
	public Object run() throws ZuulException {

		//1.根据请求的上下文来获取Request对象
		RequestContext requestContext = RequestContext.getCurrentContext();

		//2.获取Request
		HttpServletRequest request = requestContext.getRequest();

		//3.获取Request对象中的信息
		String userToken = request.getParameter("userToken");

		if(StringUtils.isBlank(userToken)){
			//如果为空 不转发请求
			requestContext.getResponse().setContentType("text/html;charset=UTF-8");
			requestContext.setSendZuulResponse(false);
			requestContext.setResponseBody("userToken is null 身份未认证...");
			requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
			return null;
		}

		System.out.println("Zuul server port is :" + serverPort + ", userToken is :" + userToken);
		//如果userToken 不为空则调用服务处理业务,这里不用自己实现,框架自己帮助我们实现了!
		return null;

	}
}
