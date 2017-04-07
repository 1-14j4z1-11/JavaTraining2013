package ex21_07;

import static org.junit.Assert.*;
import java.util.EmptyStackException;
import org.junit.Test;

public class TestStack
{
	@Test(expected=EmptyStackException.class)
	public void testEmpty()
	{
		Stack<Integer> stack = new Stack<>();
		
		assertEquals(0, stack.size());
		stack.pop();
	}
	
	@Test(expected=EmptyStackException.class)
	public void testPushPop1()
	{
		Stack<Integer> stack = new Stack<>();
		
		stack.push(123);
		assertEquals(1, stack.size());
		assertEquals(123, (int)stack.pop());
		assertEquals(0, stack.size());
		stack.pop();
	}

	@Test(expected=EmptyStackException.class)
	public void testPushPop2()
	{
		Stack<Integer> stack = new Stack<>();
		
		stack.push(100);
		stack.push(200);
		stack.push(300);
		
		assertEquals(3, stack.size());
		assertEquals(300, (int)stack.pop());
		
		assertEquals(2, stack.size());
		assertEquals(200, (int)stack.pop());
		
		assertEquals(1, stack.size());
		assertEquals(100, (int)stack.pop());
		
		assertEquals(0, stack.size());
		stack.pop();
	}
}
