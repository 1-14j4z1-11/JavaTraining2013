package ex03_06;

public class Main
{
	public static void main(String[] args)
	{
		GasTank gasTank = new GasTank();
		Battery battery = new Battery();
		
		Vehicle v1 = new Vehicle("Taro", gasTank);
		Vehicle v2 = new Vehicle("Jiro", battery);
		
		gasTank.charge();
		
		v1.start();
		v2.start();
		
		System.out.println(v1);
		System.out.println(v2);
	}
}
