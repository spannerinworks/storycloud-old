package com.spannerinworks.storycloud.convention;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.Mockery;

public class TestHelper {

	public static HttpServletRequest mockRequest(Mockery context, final String servletPath) {
		final HttpServletRequest request = context.mock(HttpServletRequest.class);;
		context.checking(new Expectations() {{
		    allowing (request).getServletPath();
		    will(returnValue(servletPath));
		}});
		return request;
	}
}
