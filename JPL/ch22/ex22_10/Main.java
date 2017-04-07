package ex22_10;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		Scanner in = null;
		
		try
		{
			FileReader source = new FileReader(args[0]);
			in = new Scanner(source);
			
			in.useDelimiter("\\s|(#.*)");
			
			while(in.hasNext())
			{
				String word = in.next();
				
				if(!word.equals(""))
					System.out.println("[token] " + word);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
			{
				in.close();
			}
		}
	}
}
