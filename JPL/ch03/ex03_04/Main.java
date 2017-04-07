package ex03_04;

public class Main
{
	public static void main(String[] args)
	{
		PassengerVehicle pv1 = new PassengerVehicle("Taro", 5);
		PassengerVehicle pv2 = new PassengerVehicle("Jiro", 4);
		
		pv1.setPassenger(3);
		pv2.setPassenger(4);

		System.out.println(pv1);
		System.out.println(pv2);
	}
}
