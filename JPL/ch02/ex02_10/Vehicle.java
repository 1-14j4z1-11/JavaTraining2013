package ex02_10;

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
	
	public static int getMaxID()
	{
		return nextID - 1;
	}
	
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
		Vehicle vehicle = new Vehicle();

		vehicle.owner = "Taro";
		vehicle.speed = 40;
		vehicle.angle = 20;
		
		System.out.println(vehicle.toString());
	}
}
