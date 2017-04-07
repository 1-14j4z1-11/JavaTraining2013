package ex20_04;

import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		FileReader fr = null;
		LineReader lr = null;
		
		try
		{
			fr = new FileReader(args[0]);
			lr = new LineReader(fr);
			String line;
			
			while((line = lr.readLine()) != null)
			{
				System.out.println(line);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(lr != null)
				{
					lr.close();
				}
				if(fr != null)
				{
					fr.close();
				}
			}
			catch(IOException e)
			{ }
		}
	}
}
