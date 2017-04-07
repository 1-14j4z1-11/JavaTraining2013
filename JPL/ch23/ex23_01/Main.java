package ex23_01;

import java.io.*;

public class Main
{
	public static Process userProg(String cmd) throws IOException
	{
		Process process = Runtime.getRuntime().exec(cmd);
		
		plugTogether(System.in, process.getOutputStream());
		plugTogether(System.out, process.getInputStream());
		plugTogether(System.err, process.getErrorStream());
		
		return process;
	}
	
	public static void plugTogether(InputStream src, OutputStream dst)
	{
		Thread thread = new Thread(new PipeOperation(src, dst));
		thread.setDaemon(true);
		thread.start();
	}

	public static void plugTogether(OutputStream src, InputStream dst)
	{
		plugTogether(dst, src);
	}

	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(String arg : args)
		{
			builder.append(arg).append(" ");
		}
		
		try
		{
			Process process = userProg(builder.toString());
			process.waitFor();
		}
		catch(IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
