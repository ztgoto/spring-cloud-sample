package io.github.ztgoto.cloud.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
@Component
public class TestFilter extends ZuulFilter {

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		System.out.println(request.getRequestURL().toString());
//		ctx.setSendZuulResponse(false);
//        ctx.setResponseStatusCode(401);
		return "test run";
	}

	@Override
	public boolean shouldFilter() {
		
		return true;
	}

	@Override
	public int filterOrder() {
		
		return 0;
	}

	@Override
	public String filterType() {
		
		return "pre";
	}

}
