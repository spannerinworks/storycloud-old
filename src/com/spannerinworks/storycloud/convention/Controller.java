package com.spannerinworks.storycloud.convention;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
