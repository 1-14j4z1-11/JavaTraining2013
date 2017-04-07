package ex20_09;

import java.io.*;

public class Main
{
	private static void printFileInfo(File file)
	{
		if(file == null)
		{
			throw new NullPointerException();
		}
		
		System.out.println("File : " + file.getName());
		System.out.println("    AbsolutePath : " + file.getAbsolutePath());
		System.out.println("    LastModified : " + file.lastModified());
		System.out.println("    FileSize : " + file.length());
		System.out.println("    Hidden : " + file.isHidden());
		System.out.println("    Read : " + file.canRead());
		System.out.println("    Write : " + file.canWrite());
		System.out.println("    Exe : " + file.canExecute());
		System.out.println("    Parent : " + file.getParent());
	}
	
	public static void main(String[] args)
	{
		for(String s : args)
		{
			printFileInfo(new File(s));
		}
	}
}
