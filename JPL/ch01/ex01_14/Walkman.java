package ex01_14;

public class Walkman
{
	private String tape;
	private String earphone;

	public String getEarphone()
	{
		return earphone;
	}

	public void setEarphone(String earphone)
	{
		this.earphone = earphone;
	}

	public String getTape()
	{
		return tape;
	}

	public void setTape(String tape)
	{
		this.tape = tape;
	}
	
	public void play()
	{
		System.out.println("Play : " + this.tape + " : Listener " + this.earphone);
	}
	
	public void stop()
	{
		System.out.println("Stop");
	}
}
