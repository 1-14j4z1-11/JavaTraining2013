package ex02_18;

public class Vehicle
{
	// �t�B�[���h

	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;
	
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
	
	public void turn(int cmd, int angle)
	{
		switch(cmd)
		{
		case TURN_LEFT:
			this.angle = angle;
			break;
			
		case TURN_RIGHT:
			this.angle = -angle;
			break;
			
		default:
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String toString()
	{
		String str = "ID : " + this.id
				+ "  ���L�� : " + this.owner
				+ "  ���x : " + this.speed;
		
		if(angle > 0)
		{
			str += "  �p�x : [��]" + this.angle;
		}
		else if(angle < 0)
		{
			str += "  �p�x : [�E]" + (-this.angle);
		}
		else
		{
			str += "  �p�x : 0";
		}
		
		return str;
	}
	
	// ���C���֐�
	
	public static void main(String[] args)
	{
		if(args.length <= 0)
		{
			return;
		}
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.setOwner(args[0]);
		vehicle.changeSpeed(60);
		vehicle.turn(TURN_LEFT, 20);
		
		System.out.println(vehicle);
	}
}
