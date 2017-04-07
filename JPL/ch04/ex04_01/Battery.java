package ex04_01;

public class Battery implements EnergySource
{
	int battery;
	
	@Override
	public boolean empty()
	{
		return (battery == 0);
	}
	
	public void charge()
	{
		battery = 10;
	}
	
	@Override
	public String toString()
	{
		return ("Battery:" + battery);
	}
}
