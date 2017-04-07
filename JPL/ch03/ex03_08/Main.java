package ex03_08;

public class Main
{
	public static void main(String[] args)
	{
		PassengerVehicle pv1 = new PassengerVehicle("Taro", 5);
		
		pv1.changeSpeed(40);
		pv1.turn(Vehicle.TURN_LEFT, 20);
		pv1.setPassenger(3);

		PassengerVehicle pv2 = pv1.clone();

		System.out.println(pv1);
		System.out.println(pv2);
		System.out.println();

		pv2.setOwner("Jiro");
		pv2.changeSpeed(30);
		pv2.turn(Vehicle.TURN_RIGHT, 30);
		pv2.setPassenger(2);

		System.out.println(pv1);
		System.out.println(pv2);
	}
}
