package ex03_01;

public class PassengerVehicle extends Vehicle
{
	// �t�B�[���h
	
	final int sheet;
	int passenger = 0;
	
	// �R���X�g���N�^
	
	public PassengerVehicle(String owner, int sheet)
	{
		super(owner);
		this.sheet = sheet;
	}
	
	// ���\�b�h
	
	public int getSheet()
	{
		return sheet;
	}
	
	public int getPassenger()
	{
		return passenger;
	}
	
	public void setPassenger(int passenger)
	{
		this.passenger = passenger;
	}
	
	@Override
	public String toString()
	{
		String str = super.toString();
		
		str += ", ���Ȑ�:" + sheet;
		str += ", ��Ԑl��:" + passenger;
		
		return str;
	}
}
