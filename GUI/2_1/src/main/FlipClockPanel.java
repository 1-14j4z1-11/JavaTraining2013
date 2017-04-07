package main;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

public class FlipClockPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final int MARGIN = 10;
	
	private FlipLabel label_Flip_Hour = new FlipLabel();
	private FlipLabel label_Flip_Minute = new FlipLabel();
	private FlipLabel label_Flip_Second = new FlipLabel();
	private JLabel label_Text_Hour = new JLabel();
	private JLabel label_Text_Minute = new JLabel();
	private JLabel label_Text_Second = new JLabel();
	
	public FlipClockPanel()
	{
		this.setLayout(null);
		
		this.initializeComponent();
	}

	public void setComponentForeground(Color color)
	{
		label_Flip_Hour.setForeground(color);
		label_Flip_Minute.setForeground(color);
		label_Flip_Second.setForeground(color);

		label_Flip_Hour.setLineColor(color);
		label_Flip_Minute.setLineColor(color);
		label_Flip_Second.setLineColor(color);
		
		label_Text_Hour.setForeground(color);
		label_Text_Minute.setForeground(color);
		label_Text_Second.setForeground(color);
	}
	
	public void setComponentBackground(Color color)
	{
		label_Flip_Hour.setBackground(color);
		label_Flip_Minute.setBackground(color);
		label_Flip_Second.setBackground(color);
		
		label_Text_Hour.setBackground(color);
		label_Text_Minute.setBackground(color);
		label_Text_Second.setBackground(color);
	}
	
	public void updateClock()
	{
		this.locateComponent();
		
		Calendar calendar = Calendar.getInstance();

		label_Flip_Hour.setText(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
		label_Flip_Minute.setText(String.format("%02d", calendar.get(Calendar.MINUTE)));
		label_Flip_Second.setText(String.format("%02d", calendar.get(Calendar.SECOND)));
	}
	
	private void initializeComponent()
	{
		this.add(label_Flip_Hour);
		this.add(label_Flip_Minute);
		this.add(label_Flip_Second);
		
		label_Text_Hour.setText("H");
		this.add(label_Text_Hour);
		
		label_Text_Minute.setText("M");
		this.add(label_Text_Minute);
		
		label_Text_Second.setText("S");
		this.add(label_Text_Second);
	}
	
	private void locateComponent()
	{
		int height = (int)Math.min((this.getSize().getWidth() - 4 * MARGIN) / 4.5 / 1.2,
			this.getSize().getHeight() - MARGIN * 2);
		int width = height * 6 / 5;
		Font largeFont = new Font(Font.DIALOG, Font.PLAIN, height * 4 / 5);
		Font smallFont = new Font(Font.DIALOG, Font.PLAIN, height * 2 / 5);
		
		label_Flip_Hour.setBounds(MARGIN, MARGIN, width, height);
		label_Flip_Hour.setFont(largeFont);

		label_Flip_Minute.setBounds(MARGIN * 2 + width * 3 / 2, MARGIN, width, height);
		label_Flip_Minute.setFont(largeFont);

		label_Flip_Second.setBounds(MARGIN * 3 + width * 3, MARGIN, width, height);
		label_Flip_Second.setFont(largeFont);
		
		label_Text_Hour.setBounds(MARGIN + width + MARGIN, MARGIN + height / 2, width / 2, height / 2);
		label_Text_Hour.setFont(smallFont);
		
		label_Text_Minute.setBounds(MARGIN * 2 + width * 5 / 2 + MARGIN, MARGIN + height / 2, width / 2, height / 2);
		label_Text_Minute.setFont(smallFont);
		
		label_Text_Second.setBounds(MARGIN * 3 + width * 4 + MARGIN, MARGIN + height / 2, width / 2, height / 2);
		label_Text_Second.setFont(smallFont);
	}
}
