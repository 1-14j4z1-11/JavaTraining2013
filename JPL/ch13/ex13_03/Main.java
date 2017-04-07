package ex13_03;

import java.util.ArrayList;

public class Main
{
	public static String[] delimitedStringAll(String str, char start, char end)
	{
		ArrayList<String> list = new ArrayList<String>();
		int startIndex = -1;
		
		for(int i = 0; i < str.length(); i++)
		{
			if((startIndex == -1) && (str.charAt(i) == start))
			{
				startIndex = i;
			}
			else if((startIndex != -1) && (str.charAt(i) == end))
			{
				list.add(str.substring(startIndex, i + 1));
				startIndex = -1;
			}
		}
		
		if(startIndex != -1)
		{
			list.add(str.substring(startIndex));
		}
		
		return list.toArray(new String[] { });
	}
	
	public static void main(String[] args)
	{
		String str1 = "AbcdEAxyzEAbcDAEAE";
		String[] strArray = delimitedStringAll(str1, 'A', 'E');
		
		for(int i = 0; i < strArray.length; i++)
		{
			System.out.println(strArray[i]);
		}
	}
}
