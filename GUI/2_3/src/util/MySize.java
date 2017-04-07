package util;

import java.awt.Dimension;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySize extends Dimension
{
	private static final long serialVersionUID = 1L;
	
	// コンストラクタ
	public MySize(MySize size)
	{
		this(size.width, size.height);
	}
	
	public MySize(int width, int height)
	{
		super(width, height);
	}

	@Override
	public String toString()
	{
		return ((int)this.getWidth()) + "x" + ((int)this.getHeight());
	}

	public static MySize parse(String str)
	{
		Matcher matcher = Pattern.compile("(\\d+)\\s*.\\s*(\\d+)").matcher(str);
		
		if(!matcher.matches())
		{
			throw new IllegalArgumentException();
		}
		
		int width = Integer.parseInt(matcher.group(1));
		int height = Integer.parseInt(matcher.group(2));
		
		return new MySize(width, height);
	}
}
