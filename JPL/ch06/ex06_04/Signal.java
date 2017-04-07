package ex06_04;

public enum Signal
{
	RED(new Color(Color.RED)),
	Yellow(new Color(Color.YELLOW)),
	Blue(new Color(Color.BLUE));
	
	Color color;
	Signal(Color color) { this.color = color; }
	
	public Color getColor() { return color; }
}
