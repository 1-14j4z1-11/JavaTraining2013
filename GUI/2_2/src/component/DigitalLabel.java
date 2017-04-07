package component;

import java.awt.*;
import javax.swing.*;

public class DigitalLabel extends JLabel
{
	private enum Orientation
	{
		VERTICAL, HORIZONTAL,
	}
	
	private static final long serialVersionUID = 1L;
	
	private int number;
	private int lineWidth = 1;
	
	public void setNumber(int number)
	{
		this.number = number;
		this.repaint();
	}
	
	public int getLineWidth()
	{
		return lineWidth;
	}
	
	public void setLineWidth(int width)
	{
		this.lineWidth = width;
	}
	
	@Deprecated
	@Override
	public void setText(String text)
	{
		super.setText(text);
	}
	
	@Override
	public void paint(Graphics g)
	{
		int length = (int)Math.min(this.getWidth(), this.getHeight() / 2) - lineWidth * 2 - 1;
		length = length * 4 / 5;
		
		lineWidth++;
		
		Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
		Point top = new Point(center.x, center.y - length - lineWidth * 2);
		Point bottom =  new Point(center.x, center.y + length + lineWidth * 2);
		Point leftU = new Point(center.x - length / 2 - lineWidth, center.y - length / 2 - lineWidth);
		Point rightU = new Point(center.x + length / 2 + lineWidth, center.y - length / 2 - lineWidth);
		Point leftD = new Point(center.x - length / 2 - lineWidth, center.y + length / 2 + lineWidth);
		Point rightD = new Point(center.x + length / 2 + lineWidth, center.y + length / 2 + lineWidth);

		lineWidth--;
		
		Color foreSegment = this.getForeground();
		Color backSegment = this.getBackground();
		
		switch(number % 10)
		{
			case 0:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			case 1:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, backSegment);
				break;
			
			case 2:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			case 3:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			case 4:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, backSegment);
				break;
			
			case 5:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, backSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			case 6:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, backSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			case 7:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, backSegment);
				break;
			
			case 8:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			case 9:
				drawSegment(g, Orientation.HORIZONTAL, top, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, rightU, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, center, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.VERTICAL, leftD, length, lineWidth, backSegment);
				drawSegment(g, Orientation.VERTICAL, rightD, length, lineWidth, foreSegment);
				drawSegment(g, Orientation.HORIZONTAL, bottom, length, lineWidth, foreSegment);
				break;
			
			default:
				break;
		}
	}
	
	private static void drawSegment(Graphics g, Orientation orientation, Point center, int length, int lineWidth, Color color)
	{
		color = new Color((int)(color.getRed() * 0.8 + 51), (int)(color.getGreen() * 0.8 + 51), (int)(color.getBlue() * 0.8 + 51));
		drawBaseSegment(g, orientation, center, length, lineWidth, color);
		
		color = new Color((int)(color.getRed() * 0.8), (int)(color.getGreen() * 0.8), (int)(color.getBlue() * 0.8));
		drawBaseSegment(g, orientation, center, length, lineWidth - 2, color);
	}
	
	private static void drawBaseSegment(Graphics g, Orientation orientation, Point center, int length, int lineWidth, Color color)
	{
		int begX, begY;
		
		length += lineWidth * 2;
		g.setColor(color);
		
		switch(orientation)
		{
			case HORIZONTAL:
				begX = center.x - length / 2;
				begY = center.y;
				
				for(int i = 0; i < lineWidth / 2; i++)
				{
					g.drawLine(begX + i, begY - i, begX + length - i, begY - i);
					g.drawLine(begX + i, begY + i, begX + length - i, begY + i);
				}
				break;

			case VERTICAL:
				begX = center.x;
				begY = center.y - length / 2;
				
				for(int i = 0; i < lineWidth / 2; i++)
				{
					g.drawLine(begX - i, begY + i, begX - i, begY + length - i);
					g.drawLine(begX + i, begY + i, begX + i, begY + length - i);
				}
				break;
				
			default:
				throw new AssertionError();
		}
	}
}
