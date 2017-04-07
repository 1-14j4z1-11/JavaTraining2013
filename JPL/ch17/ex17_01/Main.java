package ex17_01;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("FreeMemory : " + Runtime.getRuntime().freeMemory());
		
		@SuppressWarnings("unused")
		byte[] array = new byte[1000000];
		
		System.out.println("Created");
		System.out.println("FreeMemory : " + Runtime.getRuntime().freeMemory());
	}
}
