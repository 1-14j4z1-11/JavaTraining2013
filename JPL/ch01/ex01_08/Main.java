package ex01_08;

public class Main {

	public static void main(String[] args) {
		Point src = new Point(1.0, 2.0);
		Point dst = new Point();

		System.out.println("<•ÏX‘O>");
		System.out.println("src: " + src);
		System.out.println("dst: " + dst);
		
		src.toPoint(dst);

		System.out.println("<•ÏXŒã>");
		System.out.println("src: " + src);
		System.out.println("dst: " + dst);		
	}

}
