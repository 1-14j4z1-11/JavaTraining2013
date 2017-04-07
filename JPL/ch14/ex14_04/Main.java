package ex14_04;

public class Main
{
	public static void main(String[] args)
	{
		Test test = new Test(100, 2);
		
		Thread t1 = new Thread(test);
		Thread t2 = new Thread(test);
		
		t1.start();
		t2.start();
	}
}

class Test implements Runnable
{
	int loop;
	int additionalValue;
	
	public Test(int loop, int additionalValue)
	{
		this.loop = loop;
		this.additionalValue = additionalValue;
	}
	
	@Override
	public void run()
	{
		for(int i = 0; i < this.loop; i++)
		{
			Calculation.addValue(this.additionalValue);
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{ }
		}
	}
}