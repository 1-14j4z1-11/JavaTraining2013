package ex20_04;

import java.io.*;

public class LineReader extends FilterReader
{
	public LineReader(Reader reader)
	{
		super(reader);
	}
	
	public String readLine()
	{
		int data = 0;
		String newLine = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		
		try
		{
			while(true)
			{
				data = super.read();
				
				if(data == -1)
				{
					if(builder.length() == 0)
					{
						return null;
					}
					else
					{
						break;
					}
				}
				else if((data == '\n') || (data == '\r'))
				{
					if(newLine.length() == 2)
					{
						super.read();
					}
					break;
				}
				else
				{
					builder.append((char)data);
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return builder.toString();
	}
}
