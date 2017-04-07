package ex02_03;

public class Vehicle
{
	// フィールド
	
	public static int nextID;
	
	public int id;
	public String owner;
	public int speed;
	public int angle;
	
	// メソッド
	
	@Override
	public String toString()
	{
		String str = "ID : " + this.id
				+ "  所有者 : " + this.owner
				+ "  速度 : " + this.speed
				+ "  角度 : " + this.angle;
		
		return str;
	}
	
	// メソッド
	
	public static void main(String[] args)
	{
		Vehicle vehicle = new Vehicle();
		
		vehicle.id = Vehicle.nextID++;
		vehicle.owner = "Taro";
		vehicle.speed = 40;
		vehicle.angle = 20;
		
		System.out.println(vehicle);
	}
}
