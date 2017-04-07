package ex21_05;

import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		Integer[][] arrays = {
				{ 1, 2, 3, 4, 5 },
				{ 11, 12, 13 },
				{ 21, 22, 23, 24, 25, 26 }, };
		
		List<Integer> list = new ArrayBunchList<>(arrays);
		ListIterator<Integer> iter = list.listIterator();
		
		while(iter.hasNext())
		{
			System.out.printf("%2d : %d%n", iter.nextIndex(), iter.next());
		}

		System.out.println();
		
		while(iter.hasPrevious())
		{
			System.out.printf("%2d : %d%n", iter.previousIndex(), iter.previous());
		}
	}
}
