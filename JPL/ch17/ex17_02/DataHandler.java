package ex17_02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class DataHandler
{
	private WeakReference<File> lastFile;
	private WeakReference<byte[]> lastData;
	
	byte[] readFile(File file) throws FileNotFoundException
	{
		byte[] data;
		
		if((this.lastFile != null) && (file.equals(this.lastFile.get())))
		{
			System.out.println("read from cache");
			
			data = this.lastData.get();
			
			if(data != null)
			{
				return data;
			}
		}
		
		System.out.println("read from file");
		
		this.lastFile = new WeakReference<>(file);
		data = this.readBytesFromFile(file);
		lastData = new WeakReference<>(data);
		
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
