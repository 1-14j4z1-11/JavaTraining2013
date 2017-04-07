package ex03_08;

public class Vehicle implements Cloneable
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
		
		return str;
	}
	
	@Override
	public Vehicle clone()
	{
		try
		{
			Vehicle v = (Vehicle)super.clone();
			v.id = id;
			v.owner = owner;
			v.speed = speed;
			v.angle = angle;
			return v;
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError("Vehicle");
		}
	}
}
