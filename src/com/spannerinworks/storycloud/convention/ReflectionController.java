package com.spannerinworks.storycloud.convention;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReflectionController implements Controller {

	public void serviceRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		new JdoTemplate(){
			public void query(PersistenceManager pm) throws ServletException, IOException {
				Method action = findAction(request);
				try {
					action.invoke(ReflectionController.this, request, response, pm);
				} catch (IllegalArgumentException e) {
					throw new ServletException("Action method not called correctly", e);
				} catch (InvocationTargetException e) {
					throw new ServletException("Action method doesn't have correct arguments", e);
				} catch (IllegalAccessException e) {
					throw new ServletException("Action method not available", e);
				}
			}
		}.run();

	}
	
	private Method findAction(HttpServletRequest req) throws ServletException {
		try {
			String path = req.getServletPath();
	
			String[] pathParts = path.split("/");
			String actionName = pathParts[2];
	
			Class<?>[] argTypes = {HttpServletRequest.class, HttpServletResponse.class, PersistenceManager.class};
			return this.getClass().getDeclaredMethod(actionName, argTypes);
		} catch (NoSuchMethodException e) {
			throw new ServletException("Unknown method on controller", e);
		}
	}
}
