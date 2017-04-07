package ex24_03;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Main
{
	public static void main(String[] args)
	{
		String text = "10/1/3 14:23:54";
		Date date = null;
		
		int[] formatTypes = new int[]
		{
			DateFormat.FULL,
			DateFormat.LONG,
			DateFormat.MEDIUM,
			DateFormat.SHORT,
		};
		
		try
		{
			DateFormat format = DateFormat.getInstance();
			date = format.parse(text);
			
			for(int f1 : formatTypes)
			{
				for(int f2 : formatTypes)
				{
					format = DateFormat.getDateTimeInstance(f1, f2);
					System.out.println(format.format(date));
				}
			}
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
	}
}
