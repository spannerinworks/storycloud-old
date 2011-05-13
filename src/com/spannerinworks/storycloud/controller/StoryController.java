package com.spannerinworks.storycloud.controller;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spannerinworks.storycloud.convention.Controller;
import com.spannerinworks.storycloud.convention.PMF;
import com.spannerinworks.storycloud.model.Story;

public class StoryController implements Controller {
	
	public void index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			Query q = pm.newQuery(com.spannerinworks.storycloud.model.Story.class);
				
			@SuppressWarnings("unchecked")
			List<Story> result = (List<Story>) q.execute();

			req.setAttribute("stories", result);
			req.getRequestDispatcher("/WEB-INF/view/story/index.jsp").forward(req, res);

		} finally {
			pm.close();
		}
		
	}
	
	public void create(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		pm.currentTransaction().begin();
		try {
			Story story = new Story(req.getParameter("title"), req.getParameter("description"), req.getParameter("points"));
		    pm.makePersistent(story);
		    pm.currentTransaction().commit();
			res.sendRedirect("/story/index");
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
}
