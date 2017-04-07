package ex02_13;

public class Vehicle
{
	// フィールド
	
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

	@Override
	public String toString()
	{
		String str = "ID : " + this.id
				+ "  所有者 : " + this.owner
				+ "  速度 : " + this.speed
				+ "  角度 : " + this.angle;
		
		return str;
	}
	
	// メイン関数
	
	public static void main(String[] args)
	{
		Vehicle vehicle = new Vehicle();
		
		vehicle.setOwner("Taro");
		
		System.out.println("ID : " + vehicle.getID());
		System.out.println("所有者 : " + vehicle.getOwner());
		System.out.println("速度 : " + vehicle.getSpeed());
		System.out.println("角度 : " + vehicle.getAngle());
	}
}
