package ex04_01;

public class Vehicle
{
	// �t�B�[���h

	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;
	
	private static int nextID = 0;
	
	private int id = nextID;
	private String owner = "<None>";
	private int speed;
	private int angle;
	private EnergySource energy;
	
	// �R���X�g���N�^
	
	public Vehicle(EnergySource energy)
	{
		nextID++;
		this.energy = energy;
	}

	public Vehicle(String owner, EnergySource energy)
	{
		nextID++;
		this.owner = owner;
		this.energy = energy;
	}
	
	// ���\�b�h
	
	public static int getMaxID()
	{
		return nextID - 1;
	}
	
	// final
	public final int getID()
	{
		return id;
	}

	// final
	public final String getOwner()
	{
		return owner;
	}

	// final
	public final void setOwner(String owner)
	{
		this.owner = owner;
	}

	// final
	public final int getSpeed()
	{
		return speed;
	}

	// final
	public final int getAngle()
	{
		return angle;
	}

	// final
	public final void changeSpeed(int speed)
	{
		this.speed = speed;
	}

	// final
	public final void stop()
	{
		speed = 0;
	}
	
	public void start()
	{
		if(!energy.empty())
			changeSpeed(30);
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
		String str = "ID:" + id
				+ ", ���L��:" + owner
				+ ", ���x:" + speed;
		
		if(angle > 0)
		{
			str += ", �p�x:[��]" + angle;
		}
		else if(angle < 0)
		{
			str += ", �p�x:[�E]" + (-angle);
		}
		else
		{
			str += ", �p�x:0";
		}
		
		str += ", ����:" + energy.toString();
		
		return str;
	}
}
