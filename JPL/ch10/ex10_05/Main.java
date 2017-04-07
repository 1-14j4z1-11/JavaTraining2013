package ex10_05;

public class Main
{
	public static void printCharSeq(char c1, char c2)
	{
		if(c1 < c2)
		{
			for(char c = c1; c <= c2; c++)
			{
				System.out.print(c);
			}
		}
		else
		{
			for(char c = c2; c <= c1; c++)
			{
				System.out.print(c);
			}
		}
		
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		printCharSeq('A', 'Z');
		printCharSeq('f', 'F');
	}
}
