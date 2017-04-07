package ex14_05;

public class Calculation
{
	public static int value = 100000;
	
	public static synchronized void addValue(int value)
	{
		Calculation.value += value;
		System.out.printf("Value : %8d%n", Calculation.value);
	}
}
