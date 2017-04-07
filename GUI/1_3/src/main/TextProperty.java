package main;

import java.awt.*;

public class TextProperty
{
	// フィールド
	
	private Font font;
	private Color textColor;
	private Color backColor;
	private boolean changed;
	
	// コンストラクタ
	public TextProperty(Font font, Color textColor, Color backColor)
	{
		this.font = font;
		this.textColor = textColor;
		this.backColor = backColor;
	}

	public TextProperty(TextProperty textProperty)
	{
		this.font = new Font(
			textProperty.font.getFontName(),
			textProperty.font.getStyle(),
			textProperty.font.getSize());
		this.textColor = new Color(textProperty.getTextColor().getRGB());
		this.backColor = new Color(textProperty.getBackColor().getRGB());
	}
	
	// アクセサ
	
	public Font getFont()
	{
		return this.font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Color getTextColor()
	{
		return this.textColor;
	}

	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	public Color getBackColor()
	{
		return this.backColor;
	}

	public void setBackColor(Color backColor)
	{
		this.backColor = backColor;
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
