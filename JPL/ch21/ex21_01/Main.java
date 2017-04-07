package ex21_01;

import java.io.*;
import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Usage : <main_class> <file_path>");
			return;
		}
		
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> lines = new ArrayList<String>();
		
		try
		{
			fr = new FileReader(args[0]);
			br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null) 
			{
				lines.add(line);
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
				if(br != null)
				{
					br.close();
				}
				if(fr != null)
				{
					fr.close();
				}
			}
			catch(IOException e)
			{ }
		}
		
		Collections.sort(lines);
		
		for(String s : lines)
		{
			System.out.println(s);
		}
	}
}
