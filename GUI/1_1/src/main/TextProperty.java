package main;

import java.awt.*;

public class TextProperty
{
	// �t�B�[���h
	
	private Font font;
	private Color textColor;
	private Color backColor;
	
	// �R���X�g���N�^
	public TextProperty(Font font, Color textColor, Color backColor)
	{
		this.font = font;
		this.textColor = textColor;
		this.backColor = backColor;
	}
	
	// �A�N�Z�T
	
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
}
