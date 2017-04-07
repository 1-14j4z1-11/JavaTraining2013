package ex09_02;

public class Main
{
	private static final int SIZE_OF_INT = 32;
	
	public static int bitCount(int value)
	{
		int count = 0;
		
		for(int i = 0; i < SIZE_OF_INT; i++)
		{
			if((value & 1) == 1)
			{
				count++;
			}
			
			value >>>= 1;
		}
		
		return count;
	}
	
	public static void main(String[] args)
	{
		System.out.println(bitCount(0x12345678));
	}
}
