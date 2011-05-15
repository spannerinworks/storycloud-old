package com.spannerinworks.storycloud.convention;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jdo.PersistenceManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ReflectionRouterTest {
	Mockery context = new JUnit4Mockery();

	@Test
	public void testFindsExistingController() throws Exception{
		final HttpServletRequest request = context.mock(HttpServletRequest.class);;
		context.checking(new Expectations() {{
		    allowing (request).getServletPath();
		    will(returnValue("/testable_fake/boom"));
		}});
		
		ReflectionRouter router = new ReflectionRouter("com.spannerinworks.storycloud.convention");
		Controller controller = router.findController(request);
		
		assertTrue(controller instanceof TestableFakeController);
	}
	
	@Test
	public void testReturnsNullIfControllerDoesntExist() throws Exception {
		final HttpServletRequest request = context.mock(HttpServletRequest.class);;
		context.checking(new Expectations() {{
		    allowing (request).getServletPath();
		    will(returnValue("/testableblahblahblah/boom"));
		}});
		
		ReflectionRouter router = new ReflectionRouter("com.spannerinworks.storycloud.convention");
		Controller controller = router.findController(request);
		
		assertNull(controller);
	}
}


class TestableFakeController extends ReflectionController {

	public TestableFakeController(PersistenceManagerFactory pmf) {
		super(pmf);
	}

	@Override
	public boolean serviceRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
}