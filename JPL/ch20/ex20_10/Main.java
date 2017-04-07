package ex20_10;

import java.io.*;
import java.util.HashMap;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		FileReader reader = null;
		
		try
		{
			reader = new FileReader(args[0]);
			StreamTokenizer in = new StreamTokenizer(reader);

			in.wordChars('a', 'z');
			in.wordChars('A', 'Z');
			in.ordinaryChars('0', '9');
			in.wordChars('0', '9');
			in.ordinaryChars('-', '-');
			in.wordChars('-', '-');
			in.wordChars(128 + 32, 255);
			in.whitespaceChars(0, ' ');
			in.whitespaceChars('.', '.');
			in.whitespaceChars('(', ')');
			in.whitespaceChars('<', '>');
			
			while(in.nextToken() != StreamTokenizer.TT_EOF)
			{
				switch(in.ttype)
				{
					case StreamTokenizer.TT_WORD:
						int count = map.containsKey(in.sval) ? map.get(in.sval) : 0;
						map.put(in.sval, count + 1);
						break;
						
					default:
						break;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				}
				catch(IOException e)
				{ }
			}
		}
		
		for(String key : map.keySet())
		{
			System.out.printf("%s : %d%n", key, map.get(key));
		}
	}
}
