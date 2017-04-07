package ex20_03;

public class XOREncryptor implements Encryptable
{
	private final int key;
	
	public XOREncryptor(int key)
	{
		this.key = key;
	}
	
	@Override
	public int encrypt(int data)
	{
		return (this.key ^ data);
	}

	@Override
	public int decrypt(int data)
	{
		return (this.key ^ data);
	}
}
