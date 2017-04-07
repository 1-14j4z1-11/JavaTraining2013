package ex03_07;

public class Main
{
	public static void main(String[] args)
	{
		ColorAttr ca1 = new ColorAttr("ca", "Red");
		ColorAttr ca2 = new ColorAttr("ca", "Red");
		
		if(ca1.equals(ca2))
			System.out.println("ca1 == ca2");
		else
			System.out.println("ca1 != ca2");
		
		System.out.println("ca1.hashCode() = " + ca1.hashCode());
		System.out.println("ca2.hashCode() = " + ca2.hashCode());
	}
}
