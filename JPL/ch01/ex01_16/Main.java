package ex01_16;

import java.io.FileInputStream;
import java.io.IOException;

class BadDataSetException extends Exception
{
	// フィールド

	private static final long serialVersionUID = 1L;
	
	private IOException ioException = null;
	private String dataSetName = null;
	
	
	// コンストラクタ
	
	public BadDataSetException()
	{ }
	
	public BadDataSetException(IOException ioException, String dataSetName)
	{
		this.ioException = ioException;
		this.dataSetName = dataSetName;
	}
	
	
	// メソッド
	
	public IOException getIOException()
	{
		return ioException;
	}
	
	public String getDataSetName()
	{
		return dataSetName;
	}
}

class MyUtilities
{
	public double[] getDataSet(String setName) throws BadDataSetException
	{
		String file = setName + ".dset";
		FileInputStream in = null;
		
		try
		{
			in = new FileInputStream(file);
			return readDataSet(in);
		}
		catch(IOException e)
		{
			throw new BadDataSetException(e, setName);
		}
		finally
		{
			try
			{
				if(in != null)
				{
					in.close();
				}
			}
			catch(IOException e)
			{ }
		}
	}
	
	private static double[] readDataSet(FileInputStream in)
	{
		if(in == null)
		{
			return null;
		}
		else
		{
			return new double[] { 0.0 };
		}
	}
}

public class Main
{
	public static void main(String[] args)
	{
		MyUtilities util = new MyUtilities();
		
		try
		{
			util.getDataSet("Test");
		}
		catch (BadDataSetException e)
		{
			System.out.println("データセット:" + e.getDataSetName());
			System.out.println("IOException:" + e.getIOException().getMessage());
		}
	}
}
