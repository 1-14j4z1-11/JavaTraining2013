package ex02_03;

public class Vehicle
{
	// �t�B�[���h
	
	public static int nextID;
	
	public int id;
	public String owner;
	public int speed;
	public int angle;
	
	// ���\�b�h
	
	@Override
	public String toString()
	{
		String str = "ID : " + this.id
				+ "  ���L�� : " + this.owner
				+ "  ���x : " + this.speed
				+ "  �p�x : " + this.angle;
		
		return str;
	}
	
	// ���\�b�h
	
	public static void main(String[] args)
	{
		Vehicle vehicle = new Vehicle();
		
		vehicle.id = Vehicle.nextID++;
		vehicle.owner = "Taro";
		vehicle.speed = 40;
		vehicle.angle = 20;
		
		System.out.println(vehicle);
	}
}
