package ex01_12;

public class Main {
	private static final int MAX_INDEX = 9;
	
	public static void main(String[] args) {
		int lo = 0;
		int hi = 1;
		
		String[] numberStrings = new String[MAX_INDEX];
		
		for(int i = 0; i < numberStrings.length; i++)
		{
			numberStrings[i] = hi + ((hi % 2 == 0) ? "*" : "");
			
			hi = hi + lo;
			lo = hi - lo;
		}
		
		for(String str : numberStrings)
		{
			System.out.println(str);
		}
	}

}
