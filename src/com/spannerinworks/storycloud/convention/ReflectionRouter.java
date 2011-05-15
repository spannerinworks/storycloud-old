package com.spannerinworks.storycloud.convention;

import java.lang.reflect.Constructor;

import javax.jdo.PersistenceManagerFactory;
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

		if (!controllerExists(controllerName)) {
			return null;
		}
		
		try {
			Constructor<?> constructor = Class.forName(controllerName).getConstructor(PersistenceManagerFactory.class);
			return (Controller) constructor.newInstance(PMF.get());
		} catch (Exception e) {
			throw new ServletException("Cannot create controller: " + controllerName, e);
		}
	}
	
	private boolean controllerExists(String controllerName) {
		String pathToController = "/" + controllerName.replace('.', '/') + ".class";
		
		return this.getClass().getResource(pathToController) != null;
	}
}
