package ex01_06;

public class Main {
	private static final String TITLE = "<50�����̃t�B�{�i�b�`����>";
	
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		
		System.out.println(TITLE);
		System.out.println(lo);
		
		while(hi < 50)
		{
			System.out.println(hi);
			
			hi = hi + lo;
			lo = hi - lo;
		}
	}

}
