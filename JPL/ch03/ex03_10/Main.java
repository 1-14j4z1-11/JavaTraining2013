package ex03_10;

public class Main
{
	public static void main(String[] args)
	{
		LinkedList l1 = new LinkedList();
		
		for(int i = 0; i < 5; i++)
		{
			l1.add(new Test(i));
		}
		
		LinkedList l2 = l1.clone();
		
		l1.removeAt(2);
		l1.removeAt(3);
		((Test)l1.getAt(0)).setValue(20);
		
		System.out.println(l1);
		System.out.println(l2);
	}
}

class Test
{
	private int value;
	
	public Test(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "value=" + value;
	}
}