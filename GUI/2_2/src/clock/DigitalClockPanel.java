package clock;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;
import component.DigitalLabel;

public class DigitalClockPanel extends ClockPanel
{
	private static final long serialVersionUID = 1L;
	private static final int MARGIN = 10;
	
	private DigitalLabel label_Hour1 = new DigitalLabel();
	private DigitalLabel label_Hour2 = new DigitalLabel();
	private DigitalLabel label_Minute1 = new DigitalLabel();
	private DigitalLabel label_Minute2 = new DigitalLabel();
	private DigitalLabel label_Second1 = new DigitalLabel();
	private DigitalLabel label_Second2 = new DigitalLabel();
	private JLabel label_Text_Hour = new JLabel();
	private JLabel label_Text_Minute = new JLabel();
	private JLabel label_Text_Second = new JLabel();
	
	public DigitalClockPanel()
	{
		this.setLayout(null);
		
		this.initializeComponent();
	}
	
	@Override
	public void setComponentForeground(Color color)
	{
		label_Hour1.setForeground(color);
		label_Hour2.setForeground(color);
		label_Minute1.setForeground(color);
		label_Minute2.setForeground(color);
		label_Second1.setForeground(color);
		label_Second2.setForeground(color);

		label_Text_Hour.setForeground(color);
		label_Text_Minute.setForeground(color);
		label_Text_Second.setForeground(color);
	}
	
	@Override
	public void setComponentBackground(Color color)
	{
		label_Hour1.setBackground(color);
		label_Hour2.setBackground(color);
		label_Minute1.setBackground(color);
		label_Minute2.setBackground(color);
		label_Second1.setBackground(color);
		label_Second2.setBackground(color);
		
		label_Text_Hour.setBackground(color);
		label_Text_Minute.setBackground(color);
		label_Text_Second.setBackground(color);
	}
	
	@Override
	public void updateClock()
	{
		this.locateComponent();
		
		Calendar calendar = Calendar.getInstance();

		label_Hour1.setNumber(calendar.get(Calendar.HOUR_OF_DAY) / 10);
		label_Hour2.setNumber(calendar.get(Calendar.HOUR_OF_DAY) % 10);
		label_Minute1.setNumber(calendar.get(Calendar.MINUTE) / 10);
		label_Minute2.setNumber(calendar.get(Calendar.MINUTE) % 10);
		label_Second1.setNumber(calendar.get(Calendar.SECOND) / 10);
		label_Second2.setNumber(calendar.get(Calendar.SECOND) % 10);
	}
	
	@Override
	public String toString()
	{
		return "デジタル時計";
	}
	
	private void initializeComponent()
	{
		this.add(label_Hour1);
		this.add(label_Hour2);
		this.add(label_Minute1);
		this.add(label_Minute2);
		this.add(label_Second1);
		this.add(label_Second2);
		
		label_Text_Hour.setText("H");
		this.add(label_Text_Hour);
		
		label_Text_Minute.setText("M");
		this.add(label_Text_Minute);
		
		label_Text_Second.setText("S");
		this.add(label_Text_Second);
	}
	
	private void locateComponent()
	{
		int height = (int)Math.min((this.getWidth() - 4 * MARGIN) / 4.5 / 1.2, this.getHeight() - MARGIN * 2);
		int width = height * 6 / 5;
		Font smallFont = new Font(Font.DIALOG, Font.PLAIN, height * 2 / 5);
		
		label_Hour1.setBounds(MARGIN * 2, MARGIN, width / 2, height);
		label_Hour2.setBounds(MARGIN * 2 + width / 2, MARGIN, width / 2, height);

		label_Minute1.setBounds(MARGIN * 3 + width * 3 / 2, MARGIN, width / 2, height);
		label_Minute2.setBounds(MARGIN * 3 + width * 2, MARGIN, width / 2, height);

		label_Second1.setBounds(MARGIN * 4 + width * 3, MARGIN, width / 2, height);
		label_Second2.setBounds(MARGIN * 4 + width * 7 / 2, MARGIN, width / 2, height);

		int lineWidth = Math.min(width, height / 2) / 5;
		label_Hour1.setLineWidth(lineWidth);
		label_Hour2.setLineWidth(lineWidth);
		label_Minute1.setLineWidth(lineWidth);
		label_Minute2.setLineWidth(lineWidth);
		label_Second1.setLineWidth(lineWidth);
		label_Second2.setLineWidth(lineWidth);
		
		label_Text_Hour.setBounds(MARGIN * 2 + width + MARGIN, MARGIN + height / 2, width / 2, height / 2);
		label_Text_Hour.setFont(smallFont);
		
		label_Text_Minute.setBounds(MARGIN * 3 + width * 5 / 2 + MARGIN, MARGIN + height / 2, width / 2, height / 2);
		label_Text_Minute.setFont(smallFont);
		
		label_Text_Second.setBounds(MARGIN * 4 + width * 4 + MARGIN, MARGIN + height / 2, width / 2, height / 2);
		label_Text_Second.setFont(smallFont);
	}
}
