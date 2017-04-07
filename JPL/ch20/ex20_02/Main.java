package ex20_02;

import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		TranslateFilterReader in = new TranslateFilterReader(new InputStreamReader(System.in), 'a', 'A');
		OutputStream out = System.out;
		int data = 0;
		
		try
		{
			while((data = in.read()) != -1)
			{
				out.write(data);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
