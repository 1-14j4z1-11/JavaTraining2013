package ex13_02;

public class Main
{
	private static final int SKIP_ARRAY = 65536;
	
	public static int countString(String str, String target)
	{
		int[] skip = new int[SKIP_ARRAY];
		int targetLength = target.length();
		
		for(int i = 0; i < skip.length; i++)
		{
			skip[i] = targetLength;
		}
		
		for(int i = 0; i < targetLength; i++)
		{
			char ch = target.charAt(i);
			
			if(ch < skip.length)
			{
				skip[ch] = targetLength - i - 1;
			}
		}
		
		int count = 0;
		int i = 0;
		
		while(i < str.length() - targetLength + 1)
		{
			boolean isMatch = true;
			
			for(int j = targetLength - 1; j >= 0; j--)
			{
				if(str.charAt(i + j) != target.charAt(j))
				{
					if(str.charAt(i + j) < skip.length)
					{
						i += skip[str.charAt(i + j)];
					}
					else
					{
						i++;
					}
					
					isMatch = false;
					break;
				}
			}
			
			if(isMatch)
			{
				count++;
				i += targetLength;
			}
		}
		
		return count;
	}
	
	public static void main(String[] args)
	{
		String str1 = " HelloWorldHello world";
		String str2 = "テストテストテスト";
		
		System.out.println("Count 'Hello' : " + countString(str1, "Hello"));
		System.out.println("Count 'テスト' : " + countString(str2, "テスト"));
	}
}
