package ex01_04;

public class Main {

	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		
		System.out.println("<50�����̃t�B�{�i�b�`����>");
		System.out.println(lo);
		
		while(hi < 50)
		{
			System.out.println(hi);
			
			hi = hi + lo;
			lo = hi - lo;
		}
	}

}
