package ex02_05;

public class Vehicle
{
	// �t�B�[���h
	
	public static int nextID;
	
	public int id;
	public String owner;
	public int speed;
	public int angle;
	
	// ���C���֐�
	
	public static void main(String[] args)
	{
		Vehicle vehicle1 = new Vehicle();
		Vehicle vehicle2 = new Vehicle();
		Vehicle vehicle3 = new Vehicle();
		
		vehicle1.id = Vehicle.nextID++;
		vehicle1.owner = "Taro";
		vehicle1.speed = 40;
		vehicle1.angle = 20;

		vehicle2.id = Vehicle.nextID++;
		vehicle2.owner = "Hanako";
		vehicle2.speed = 60;
		vehicle2.angle = 0;

		vehicle3.id = Vehicle.nextID++;
		vehicle3.owner = "Jiro";
		vehicle3.speed = 50;
		vehicle3.angle = 50;
		
		System.out.println("ID : " + vehicle1.id
				+ "  ���L�� : " + vehicle1.owner
				+ "  ���x : " + vehicle1.speed
				+ "  �p�x : " + vehicle1.angle);

		System.out.println("ID : " + vehicle2.id
				+ "  ���L�� : " + vehicle2.owner
				+ "  ���x : " + vehicle2.speed
				+ "  �p�x : " + vehicle2.angle);
		
		System.out.println("ID : " + vehicle3.id
				+ "  ���L�� : " + vehicle3.owner
				+ "  ���x : " + vehicle3.speed
				+ "  �p�x : " + vehicle3.angle);
	}
}
