package ex20_08;

import java.io.*;
import java.util.*;

public class Main
{
	private static ArrayList<Long> getEntryTable(String path) throws IOException
	{
		ArrayList<Long> indices = new ArrayList<Long>();
		RandomAccessFile raf = null;
		
		try
		{
			raf = new RandomAccessFile(path, "r");
			String line;
			
			while((line = raf.readLine()) != null)
			{
				if(line.startsWith("%%"))
				{
					indices.add(raf.getFilePointer());
				}
			}
		}
		finally
		{
			try
			{
				if(raf != null)
				{
					raf.close();
				}
			}
			catch(IOException e)
			{ }
		}
		
		return indices;
	}
	
	private static String printEntry(RandomAccessFile file, long fp) throws IOException
	{
		if(file == null)
		{
			throw new NullPointerException();
		}
		
		StringBuilder builder = new StringBuilder();
		String line;
		String newLine = System.getProperty("line.separator");
		
		file.seek(fp);
		
		while((line = file.readLine()) != null)
		{
			if(line.startsWith("%%"))
			{
				break;
			}
			
			builder.append(line).append(newLine);
		}
		
		return builder.toString();
	}
	
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		try
		{
			ArrayList<Long> table = getEntryTable(args[0]);
			RandomAccessFile raf = new RandomAccessFile(args[0], "r");
			Random random = new Random();
			
			while(table.size() > 0)
			{
				int index = (table.size() == 1) ? 0 : random.nextInt(table.size());

				System.out.println("----------------");
				System.out.print(printEntry(raf, table.remove(index)));
			}

			System.out.println("----------------");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
