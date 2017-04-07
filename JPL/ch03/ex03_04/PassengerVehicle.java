package ex03_04;

public class PassengerVehicle extends Vehicle
{
	// フィールド
	
	final int sheet;
	int passenger = 0;
	
	// コンストラクタ
	
	public PassengerVehicle(String owner, int sheet)
	{
		super(owner);
		this.sheet = sheet;
	}
	
	// メソッド

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
		
		str += ", 座席数:" + sheet;
		str += ", 乗車人数:" + passenger;
		
		return str;
	}
}
