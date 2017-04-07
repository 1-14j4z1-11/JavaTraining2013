package ex02_09;

public class Vehicle
{
	// フィールド
	
	private static int nextID = 0;
	
	public int id = nextID;
	public String owner = "";
	public int speed = 0;
	public int angle = 0;
	
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
		Vehicle vehicle1 = new Vehicle();
		Vehicle vehicle2 = new Vehicle();
		Vehicle vehicle3 = new Vehicle();
		
		System.out.println(vehicle1);
		System.out.println(vehicle2);
		System.out.println(vehicle3);
		
		System.out.println("MaxID = " + Vehicle.getMaxID());
	}
}
