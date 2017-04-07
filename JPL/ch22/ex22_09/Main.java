package ex22_09;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main
{
	public static List<String[]> readCSVTable(Readable source, int cellNum, SeparatePatternCreator pattern) throws IOException
	{
		if(cellNum < 1)
		{
			throw new IllegalArgumentException();
		}
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(source);
		List<String[]> vals = new ArrayList<>();
		
		Pattern pat = Pattern.compile(pattern.create(cellNum), Pattern.MULTILINE);
		
		while(in.hasNextLine())
		{
			String line = in.findInLine(pat);
			
			if(line != null)
			{
				String[] cells = new String[cellNum];
				MatchResult match = in.match();
				
				for(int i = 0; (i < cellNum) && (i < match.groupCount()); i++)
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
	
	public static void testReadCSV(String path, SeparatePatternCreator creator)
	{
		FileReader source = null;
		long start = 0, stop = 0;
		List<String[]> table = null;
		
		try
		{
			source = new FileReader(path);

			start = System.currentTimeMillis();
			table = readCSVTable(source, 5, creator);
			stop = System.currentTimeMillis();
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
		
		// データチェック
		boolean valid = true;
		
		for(String[] ss : table)
		{
			for(String s : ss)
			{
				if(s == null)
					valid = false;
			}
		}
		
		System.out.printf("[Time] : %3d ms  [Data check] %s%n", stop - start, (valid ? "OK" : "NG"));
	}
	
	public static void main(String[] args)
	{
		String path1 = "./src/ex22_09/test1.txt";
		String path2 = "./src/ex22_09/test2.txt";
		
		testReadCSV(path1, CSVRegexPattern.PATTERN1);
		testReadCSV(path1, CSVRegexPattern.PATTERN2);
		testReadCSV(path1, CSVRegexPattern.PATTERN3);
		testReadCSV(path1, CSVRegexPattern.PATTERN4);
		
		System.out.println();
		
		testReadCSV(path2, CSVRegexPattern.PATTERN1);
		testReadCSV(path2, CSVRegexPattern.PATTERN2);
		testReadCSV(path2, CSVRegexPattern.PATTERN3);
		testReadCSV(path2, CSVRegexPattern.PATTERN4);
	}
}
