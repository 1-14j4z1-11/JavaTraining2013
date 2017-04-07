package ex14_05;

public class Main
{
	public static void main(String[] args)
	{
		TestDec test1 = new TestDec(100, 2);
		TestDec test2 = new TestDec(100, 1);
		
		Thread t1 = new Thread(test1);
		Thread t2 = new Thread(test2);
		
		t1.start();
		t2.start();
	}
}

class TestDec implements Runnable
{
	int loop;
	int value;
	
	public TestDec(int loop, int value)
	{
		this.loop = loop;
		this.value = value;
	}
	
	@Override
	public void run()
	{
		for(int i = 0; i < this.loop; i++)
		{
			synchronized (Calculation.class)
			{
				Calculation.value -= this.value;
				System.out.printf("Value : %8d%n", Calculation.value);
			}
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{ }
		}
	}
}