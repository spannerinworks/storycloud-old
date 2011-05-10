package com.spannerinworks.storycloud.convention;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ConventionFilter implements Filter {
	private String dispatcherPath = "/dispatch";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		dispatcherPath = filterConfig.getInitParameter("dispatcher path");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI();
		if (uri.startsWith("/WEB-INF") || uri.startsWith(dispatcherPath)) {
		    chain.doFilter(request, response);
		} else {
		    request.getRequestDispatcher(dispatcherPath + uri).forward(request, response);
		}
	}

	@Override
	public void destroy() {
		// Meh
	}

}
