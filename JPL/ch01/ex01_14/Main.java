package ex01_14;

public class Main
{
	public static void main(String[] args)
	{
		CommunicatableWalkman obj = new CommunicatableWalkman();
		
		obj.setTape("Test");
		obj.setEarphone("Taro");
		obj.setSubEarphone("Jiro");
		
		obj.play();
		obj.talk("Hello");
		obj.stop();
	}
}
