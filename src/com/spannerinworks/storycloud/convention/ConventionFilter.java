package com.spannerinworks.storycloud.convention;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConventionFilter implements Filter {
	private String controllerPackage;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		controllerPackage = filterConfig.getInitParameter("controllerPackage");
		
		if (controllerPackage == null) {
			throw new ServletException("controllerPackage must be set during init");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		Controller controller = findController((HttpServletRequest) request);
		
		if (controller != null) {
			Method action = findAction((HttpServletRequest) request, controller);
			try {
				action.invoke(controller, (HttpServletRequest) request, (HttpServletResponse)response);
			} catch (IllegalArgumentException e) {
				throw new ServletException("Action method not called correctly", e);
			} catch (InvocationTargetException e) {
				throw new ServletException("Action method doesn have correct arguments", e);
			} catch (IllegalAccessException e) {
				throw new ServletException("Action method not available", e);
			}
		} else {
		    chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// Meh
	}

	private Controller findController(HttpServletRequest req) throws ServletException  {
		String path = req.getServletPath();

		String[] pathParts = path.split("/");
			
		String controllerPrefix = pathParts[1];
		String[] controllerNameParts = controllerPrefix.split("_");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < controllerNameParts.length; i++) {
			buf.append(controllerNameParts[i].substring(0, 1).toUpperCase());
			buf.append(controllerNameParts[i].substring(1).toLowerCase());
		}
		String controllerName = controllerPackage + "." + buf.toString() + "Controller";

		try {
			String pathToController = controllerName.replace('.', '/');
			URL url = Thread.currentThread().getClass().getResource(pathToController);
			
			if (url != null) {
				return null;
			}
			return (Controller)(Class.forName(controllerName).newInstance());
		} catch (IllegalAccessException e) {
			throw new ServletException("Controller not available", e);
		} catch (ClassNotFoundException e) {
			throw new ServletException("Cannot find controller", e);
		} catch (InstantiationException e) {
			throw new ServletException("Cannot create controller", e);
		}
	}
	
	private Method findAction(HttpServletRequest req, Controller controller) throws ServletException {
		try {
			String path = req.getServletPath();
	
			String[] pathParts = path.split("/");
			String actionName = pathParts[2];
	
			Class<?>[] argTypes = {HttpServletRequest.class, HttpServletResponse.class};
			return controller.getClass().getDeclaredMethod(actionName, argTypes);
		} catch (NoSuchMethodException e) {
			throw new ServletException("Unknown method on controller", e);
		}
	}
}