package ex03_05;

public class Main
{
	public static void main(String[] args)
	{
		int value = 31;
		int count = 5;
		
		long time = new CollatzBenchmark(value).repeat(count);
		
		System.out.println(count + " methods in " + time + " nanoseconds");
	}
}