package ex22_04;

public class Main
{	
	public static void main(String[] args)
	{
		AttributedImpl attrs = new AttributedImpl();
		attrs.addObserver(new AttributedObserver());
		
		attrs.add(new Attr("A", 1));
		attrs.add(new Attr("B", 2));
		attrs.remove("A");
		attrs.add(new Attr("C", 3));
	}
}
