package com.spannerinworks.storycloud.convention;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface Router {
	Controller findController(HttpServletRequest req) throws ServletException;
}
