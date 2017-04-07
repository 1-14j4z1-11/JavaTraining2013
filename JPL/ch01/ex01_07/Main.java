package ex01_07;

public class Main {
	private static final int MAX_INDEX = 9;
	
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		String mark = null;
		
		System.out.println(MAX_INDEX + ": " + lo);
		
		for(int i = MAX_INDEX - 1; i >= 1; i--)
		{
			if(hi % 2 == 0) mark = "*";
			else mark = "";
			
			System.out.println(i + ": " + hi + mark);
			
			hi = hi + lo;
			lo = hi - lo;
		}
	}

}
