package ex12_01;

import ex12_01.LinkedList.ObjectNotFoundException;

public class Main
{
	public static void main(String[] args)
	{
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		for(int i = 0; i < 5; i++)
		{
			list.add(i);
		}
		
		try
		{
			Integer value = list.find(4);
			System.out.println("Found : " + value);
		}
		catch (ObjectNotFoundException e)
		{
			System.out.println("NotFound : " + e.getTargetObject());
		}
		
		try
		{
			Integer value = list.find(10);
			System.out.println("Found : " + value);
		}
		catch (ObjectNotFoundException e)
		{
			System.out.println("NotFound : " + e.getTargetObject());
		}
	}
}
