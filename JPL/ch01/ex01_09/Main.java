package ex01_09;

public class Main {
	private static final int MAX_INDEX = 9;
	
	public static void main(String[] args) {
		int lo = 0;
		int hi = 1;
		
		int[] numbers = new int[MAX_INDEX];
		
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = hi;
			
			hi = hi + lo;
			lo = hi - lo;
		}
		
		for(int number : numbers)
		{
			System.out.println(number);
		}
	}

}
