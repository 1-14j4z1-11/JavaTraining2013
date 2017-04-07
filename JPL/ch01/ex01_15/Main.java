package ex01_15;

public class Main {

	public static void main(String[] args) {
		SimpleLookup s = new SimpleLookup(5);

		s.add("One", new Integer(1));
		s.add("Two", new Integer(2));
		s.add("Three", new Integer(3));
		
		System.out.println(s);

		s.add("Four", new Integer(4));
		s.add("Two", new Integer(5));

		System.out.println(s);
		
		Integer i = (Integer)s.find("Two");

		if(i == null) System.out.println("find [Two] : null" );
		else System.out.println("find [Two] : " + i);
		
		s.remove("Two");
		s.remove("Five");

		System.out.println(s);
	}

}
