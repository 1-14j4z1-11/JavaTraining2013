package ex20_01;

import java.io.*;

public class Main
{
	private static void translateByte(InputStream in, OutputStream out, char from, char to)
	{
		int data = 0;
		
		try
		{
			while((data = in.read()) != -1)
			{
				out.write((data == from) ? to : data);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		InputStream in = System.in;
		OutputStream out = System.out;
		
		translateByte(in, out, 'a', 'A');
	}
}
