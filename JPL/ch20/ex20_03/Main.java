package ex20_03;

import java.io.*;

public class Main
{
	private static final int KEY = 0x0F;

	public static void main(String[] args)
	{
		Encryptable encryptor = new XOREncryptor(KEY);
		EncryptOutputStream eout = new EncryptOutputStream(System.out, encryptor);
		DecryptInputStream din = new DecryptInputStream(System.in, encryptor);
		InputStreamReader reader = new InputStreamReader(din);
		OutputStreamWriter writer = new OutputStreamWriter(eout);
		
		String text = "abcdefghijk";
		
		try
		{
			char[] buf = new char[16];
			
			writer.write(text.toCharArray());
			writer.flush();
			System.out.println();
			
			reader.read(buf);
			
			System.out.println(buf);
			
			reader.close();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
