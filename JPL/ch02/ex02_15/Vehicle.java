package ex02_15;

public class Vehicle
{
	// �t�B�[���h
	
	private static int nextID = 0;
	
	private int id = nextID;
	private String owner = "";
	private int speed = 0;
	private int angle = 0;
	
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
	
	public int getID()
	{
		return this.id;
	}

	public String getOwner()
	{
		return this.owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public int getSpeed()
	{
		return this.speed;
	}

	public int getAngle()
	{
		return this.angle;
	}

	public void changeSpeed(int speed)
	{
		this.speed = speed;
	}

	public void stop()
	{
		this.speed = 0;
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
		
		vehicle.setOwner("Taro");
		vehicle.changeSpeed(60);
		
		System.out.println(vehicle);
		
		vehicle.stop();
		
		System.out.println(vehicle);
	}
}
