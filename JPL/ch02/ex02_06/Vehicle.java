package ex02_06;

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
}
