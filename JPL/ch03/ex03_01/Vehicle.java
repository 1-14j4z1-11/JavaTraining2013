package ex03_01;

public class Vehicle
{
	// フィールド

	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;
	
	private static int nextID = 0;
	
	private int id = nextID;
	private String owner = "";
	private int speed = 0;
	private int angle = 0;
	
	// コンストラクタ
	
	public Vehicle()
	{
		nextID++;
	}

	public Vehicle(String owner)
	{
		nextID++;
		this.owner = owner;
	}
	
	// メソッド
	
	public static int getMaxID()
	{
		return nextID - 1;
	}
	
	public int getID()
	{
		return id;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getAngle()
	{
		return angle;
	}

	public void changeSpeed(int speed)
	{
		this.speed = speed;
	}

	public void stop()
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
				+ ", 所有者:" + owner
				+ ", 速度:" + speed;
		
		if(angle > 0)
		{
			str += ", 角度:[左]" + angle;
		}
		else if(angle < 0)
		{
			str += ", 角度:[右]" + (-angle);
		}
		else
		{
			str += ", 角度:0";
		}
		
		return str;
	}
}
