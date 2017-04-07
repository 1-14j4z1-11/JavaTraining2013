package ex20_05;

import java.io.*;

public class Main
{
	private static final int SKIP_ARRAY = 65536;
	
	private static void searchString(String path, String ref)
	{
		LineNumberReader reader = null;
		
		try
		{
			reader = new LineNumberReader(new FileReader(path));
			String line;
			
			while((line = reader.readLine()) != null)
			{
				if(existsString(line, ref))
				{
					System.out.printf("%4d : %s%n", reader.getLineNumber(), line);
				}
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
				if(reader != null)
				{
					reader.close();
				}
			}
			catch(IOException e)
			{ }
		}
	}

	private static boolean existsString(String input, String ref)
	{
		int[] skip = new int[SKIP_ARRAY];
		int targetLength = ref.length();
		
		for(int i = 0; i < skip.length; i++)
		{
			skip[i] = targetLength;
		}
		
		for(int i = 0; i < targetLength; i++)
		{
			char ch = ref.charAt(i);
			
			if(ch < skip.length)
			{
				skip[ch] = targetLength - i - 1;
			}
		}
		
		for(int i = 0; i < input.length() - targetLength + 1;)
		{
			boolean isMatch = true;
			
			for(int j = targetLength - 1; j >= 0; j--)
			{
				if(input.charAt(i + j) != ref.charAt(j))
				{
					i += (input.charAt(i + j) < skip.length) ? skip[input.charAt(i + j)] : 1;
					
					isMatch = false;
					break;
				}
			}
			
			if(isMatch)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("Usage : Main <input_path> <reference_string>");
			return;
		}
		
		searchString(args[0], args[1]);
	}
}
