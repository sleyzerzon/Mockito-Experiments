package mockito.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class UserServiceTest {

	@Test
	public void testThenReturn() {
		
		UserManager manager = mock(UserManager.class);
		when(manager.getUserCount()).thenReturn(50);
		
		
		UserService service = new UserService(manager);
		assertEquals(50,service.getUserCount());
	}
	
	@Test
	public void testThenThrow() {
		
		UserManager manager = mock(UserManager.class);
		when(manager.getUserCount()).thenThrow(new RuntimeException());
		
		
		UserService service = new UserService(manager);
		assertEquals(-1,service.getUserCount());
		
	}
	
	@Test
	public void testThenAnswer() {
		
		UserManager manager = mock(UserManager.class);
		when(manager.getUserCount()).thenAnswer(new Answer(){

			private int count;
			
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {

				return ++count;
			}});
		
		
		UserService service = new UserService(manager);
		assertEquals(1,service.getUserCount());
		assertEquals(2,service.getUserCount());
		assertEquals(3,service.getUserCount());
	}
	
	
	@Test
	public void testSaving() {
		
		UserManager manager = mock(UserManager.class);
		ArgumentCaptor<?> captor = ArgumentCaptor.forClass(String.class);
		
		
		UserService service = new UserService(manager);
		String actualArg = "simeon";
		service.save(actualArg);
		
		verify(manager, times(1)).save((String) captor.capture());
		verify(manager, times(0)).getUserCount();
		
		assertThat((String)captor.getValue(),equalTo(actualArg));
	}

}
