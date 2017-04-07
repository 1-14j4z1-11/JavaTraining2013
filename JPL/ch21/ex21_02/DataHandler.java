package ex21_02;

import java.io.*;
import java.util.WeakHashMap;

public class DataHandler
{
	private WeakHashMap<File, byte[]> cache = new WeakHashMap<>();
	
	byte[] readFile(File file) throws FileNotFoundException
	{
		byte[] data;
		
		if(this.cache.containsKey(file))
		{
			System.out.println("read from cache");
			
			data = this.cache.get(file);
			
			if(data != null)
			{
				return data;
			}
		}
		
		System.out.println("read from file");
		
		data = this.readBytesFromFile(file);
		cache.put(file, data);
		
		return data;
	}

	private byte[] readBytesFromFile(File file) throws FileNotFoundException
	{
		if(!file.isFile())
		{
			throw new IllegalArgumentException();
		}
		
		if(!file.exists())
		{
			throw new FileNotFoundException();
		}
		
		byte[] data = new byte[(int)file.length()];
		FileInputStream fis = new FileInputStream(file);
		
		try
		{
			fis.read(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fis.close();
			}
			catch (IOException e)
			{ }
		}
		
		return data;
	}
}
