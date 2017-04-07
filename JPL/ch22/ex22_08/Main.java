package ex22_08;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main
{
	public static List<String[]> readCSVTable(Readable source, int cellNum) throws IOException
	{
		if(cellNum < 1)
		{
			throw new IllegalArgumentException();
		}
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(source);
		List<String[]> vals = new ArrayList<>();
		
		String exp = "^([^,]*)";
		
		for(int i = 1; i < cellNum; i++)
		{
			exp += ",([^,]*)";
		}
		
		exp += "$";
		
		Pattern pat = Pattern.compile(exp, Pattern.MULTILINE);
		
		while(in.hasNextLine())
		{
			String line = in.findInLine(pat);
			
			if(line != null)
			{
				String[] cells = new String[cellNum];
				MatchResult match = in.match();
				
				for(int i = 0; i < cellNum; i++)
				{
					cells[i] = match.group(i + 1);
				}
				
				vals.add(cells);
				in.nextLine();
			}
			else
			{
				String s = in.nextLine();
				
				if(!s.matches("\\s*"))
				{
					throw new IOException();
				}
			}
		}
		
		IOException ex = in.ioException();
		
		if(ex != null)
		{
			throw ex;
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
			List<String[]> list = readCSVTable(source, 3);
			
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
