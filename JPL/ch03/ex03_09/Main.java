package ex03_09;

public class Main
{
	public static void main(String[] args)
	{
		Garage g1 = new Garage();
		
		Vehicle v1 = new Vehicle("Taro");
		v1.changeSpeed(10);

		Vehicle v2 = new Vehicle("Jiro");
		v2.changeSpeed(20);

		Vehicle v3 = new Vehicle("Taro");
		v3.changeSpeed(40);
		
		g1.add(v1);
		g1.add(v2);
		g1.add(v3);
		
		Garage g2 = g1.clone();
		
		g2.getAt(0).stop();
		g2.getAt(1).stop();
		g2.getAt(2).stop();
		
		System.out.println(g1);
		System.out.println(g2);
	}
}
