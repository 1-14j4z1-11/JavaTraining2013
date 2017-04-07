package ex03_04;

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

	// final
	public final int getSheet()
	{
		return sheet;
	}

	// final
	public final int getPassenger()
	{
		return passenger;
	}

	// final
	public final void setPassenger(int passenger)
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
