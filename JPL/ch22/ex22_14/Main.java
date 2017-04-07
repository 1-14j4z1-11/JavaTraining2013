package ex22_14;

import java.util.regex.*;

public class Main
{
	public static void main(String[] args)
	{
		String text = "3.5 4.3 2.1 4.4 10.3 22.0";
		
		Pattern pat = Pattern.compile("\\s*([^\\s]+)\\s*");
		Matcher matcher = pat.matcher(text);
		
		double value = 0;
		
		while(matcher.find())
		{
			try
			{
				value += Double.parseDouble(matcher.group(1));
			}
			catch(NumberFormatException e)
			{
				System.out.println("InvalidValue : " + "");
				break;
			}
		}
		
		System.out.println("value = " + value);
	}
}
