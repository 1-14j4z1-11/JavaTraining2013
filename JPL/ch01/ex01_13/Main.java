package ex01_13;

public class Main {
	private static final int MAX_INDEX = 9;
	
	public static void main(String[] args) {
		int lo = 0;
		int hi = 1;
		
		NumberItem[] numbers = new NumberItem[MAX_INDEX];
		
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = new NumberItem(hi, (hi % 2 == 0));
			
			hi = hi + lo;
			lo = hi - lo;
		}
		
		for(NumberItem item : numbers)
		{
			if(item.getIsEven()) System.out.printf("%d*\n", item.getNumber());
			else System.out.printf("%d\n", item.getNumber());
		}
	}

}
