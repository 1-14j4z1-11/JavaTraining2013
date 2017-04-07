package ex11_01;

public class Main
{
	public static void main(String[] args)
	{
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		for(int i = 0; i < 5; i++)
		{
			list.add(i);
		}
		
		System.out.println(list);
	}
}
