package util;

import java.awt.*;
import clock.*;

public class DisplayProperty
{
	//static フィールド
	public static final ClockPanel[] CLOCK_PANELS = new ClockPanel[]
	{
		new DigitalClockPanel(),
		new FlipClockPanel(),
	};
	
	public static final MySize[] FRAME_SIZES = new MySize[]
	{
		new MySize(200, 35),
		new MySize(400, 70),
		new MySize(600, 115),
		new MySize(800, 150),
	};
	
	// フィールド
	private ClockPanel clock;
	private MySize size;
	private Color textColor;
	private Color foreColor;
	private Color backColor;
	private boolean changed;
	
	// コンストラクタ
	public DisplayProperty(ClockPanel clock, MySize size, Color textColor, Color foreColor, Color backColor)
	{
		this.clock = clock;
		this.size = size;
		this.textColor = textColor;
		this.foreColor = foreColor;
		this.backColor = backColor;
	}

	public DisplayProperty(DisplayProperty property)
	{
		this.clock = property.clock;
		this.size = new MySize(property.size);
		this.textColor = new Color(property.textColor.getRGB());
		this.foreColor = new Color(property.foreColor.getRGB());
		this.backColor = new Color(property.backColor.getRGB());
	}
	
	// アクセサ
	public ClockPanel getClock()
	{
		return this.clock;
	}

	public void setClock(ClockPanel clock)
	{
		this.clock = clock;
	}

	public MySize getSize()
	{
		return this.size;
	}

	public void setSize(MySize size)
	{
		this.size = size;
	}

	public Color getTextColor()
	{
		return this.textColor;
	}

	public void setTextColor(Color color)
	{
		this.textColor = color;
	}
	
	public Color getForeColor()
	{
		return this.foreColor;
	}

	public void setForeColor(Color color)
	{
		this.foreColor = color;
	}

	public Color getBackColor()
	{
		return this.backColor;
	}

	public void setBackColor(Color color)
	{
		this.backColor = color;
	}

	public boolean isChanged()
	{
		return this.changed;
	}

	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}
}
