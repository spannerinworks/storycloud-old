package com.spannerinworks.storycloud.convention;

import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ReflectionRouter implements Router {
	private String controllerPackage;
	
	public ReflectionRouter(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public Controller findController(HttpServletRequest req) throws ServletException  {
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
}
