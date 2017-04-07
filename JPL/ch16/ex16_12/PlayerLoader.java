package ex16_12;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

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
	
	public URL findResource(String name)
	{
		File file = new File(name);
		
		if(!file.exists())
		{
			return null;
		}
		
		try
		{
			return file.toURI().toURL();
		}
		catch(MalformedURLException e)
		{
			return null;
		}
	}

	public Enumeration<URL> findResources(String name)
	{
		File file = new File(name);
		
		if(!file.exists())
		{
			return null;
		}
		
		try
		{
			return new Enumerator(new URL[] { file.toURI().toURL() });
		}
		catch(MalformedURLException e)
		{
			return null;
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

	private static class Enumerator implements Enumeration<URL>
	{
		private int index = 0;
		private final URL[] urls;
		
		public Enumerator(URL[] urls)
		{
			this.urls = urls;
		}

		@Override
		public boolean hasMoreElements()
		{
			return (this.index < this.urls.length);
		}

		@Override
		public URL nextElement()
		{
			if(!this.hasMoreElements())
			{
				throw new IndexOutOfBoundsException();
			}
			
			return this.urls[this.index++];
		}
	}
}
