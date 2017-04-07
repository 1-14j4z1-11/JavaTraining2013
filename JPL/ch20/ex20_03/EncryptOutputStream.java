package ex20_03;

import java.io.*;

public class EncryptOutputStream extends FilterOutputStream
{
	Encryptable encryptor;
	
	public EncryptOutputStream(OutputStream stream, Encryptable encryptor)
	{
		super(stream);
		
		this.encryptor = encryptor;
	}
	
	@Override
	public void write(int data) throws IOException
	{
		super.write(this.encryptor.encrypt(data));
	}
}
