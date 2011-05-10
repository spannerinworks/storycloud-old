package com.spannerinworks.storycloud.convention;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.*;
import javax.servlet.http.*;

// Convention based dispatcher
// Finds appropriate controller
// Forwards to jsp
public class ConventionDispatcherServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			Controller controller = findController(req);
			controller.setServletContext(getServletContext());
			
			Method action = findAction(req, controller);
			
			action.invoke(controller, req, res);

		} catch (IllegalArgumentException e) {
			throw new ServletException("Action method not called correctly", e);
		} catch (InvocationTargetException e) {
			throw new ServletException("Action method doesn have correct arguments", e);
		} catch (IllegalAccessException e) {
			throw new ServletException("Action method not available", e);
		}
	}
	
	private Controller findController(HttpServletRequest req) throws ServletException  {
		String path = req.getPathInfo();

		getServletContext().log("PATH: " + path);

		String[] pathParts = path.split("/");
			
		String controllerPackage = "com.spannerinworks.storycloud.";
		String controllerPrefix = pathParts[1];
		String[] controllerNameParts = controllerPrefix.split("_");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < controllerNameParts.length; i++) {
			buf.append(controllerNameParts[i].substring(0, 1).toUpperCase());
			buf.append(controllerNameParts[i].substring(1).toLowerCase());
		}
		String controllerName = controllerPackage + buf.toString() + "Controller";

		try {
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
			String path = req.getPathInfo();
	
			String[] pathParts = path.split("/");
			String actionName = pathParts[2];
	
			Class<?>[] argTypes = {HttpServletRequest.class, HttpServletResponse.class};
			return controller.getClass().getDeclaredMethod(actionName, argTypes);
		} catch (NoSuchMethodException e) {
			throw new ServletException("Unknown method on controller", e);
		}
	}
	
}
