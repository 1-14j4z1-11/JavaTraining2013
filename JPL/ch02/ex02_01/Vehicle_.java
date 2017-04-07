package ex02_01;

public class Vehicle_
{
	// �t�B�[���h
	
	private String owner;
	private int speed;
	private int angle;
	
	// �R���X�g���N�^
	
	public Vehicle_(String owner)
	{
		this.owner = owner;
	}

	public Vehicle_(String owner, int speed, int angle)
	{
		this.owner = owner;
		this.speed = speed;
		this.angle = angle;
	}
	
	// ���\�b�h
	
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

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getAngle()
	{
		return this.angle;
	}

	public void setAngle(int angle)
	{
		this.angle = angle;
	}
	
	@Override
	public String toString()
	{
		String str = "���L�� : " + owner
			+ "  ���x : " + speed
			+ "  �p�x : " + angle;
		
		return str;
	}
}
