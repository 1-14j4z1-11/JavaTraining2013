package ex02_06;

public class Vehicle
{
	// �t�B�[���h
	
	public static int nextID;
	
	public int id;
	public String owner;
	public int speed;
	public int angle;
	
	// ���\�b�h
	
	@Override
	public String toString()
	{
		String str = "ID : " + this.id
				+ "  ���L�� : " + this.owner
				+ "  ���x : " + this.speed
				+ "  �p�x : " + this.angle;
		
		return str;
	}
}
