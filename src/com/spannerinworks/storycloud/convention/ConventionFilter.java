package com.spannerinworks.storycloud.convention;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConventionFilter implements Filter {
	private Router router;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String controllerPackage = filterConfig.getInitParameter("controllerPackage");
		
		if (controllerPackage == null) {
			throw new ServletException("controllerPackage must be set during init");
		}
		
		router = new ReflectionRouter(controllerPackage);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		Controller controller = router.findController((HttpServletRequest) request);
		
		if (controller != null) {
			controller.serviceRequest((HttpServletRequest)request, (HttpServletResponse)response);
		} else {
		    chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// Meh
	}
	
}