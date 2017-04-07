package ex22_01;

public class Main
{
	public static void printDouble(double[] array, int col)
	{
		for(int i = 0; i < array.length; i++)
		{
			System.out.printf("%9.3f ", array[i]);
			
			if((i % col) == col - 1)
			{
				System.out.println();
			}
		}
	}
	
	public static void main(String[] args)
	{
		double[] array = { 1.22, 32.569, 640.654654, 46456.54562, 454.98, 7823.31, 46.6, 78.542, 197.1, 910.0, 5431.5, 9019.0 };
		
		printDouble(array, 6);
	}
}
