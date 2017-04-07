package ex02_07;

public class Vehicle
{
	// �t�B�[���h
	
	private static int nextID = 0;
	
	public int id = nextID;
	public String owner = "";
	public int speed = 0;
	public int angle = 0;
	
	// �R���X�g���N�^
	
	public Vehicle()
	{
		nextID++;
	}

	public Vehicle(String owner)
	{
		nextID++;
		this.owner = owner;
	}

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
	
	// ���C���֐�
	
	public static void main(String[] args)
	{
		Vehicle vehicle1 = new Vehicle();
		Vehicle vehicle2 = new Vehicle("Taro");

		vehicle1.owner = "Taro";
		vehicle1.speed = 40;
		vehicle1.angle = 20;
		
		vehicle2.speed = 40;
		vehicle2.angle = 20;

		System.out.println(vehicle1);
		
		System.out.println(vehicle2);
	}
}
