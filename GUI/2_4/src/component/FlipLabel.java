package component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import util.GradationDecorator;

public class FlipLabel extends JLabel
{
	private static final long serialVersionUID = 1L;

	private static final int MAX_FLIP_STEP = 10;
	private static final int INTERVAL = 25;
	
	private int count = 0;
	private Boolean incrementFlag = false;
	private String current = "";
	private String previous = "";

	GradationDecorator decorator = new GradationDecorator();

	private Color lineColor = Color.BLACK;
	private Timer timer = new Timer(INTERVAL, new ActionLister_Timer());

	public FlipLabel()
	{
		this.timer.start();
	}
	
	@Override
	public final void setText(String text)
	{
		super.setText(text);
		
		if(this.current == null)
		{
			this.current = "";
		}
		
		if(this.current.equals(this.previous))
		{
			this.previous = this.current;
			this.current = text;
			this.count = 0;
		}
	}
	
	public final Color getLineColor()
	{
		return this.lineColor;
	}
	
	public final void setLineColor(Color color)
	{
		this.lineColor = color;
	}
	
	@Override
	public void paint(Graphics g)
	{
		synchronized(this.incrementFlag)
		{
			int width = this.getWidth();
			int height = this.getHeight();
			Point offset = new Point(width / 7, height * 4 / 5);
			
			BufferedImage curImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			BufferedImage prevImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics curGraphics = curImage.getGraphics();
			Graphics prevGraphics = prevImage.getGraphics();
			
			if(this.incrementFlag && !this.current.equals(this.previous))
			{
				if(++this.count >= MAX_FLIP_STEP)
				{
					this.count = 0;
					this.previous = this.current;
				}
				
				this.incrementFlag = false;
			}

			// è„
			curGraphics.setFont(this.getFont());
			curGraphics.setColor(this.getBackground());
			curGraphics.fillRect(0, 0, width, height);
			curGraphics.setColor(this.getForeground());
			curGraphics.drawString(this.current, offset.x, offset.y);

			// â∫
			prevGraphics.setFont(this.getFont());
			prevGraphics.setColor(this.getBackground());
			prevGraphics.fillRect(0, 0, width, height);
			prevGraphics.setColor(this.getForeground());
			prevGraphics.drawString(this.previous, offset.x, offset.y);
			
			// ï`âÊï‚ê≥
			curImage = decorator.decorate(curImage);
			prevImage = decorator.decorate(prevImage);
			
			// ï`âÊ
			g.drawImage(curImage, 0, 0, width, height / 2, 0, 0, width, height / 2, this);
			g.drawImage(prevImage, 0, height / 2, width, height, 0, height / 2, width, height, this);
			
			// âÒì]
			int y = (int)(-Math.cos(this.count * Math.PI / MAX_FLIP_STEP) * height / 2) + height / 2;
			
			if(y <= height / 2)
			{
				g.drawImage(prevImage, 0, y, width, height / 2, 0, 0, width, height / 2, this);
			}
			else
			{
				g.drawImage(curImage, 0, height / 2, width, y, 0, height / 2, width, height, this);
			}
			
			// äOògÇÃï`âÊ
			g.setColor(new Color(this.getBackground().getRed() * 4 / 5 + 25, this.getBackground().getGreen() * 4 / 5 + 25, this.getBackground().getBlue() * 4 / 5 + 25));
			g.drawRect(0, 0, width - 1, height - 1);
			
			//g.setColor(this.getLineColor());
			//g.drawRect(0, 0, width - 1, height - 1);
			//g.drawLine(0, height / 2, width, height / 2);
			//g.drawLine(0, y, width, y);
		}
	}
	
	private class ActionLister_Timer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			incrementFlag = true;
			repaint();
		}
	}
}
