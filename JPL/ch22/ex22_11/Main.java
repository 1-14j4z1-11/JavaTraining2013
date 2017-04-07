package ex22_11;

import java.io.*;
import java.util.*;

public class Main
{
	public static List<String[]> readCSVTable(FileReader source) throws IOException
	{	
		StreamTokenizer tokenizer = new StreamTokenizer(source);
		
		tokenizer.ordinaryChars('0', '9');
		tokenizer.ordinaryChars('-', '-');
		tokenizer.ordinaryChars('.', '.');
		tokenizer.whitespaceChars(',', ',');
		
		List<String[]> vals = new ArrayList<>();
		List<String> line = new ArrayList<>();
		int lineNo = 1;
		
		while(tokenizer.nextToken() != StreamTokenizer.TT_EOF)
		{
			if(tokenizer.ttype == StreamTokenizer.TT_WORD)
			{
				if(lineNo != tokenizer.lineno())
				{
					lineNo = tokenizer.lineno();
					vals.add(line.toArray(new String[]{ null }));
					line.clear();
				}
				
				line.add(tokenizer.sval);
			}
		}
		
		if(line.size() > 0)
		{
			vals.add(line.toArray(new String[]{ null }));
		}
		
		return vals;
	}
	
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		FileReader source = null;
		
		try
		{
			source = new FileReader(args[0]);
			List<String[]> list = readCSVTable(source);
			
			for(String[] words : list)
			{
				for(String s : words)
				{
					System.out.print("[" + s + "] ");
				}
				
				System.out.println();
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
				if(source != null)
				{
					source.close();
				}
			}
			catch(IOException ex)
			{ }
		}
	}
}
