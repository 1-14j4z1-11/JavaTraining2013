package ex22_12;

import java.io.*;
import java.util.Scanner;

public class Main
{
	public static AttributedImpl readAttrs(Reader source) throws IOException
	{
		Scanner in = new Scanner(source);
		AttributedImpl attrs = new AttributedImpl();
		
		String attrPat = "^(\\w+)=(\\w+)$";
		String commentPat = "^#.*$";
		
		while(in.hasNextLine())
		{
			String line = in.nextLine();
			
			if(line.matches(attrPat))
			{
				int index = line.indexOf('=');
				
				String name = line.substring(0, index);
				String value = line.substring(index + 1);
				
				attrs.add(new Attr(name, value));
			}
			else if(!line.matches(commentPat))
			{
				in.close();
				throw new IOException();
			}
		}
		
		in.close();
		
		return attrs;
	}
	
	public static void main(String[] args)
	{
		try
		{
			AttributedImpl attrs = readAttrs(new FileReader(args[0]));
			
			for(Attr attr : attrs)
			{
				System.out.println(attr);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
