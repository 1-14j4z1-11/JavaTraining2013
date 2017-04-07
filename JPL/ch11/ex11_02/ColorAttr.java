package ex11_02;

public class ColorAttr extends Attr<String>
{
	private ScreenColor myColor;
	
	public ColorAttr(String name, String value)
	{
		super(name, value);
		decodeColor();
	}

	public ColorAttr(String name)
	{
		this(name, "transparent");
	}
	
	public String setValue(String value)
	{
		String ret = super.setValue(value);
		decodeColor();
		return ret;
	}
	
	public ScreenColor setValue(ScreenColor value)
	{
		super.setValue(value.toString());
		ScreenColor old = myColor;
		myColor = value;
		return old;
	}
	
	public ScreenColor getColor()
	{
		return myColor;
	}

	private void decodeColor()
	{
		// TODO 自動生成されたメソッド・スタブ
		if(getValue() == null)
			myColor = null;
		else
			myColor = new ScreenColor(getValue());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((myColor == null) ? 0 : myColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ColorAttr other = (ColorAttr) obj;
		
		if (myColor == null)
		{
			if (other.myColor != null)
				return false;
		}
		else if (!myColor.equals(other.myColor))
			return false;
		
		return true;
	}

	
}

class ScreenColor
{
	public ScreenColor(Object value)
	{ }

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(this.getClass() != obj.getClass())
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return 1;
	}
}