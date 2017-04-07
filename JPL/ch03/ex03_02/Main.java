package ex03_02;

public class Main
{
	public static void main(String[] args)
	{
		Y y = new Y();
		y.mask(0x7777);
	}
}

class X
{
	protected int xMask = 0x00FF;
	protected int fullMask;
	
	public X()
	{
		fullMask = xMask;
		System.out.printf("ConstructorX : fullMask = %04X%n", fullMask);
	}
	
	public int mask(int orig)
	{
		return (orig & fullMask);
	}
}

class Y extends X
{
	protected int yMask = 0xFF00;
	
	public Y()
	{
		fullMask |= yMask;
		System.out.printf("ConstructorY : fullMask = %04X%n", fullMask);
	}
}