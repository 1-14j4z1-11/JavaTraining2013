package ex20_03;

import java.io.*;

public class DecryptInputStream extends FilterInputStream
{
	Encryptable decryptor;
	
	public DecryptInputStream(InputStream stream, Encryptable decryptor)
	{
		super(stream);
		
		this.decryptor = decryptor;
	}
	
	@Override
	public int read() throws IOException
	{
		int data = super.read();
		
		return (data != -1) ? this.decryptor.decrypt(data) : -1;
	}

	@Override
	public int read(byte[] buf, int offset, int count) throws IOException
	{
		int nread = super.read(buf, offset, count);
		int last = offset + nread;
		
		for(int i = offset; i < last; i++)
		{
			buf[i] = (byte)this.decryptor.decrypt(buf[i]);
		}
		
		return nread;
	}
}
