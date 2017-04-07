package ex01_14;

public class TwoEarphoneWalkman extends Walkman
{
	private String subEarphone;

	public String getSubEarphone()
	{
		return subEarphone;
	}
	
	public void setSubEarphone(String subEarphone)
	{
		this.subEarphone = subEarphone;
	}
	
	@Override
	public void play()
	{
		System.out.println("Play : " + this.getTape()
			+ " : Listener " + this.getEarphone() + ", " + this.subEarphone);
	}
}
