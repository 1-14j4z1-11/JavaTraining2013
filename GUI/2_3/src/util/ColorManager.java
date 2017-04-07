package util;

import java.awt.Color;

public class ColorManager
{
	private static final Color[] colors = new Color[]
	{
		new Color(0xFF, 0x00, 0x00),
		new Color(0x00, 0xFF, 0x00),
		new Color(0x00, 0x00, 0xFF),
		new Color(0xFF, 0xFF, 0x00),
		new Color(0x00, 0x00, 0x00),
		new Color(0xFF, 0xFF, 0xFF),
		new Color(0x80, 0x80, 0x80),
	};
	
	private static final String[] colorNames = new String[]
	{
		"赤色",
		"緑色",
		"青色",
		"黄色",
		"黒色",
		"白色",
		"灰色",
	};
	
	// フィールド
	
	int selectedIndex;
	
	// コンストラクタ
	
	public ColorManager(int selectedIndex)
	{
		this.selectedIndex = selectedIndex;
	}
	
	// メソッド
	
	public static int getTotal()
	{
		return colors.length;
	}
	
	public static String getColorNameAt(int index)
	{
		if((index < 0) || (index >= colorNames.length))
			throw new IndexOutOfBoundsException();
		
		return colorNames[index];
	}
	
	public static Color getColorAt(int index)
	{
		if((index < 0) || (index >= colors.length))
			throw new IndexOutOfBoundsException();
		
		return colors[index];
	}
	
	public int getSelectedIndex()
	{
		return this.selectedIndex;
	}
	
	public void setSelectedIndex(int selectedIndex)
	{
		if((selectedIndex < 0) || (selectedIndex >= colors.length))
			throw new IndexOutOfBoundsException();
		
		this.selectedIndex = selectedIndex;
	}
	
	public void SetSelectedIndex(String colorName)
	{
		for(int i = 0; i < colorNames.length; i++)
		{
			if(colorName.equals(colorNames[i]))
			{
				this.selectedIndex = i;
				return;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	public Color getSelectedColor()
	{
		return new Color(colors[this.selectedIndex].getRGB());
	}
	
	public String getSelectedColorName()
	{
		return colorNames[this.selectedIndex];
	}
}
