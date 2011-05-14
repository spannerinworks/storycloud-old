package com.spannerinworks.storycloud.controller;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spannerinworks.storycloud.convention.ReflectionController;
import com.spannerinworks.storycloud.model.Story;

public class StoryController extends ReflectionController {
	
	public StoryController(PersistenceManagerFactory pmf) {
		super(pmf);
	}

	public void index(HttpServletRequest req, HttpServletResponse res, PersistenceManager pm) throws ServletException, IOException {
		Query q = pm.newQuery(Story.class);
			
		@SuppressWarnings("unchecked")
		List<Story> result = (List<Story>) q.execute();
	
		req.setAttribute("stories", result);
		req.getRequestDispatcher("/WEB-INF/view/story/index.jsp").forward(req, res);
	}
	
	public void create(HttpServletRequest req, HttpServletResponse res, PersistenceManager pm) throws ServletException, IOException {
		Story story = new Story(req.getParameter("title"), req.getParameter("description"), req.getParameter("points"));
	    pm.makePersistent(story);
		res.sendRedirect("/story/index");
	}
}
