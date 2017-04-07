package ex21_02;

import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Usage : <main_class> <file_path ...>");
			return;
		}
		
		DataHandler handler = new DataHandler();
		
		try
		{
			for(String s : args)
			{
				File file = new File(s);
				
				if(file.exists())
				{
					handler.readFile(file);
				}
				else
				{
					System.out.println("Not Exist : " + s);
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
