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
	
	// 基底クラスで初期化関数を定義
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
	// 派生クラスで初期化関数をオーバーライド
	@Override
	protected void initialize()
	{
		super.value = 100;
	}
}