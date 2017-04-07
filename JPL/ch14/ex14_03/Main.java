package ex14_03;

public class Main
{
	public static void main(String[] args)
	{
		Calculation obj = new Calculation();
		Test test = new Test(obj, 100, 2);
		
		Thread t1 = new Thread(test);
		Thread t2 = new Thread(test);
		
		t1.start();
		t2.start();
	}
}

class Test implements Runnable
{
	private Calculation calc;
	int loop;
	int additionalValue;
	
	public Test(Calculation calc, int loop, int additionalValue)
	{
		if(calc == null)
		{
			throw new NullPointerException();
		}
		
		this.calc = calc;
		this.loop = loop;
		this.additionalValue = additionalValue;
	}
	
	@Override
	public void run()
	{
		for(int i = 0; i < this.loop; i++)
		{
			this.calc.addValue(this.additionalValue);
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{ }
		}
	}
}