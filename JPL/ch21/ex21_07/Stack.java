package ex21_07;

import java.util.*;

public class Stack<E>
{
	private ArrayList<E> list = new ArrayList<>();

	public void push(E obj)
	{
		this.list.add(obj);
	}
	
	public E pop()
	{
		if(this.list.size() == 0)
		{
			throw new EmptyStackException();
		}
		
		return this.list.remove(this.list.size() - 1);
	}
	
	public E peek()
	{
		if(this.list.size() == 0)
		{
			throw new EmptyStackException();
		}
		
		return this.list.get(this.list.size() - 1);
	}
	
	public int size()
	{
		return this.list.size();
	}
}
