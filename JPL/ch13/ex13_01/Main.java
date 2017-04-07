package ex13_01;

public class Main
{
	public static int countChar(String str, char target)
	{
		int count = 0;
		
		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == target)
			{
				count++;
			}
		}
		
		return count;
	}
	
	public static void main(String[] args)
	{
		String str = "HelloWorld";
		
		System.out.println("Count 'o' : " + countChar(str, 'o'));
		System.out.println("Count '!' : " + countChar(str, '!'));
		
	}
}
