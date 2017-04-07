package ex02_01;

public class Vehicle
{
	// フィールド
	
	public String owner;
	public int speed;
	public int angle;
	
	// メソッド
	
	@Override
	public String toString()
	{
		String str = "所有者 : " + this.owner
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
		
		System.out.println(vehicle);
	}
}
