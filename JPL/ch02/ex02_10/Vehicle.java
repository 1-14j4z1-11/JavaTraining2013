package ex02_10;

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
		Vehicle vehicle = new Vehicle();

		vehicle.owner = "Taro";
		vehicle.speed = 40;
		vehicle.angle = 20;
		
		System.out.println(vehicle.toString());
	}
}
