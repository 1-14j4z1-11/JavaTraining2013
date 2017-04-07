package ex09_01;

public class Main
{
	public static void main(String[] args)
	{
		float posInf = Float.POSITIVE_INFINITY;
		float negInf = Float.NEGATIVE_INFINITY;
		
		System.out.println(posInf + negInf);
		System.out.println(posInf - negInf);
		System.out.println(posInf * negInf);
		System.out.println(posInf / negInf);

		System.out.println();
		
		System.out.println(posInf + posInf);
		System.out.println(posInf - posInf);
		System.out.println(posInf * posInf);
		System.out.println(posInf / posInf);
	}
}
