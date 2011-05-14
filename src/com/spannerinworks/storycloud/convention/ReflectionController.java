package com.spannerinworks.storycloud.convention;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ReflectionController implements Controller {
	private PersistenceManagerFactory pmf;
	
	public ReflectionController(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}

	public void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Method action = findAction(request);
		invokeMethod(action, request, response);
	}
	
	protected Method findAction(HttpServletRequest req) throws ServletException {
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
	
	protected void invokeMethod(Method action, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
			transaction.begin();
			try {
				action.invoke(ReflectionController.this, request, response, pm);
			} catch (IllegalArgumentException e) {
				throw new ServletException("Action method not called correctly", e);
			} catch (InvocationTargetException e) {
				throw new ServletException("Action method doesn't have correct arguments", e);
			} catch (IllegalAccessException e) {
				throw new ServletException("Action method not available", e);
			}
		    transaction.commit();
		} finally {
			try {
			    if (transaction.isActive()) {
			        transaction.rollback();
			    }
			} finally {
		    	pm.close();
		    }
		}
		
	}
}
