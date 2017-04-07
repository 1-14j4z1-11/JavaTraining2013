package ex06_05;

public enum Signal
{
	RED
	{
		Color getColor() { return new Color(Color.RED); }
	},
	Yellow
	{
		Color getColor() { return new Color(Color.YELLOW); }
	},
	Blue
	{
		Color getColor() { return new Color(Color.BLUE); }
	};
	
	abstract Color getColor();
}
