package ex20_11;

import java.io.*;

public class Main
{
	private static class SuffixFilter implements FilenameFilter
	{
		private final String suffix;
		
		public SuffixFilter(String suffix)
		{
			this.suffix = suffix;
		}
		
		@Override
		public boolean accept(File dir, String name)
		{
			int index = name.indexOf(this.suffix);
			
			return (index >= 0) && (index == (name.length() - this.suffix.length()) );
		}
	}
	
	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("<directory> <suffix>");
			return;
		}
		
		File dir = new File(args[0]);
		
		if(!dir.isDirectory())
		{
			System.out.println(args[0] + " is not a directory.");
			return;
		}
		
		String[] files = dir.list(new SuffixFilter(args[1]));
		
		for(String s : files)
		{
			System.out.println("\t" + s);
		}
	}
}
