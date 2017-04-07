package ex16_11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PlayerLoader extends ClassLoader
{
	public Class<?> findClass(String name) throws ClassNotFoundException
	{
		try
		{
			byte[] buf = bytesForClass(name);
			return defineClass(name, buf, 0, buf.length);
		}
		catch(IOException e)
		{
			throw new ClassNotFoundException();
		}
	}
	
	protected byte[] bytesForClass(String name)
		throws IOException, ClassNotFoundException
	{
		FileInputStream in = null;
		
		try
		{
			in = streamFor(name + ".class");
			int length = in.available();
			
			if(length == 0)
			{
				throw new ClassNotFoundException();
			}
			
			byte[] buf = new byte[length];
			in.read(buf);
			return buf;
		}
		finally
		{
			if(in != null)
			{
				in.close();
			}
		}
	}

	private FileInputStream streamFor(String path)
	{
		File file = new File(path);
		
		if(file.exists())
		{
			try
			{
				FileInputStream stream = new FileInputStream(file);
				return stream;
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
