package ex02_01;

public class Vehicle
{
	// �t�B�[���h
	
	public String owner;
	public int speed;
	public int angle;
	
	// ���\�b�h
	
	@Override
	public String toString()
	{
		String str = "���L�� : " + this.owner
				+ "  ���x : " + this.speed
				+ "  �p�x : " + this.angle;
		
		return str;
	}
	
	// ���C���֐�
	
	public static void main(String[] args)
	{
		Vehicle vehicle = new Vehicle();
		
		vehicle.owner = "Taro";
		vehicle.speed = 40;
		vehicle.angle = 20;
		
		System.out.println(vehicle);
	}
}
