package ex03_05;

import java.security.InvalidParameterException;

public class CollatzBenchmark extends Benchmark
{
	private int value;
	
	public CollatzBenchmark(int value)
	{
		if(value <= 0)
			throw new InvalidParameterException();
		
		this.value = value;
	}
	
	@Override
	protected void benchmark()
	{
		int tmp = value;
		
		while(tmp != 1)
		{
			if(tmp % 2 == 0)
				tmp /= 2;
			else
				tmp = tmp * 3 + 1;
		}
	}
}
