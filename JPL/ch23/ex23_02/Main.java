package ex23_02;

import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			return;
		}
		
		try
		{
			Process process = Runtime.getRuntime().exec(args);
			InputStream input =  process.getInputStream();
			InputStream error =  process.getErrorStream();
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(error));
			
			String lineO;
			String lineE;
			int count = 1;
			
			while(((lineO = inputReader.readLine()) != null) | ((lineE = errorReader.readLine()) != null))
			{
				if(lineO != null)
				{
					System.out.println(String.format("[StdOut]%3d | %s", count, lineO));
				}
				
				if(lineE != null)
				{
					System.out.println(String.format("[StdErr]%3d | %s", count, lineE));
				}
				
				count++;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
}
