package ex14_03;

public class Calculation
{
	private int value;
	
	public synchronized void addValue(int value)
	{
		this.value += value;
		System.out.printf("Value : %8d%n", this.value);
	}
}
