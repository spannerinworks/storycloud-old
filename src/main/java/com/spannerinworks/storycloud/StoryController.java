package com.spannerinworks.storycloud;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spannerinworks.storycloud.convention.Controller;

public class StoryController implements Controller {
	private ServletContext servletContext;
	
	public void index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		forwardToView("index", req, res);
	}

	@Override
	public void setServletContext(ServletContext sc) {
		servletContext = sc;
	}
	
	private void forwardToView(String viewName, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		servletContext.getRequestDispatcher("/WEB-INF/views/story/" + viewName + ".jsp").forward(req, res);
	}
	
}
