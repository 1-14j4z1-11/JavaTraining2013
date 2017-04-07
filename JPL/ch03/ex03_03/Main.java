package ex03_03;

public class Main
{
	public static void main(String[] args)
	{
		Base base = new Base();
		Derived derived = new Derived();
		
		System.out.println("Base : " + base);
		System.out.println("Derived : " + derived);
	}
}

class Base
{
	protected int value;
	
	public Base()
	{
		initialize();
	}
	
	// ���N���X�ŏ������֐����`
	protected void initialize()
	{
		value = 0;
	}
	
	@Override
	public String toString()
	{
		return "value = " + value;
	}
}

class Derived extends Base
{
	// �h���N���X�ŏ������֐����I�[�o�[���C�h
	@Override
	protected void initialize()
	{
		super.value = 100;
	}
}