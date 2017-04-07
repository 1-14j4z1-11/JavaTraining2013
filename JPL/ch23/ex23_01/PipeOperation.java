package ex23_01;

import java.io.*;

public class PipeOperation implements Runnable
{
	private InputStream in;
	private OutputStream out;
	
	public PipeOperation(InputStream in, OutputStream out)
	{
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run()
	{
		try
		{
			int data;
			
			while(true)
			{
				if((data = this.in.read()) != -1)
				{
					this.out.write(data);
				}
				
				Thread.sleep(0);
			}
		}
		catch(IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
