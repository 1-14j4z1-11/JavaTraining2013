package ex02_05;

public class Vehicle
{
	// フィールド
	
	public static int nextID;
	
	public int id;
	public String owner;
	public int speed;
	public int angle;
	
	// メイン関数
	
	public static void main(String[] args)
	{
		Vehicle vehicle1 = new Vehicle();
		Vehicle vehicle2 = new Vehicle();
		Vehicle vehicle3 = new Vehicle();
		
		vehicle1.id = Vehicle.nextID++;
		vehicle1.owner = "Taro";
		vehicle1.speed = 40;
		vehicle1.angle = 20;

		vehicle2.id = Vehicle.nextID++;
		vehicle2.owner = "Hanako";
		vehicle2.speed = 60;
		vehicle2.angle = 0;

		vehicle3.id = Vehicle.nextID++;
		vehicle3.owner = "Jiro";
		vehicle3.speed = 50;
		vehicle3.angle = 50;
		
		System.out.println("ID : " + vehicle1.id
				+ "  所有者 : " + vehicle1.owner
				+ "  速度 : " + vehicle1.speed
				+ "  角度 : " + vehicle1.angle);

		System.out.println("ID : " + vehicle2.id
				+ "  所有者 : " + vehicle2.owner
				+ "  速度 : " + vehicle2.speed
				+ "  角度 : " + vehicle2.angle);
		
		System.out.println("ID : " + vehicle3.id
				+ "  所有者 : " + vehicle3.owner
				+ "  速度 : " + vehicle3.speed
				+ "  角度 : " + vehicle3.angle);
	}
}
