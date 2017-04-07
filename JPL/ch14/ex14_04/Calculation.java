package ex14_04;

public class Calculation
{
	private static int value;
	
	public static synchronized void addValue(int value)
	{
		Calculation.value += value;
		System.out.printf("Value : %8d%n", Calculation.value);
	}
}
