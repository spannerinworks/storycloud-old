package com.spannerinworks.storycloud.convention;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.spannerinworks.storycloud.convention.ReflectionController;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.Expectations;
import org.jmock.Mockery;

@RunWith(JMock.class)
public class ReflectionControllerTest {
	Mockery context = new JUnit4Mockery();
	
	@Test
	public void returnFalseIfMethodNotFound() throws Exception {

		HttpServletRequest request = TestHelper.mockRequest(context, "/story/boom");
		
		ReflectionController controller = new ReflectionController(null) {
			@SuppressWarnings("unused")
			public void index(HttpServletRequest req, HttpServletResponse res, PersistenceManager pm) throws ServletException, IOException {
			}
		};
		
		assertFalse(controller.serviceRequest(request , null));
	}
	
	@Test
	public void canExecuteActionInTransaction() throws Exception {

		HttpServletRequest request = TestHelper.mockRequest(context, "/story/index");
		final PersistenceManagerFactory pmf = context.mock(PersistenceManagerFactory.class);
		final PersistenceManager pm = context.mock(PersistenceManager.class);
		final Transaction transaction = context.mock(Transaction.class);

		context.checking(new Expectations() {{

		    allowing (pmf).getPersistenceManager();
		    will(returnValue(pm));

		    allowing (pm).currentTransaction();
		    will(returnValue(transaction));

		    one (pm).close();

		    one (transaction).begin();

		    one (transaction).commit();
		    one (transaction).isActive();
		    will(returnValue(false));
		}});

		final boolean[] called = {false};
		
		ReflectionController controller = new ReflectionController(pmf) {
			@SuppressWarnings("unused")
			public void index(HttpServletRequest req, HttpServletResponse res, PersistenceManager pm) throws ServletException, IOException {
				called[0] = true;
			}
		};

		assertTrue(controller.serviceRequest(request , null));
		
		assertTrue(called[0]);

	}
	
	@Test
	public void canRollbackTransaction() throws Exception {

		HttpServletRequest request = TestHelper.mockRequest(context, "/story/index");
		final PersistenceManagerFactory pmf = context.mock(PersistenceManagerFactory.class);
		final PersistenceManager pm = context.mock(PersistenceManager.class);
		final Transaction transaction = context.mock(Transaction.class);

		context.checking(new Expectations() {{

		    allowing (pmf).getPersistenceManager();
		    will(returnValue(pm));

		    allowing (pm).currentTransaction();
		    will(returnValue(transaction));

		    one (pm).close();

		    one (transaction).begin();
		    one (transaction).isActive();
		    will(returnValue(true));
		    one (transaction).rollback();

		}});

		ReflectionController controller = new ReflectionController(pmf) {
			@SuppressWarnings("unused")
			public void index(HttpServletRequest req, HttpServletResponse res, PersistenceManager pm) throws ServletException, IOException {
				throw new ServletException("Rollback!");
			}
		};

		try {
			controller.serviceRequest(request , null);
		} catch(ServletException e) {
			// expected
		}

	}
	
}
