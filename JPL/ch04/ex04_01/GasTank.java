package ex04_01;

public class GasTank implements EnergySource
{
	double gas;
	
	@Override
	public boolean empty()
	{
		return (gas == 0.0);
	}
	
	public void charge()
	{
		gas = 10.0;
	}
	
	@Override
	public String toString()
	{
		return ("Gas:" + gas);
	}
}
