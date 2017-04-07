package ex06_05;

public class Color
{
	public static final int RED = 0x00FF0000;
	public static final int YELLOW = 0x00FFFF00;
	public static final int BLUE = 0x000000FF;
	
	int color;
	
	public Color(int color)
	{
		this.color = color;
	}
	
	@Override
	public String toString()
	{
		return String.format("R:%X, G:%X, B:%X",
			(color >>> 16) & 0xFF,
			(color >>> 8) & 0xFF,
			color& 0xFF);
	}
}
