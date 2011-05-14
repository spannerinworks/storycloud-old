package com.spannerinworks.storycloud.convention;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;


public abstract class JdoTemplate {

	public void run() throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		pm.currentTransaction().begin();
		try {
			query(pm);
		    pm.currentTransaction().commit();
		} finally {
			try {
			    if (pm.currentTransaction().isActive()) {
			        pm.currentTransaction().rollback();
			    }
			} finally {
		    	pm.close();
		    }
		}

	}

	public abstract void query(PersistenceManager pm) throws ServletException, IOException;
	
}
