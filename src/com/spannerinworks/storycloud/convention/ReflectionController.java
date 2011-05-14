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

	// TODO: find a better way of detecting method with exception handling
	public boolean serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Method action = findAction(request);
		if (action != null) {
			invokeMethod(action, request, response);
			return true;
		} else {
			return false;
		}
	}
	
	protected Method findAction(HttpServletRequest req) {
		String path = req.getServletPath();

		String[] pathParts = path.split("/");
		String actionName = pathParts[2];

		Class<?>[] argTypes = {HttpServletRequest.class, HttpServletResponse.class, PersistenceManager.class};

		Method m[] = this.getClass().getDeclaredMethods();
		
		for (int i = 0; i < m.length; i++) {
			Method method = m[i];
			if (method.getName().equals(actionName) && arrayContentsEq(method.getParameterTypes(), argTypes)) {
				return method;
			}
		}
		
		return null;
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
	
    private static boolean arrayContentsEq(Object[] a1, Object[] a2) {
        if (a1 == null) {
            return a2 == null || a2.length == 0;
        }

        if (a2 == null) {
            return a1.length == 0;
        }

        if (a1.length != a2.length) {
            return false;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }
}
