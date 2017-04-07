package ex20_02;

import java.io.*;

public class TranslateFilterReader extends FilterReader
{
	private char from;
	private char to;
	
	public TranslateFilterReader(Reader reader, char from, char to)
	{
		super(reader);
		
		this.from = from;
		this.to = to;
	}
	
	@Override
	public int read() throws IOException
	{
		int data = super.read();
		
		return (data == from) ? to : data;
	}
	
}
