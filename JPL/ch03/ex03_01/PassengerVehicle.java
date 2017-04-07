package ex03_01;

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
		
		str += ", 座席数:" + sheet;
		str += ", 乗車人数:" + passenger;
		
		return str;
	}
}
