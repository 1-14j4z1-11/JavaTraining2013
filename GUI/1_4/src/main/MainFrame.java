package main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

public class MainFrame extends Frame
{
	// staticフィールド
	private static final long serialVersionUID = 1L;
	
	private static final Dimension NOT_CLIENT_SIZE = new Dimension(6, 49);
	private static final Dimension MARGIN_SIZE = new Dimension(10, 0);
	private static final Point DEF_LOCATION = new Point(100, 100);
	private static final Color DEF_FORECOLOR = new Color(0x00, 0xFF, 0x00);
	private static final Color DEF_BACKCOLOR = new Color(0x00, 0x00, 0x00);
	private static final Font DEF_FONT = new Font(Font.DIALOG, Font.PLAIN, 40);
	private static final int TIMER_PERIOD = 100;
	
	private static final String KEY_LOCATIONX = "x";
	private static final String KEY_LOCATIONY = "y";
	private static final String KEY_FONT_TYPE = "font";
	private static final String KEY_FONT_SIZE = "size";
	private static final String KEY_TEXTCOLOR_INDEX = "text";
	private static final String KEY_BACKCOLOR_INDEX = "back";
	private static final String KEY_DISP_TYPE_DATETIME = "disp";
	
	private static final int DEF_TEXTCOLOR_INDEX = 1;
	private static final int DEF_BACKCOLOR_INDEX = 4;
	
	// フィールド
	private DisplayStyle display = DisplayStyle.TIME;
	private TextProperty property =
		new TextProperty(DEF_FONT, DEF_FORECOLOR, DEF_BACKCOLOR);
	
	private Label_Time label_Time;
	private Label_Date label_Date;
	private Timer timer;
	private MenuBar menuBar = new MenuBar();
	private Menu menu_Display = new Menu();
	private CheckboxMenuItem menuItem_Display_Time = new CheckboxMenuItem();
	private CheckboxMenuItem menuItem_Display_DateTime = new CheckboxMenuItem();
	private MenuItem menuItem_Config = new MenuItem();
	
	private GridLayout gridLayout1 = new GridLayout(1, 1);
	private GridLayout gridLayout2 = new GridLayout(2, 1);
	
	// コンストラクタ
	
	public MainFrame(String title)
	{
		this.setTitle(title);
		this.setLocation(DEF_LOCATION);
		this.setLayout(gridLayout1);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowOpened(WindowEvent e)
			{
				readPreferences();
				changeState();
			}
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				writePreferences();
				System.exit(0);
			}
		});

		// label_Date
		label_Date = new Label_Date();
		label_Date.setForeground(DEF_FORECOLOR);
		label_Date.setBackground(DEF_BACKCOLOR);
		this.add(label_Date);

		// label_Time
		label_Time = new Label_Time();
		label_Time.setForeground(DEF_FORECOLOR);
		label_Time.setBackground(DEF_BACKCOLOR);
		this.add(label_Time);

		// timer
		timer = new Timer();
		timer.schedule(new TimerTask_Clock(), 0, TIMER_PERIOD);
		
		// menuBar
		this.setMenuBar(menuBar);
		
		// menu_Display
		menu_Display.setLabel("表示");
		menuBar.add(menu_Display);
		
		// menuItem_Display_Time
		menuItem_Display_Time.setLabel("時刻");
		menuItem_Display_Time.addItemListener(new MenuItem_Display_Click());
		menu_Display.add(menuItem_Display_Time);
		
		// menuItem_Display_DateTime
		menuItem_Display_DateTime.setLabel("日付+時刻");
		menuItem_Display_DateTime.addItemListener(new MenuItem_Display_Click());
		menu_Display.add(menuItem_Display_DateTime);
		
		menu_Display.addSeparator();
		
		// menuItem_Config
		menuItem_Config.setLabel("設定");
		menuItem_Config.addActionListener(new MenuItem_Config_Click());
		menu_Display.add(menuItem_Config);
		
		menuItem_Display_Time.setState(true);
		display = DisplayStyle.TIME;

		this.changeState();
	}
	
	// 内部メソッド
	
	private void changeState()
	{
		int fontPoint = property.getFont().getSize();
		
		Dimension size = new Dimension(
			NOT_CLIENT_SIZE.width + MARGIN_SIZE.width * 2
				+ (int)(fontPoint * 3.9),
			NOT_CLIENT_SIZE.height + MARGIN_SIZE.height * 2
				+ (int)(fontPoint * 1.3));
		
		label_Time.setFont(this.property.getFont());
		label_Time.setForeground(this.property.getTextColor());
		label_Time.setBackground(this.property.getBackColor());

		label_Date.setFont(this.property.getFont());
		label_Date.setForeground(this.property.getTextColor());
		label_Date.setBackground(this.property.getBackColor());
		
		this.removeAll();
		
		switch(display)
		{
			case TIME:
				this.setLayout(gridLayout1);
				this.add(label_Time);
				this.setSize(size);
				break;

			case DATETIME:
				this.setLayout(gridLayout2);
				this.add(label_Date);
				this.add(label_Time);
				this.setSize(size.width,
					size.height * 2 - NOT_CLIENT_SIZE.height);
				break;
				
			default:
				throw new AssertionError();
		}
	}
	
	private void readPreferences()
	{
		Preferences pref = Preferences.userNodeForPackage(this.getClass());
		
		int x = pref.getInt(KEY_LOCATIONX, (int)DEF_LOCATION.getX());
		int y = pref.getInt(KEY_LOCATIONY, (int)DEF_LOCATION.getY());
		
		this.setLocation(new Point(x, y));
		
		String fontName = this.property.getFont().getFontName();
		fontName = fontName.substring(0, fontName.indexOf('.'));
		int size = this.property.getFont().getSize();
		
		this.property.setFont(new Font(
			pref.get(KEY_FONT_TYPE, fontName),
			Font.PLAIN,
			pref.getInt(KEY_FONT_SIZE, size)));
		
		int textIndex = pref.getInt(KEY_TEXTCOLOR_INDEX, DEF_TEXTCOLOR_INDEX);
		int backIndex = pref.getInt(KEY_BACKCOLOR_INDEX, DEF_BACKCOLOR_INDEX);
		
		try
		{
			this.property.setTextColor(ColorManager.getColorAt(textIndex));
			this.property.setBackColor(ColorManager.getColorAt(backIndex));
		}
		catch(IndexOutOfBoundsException e)
		{
			this.property.setTextColor(ColorManager.getColorAt(DEF_TEXTCOLOR_INDEX));
			this.property.setBackColor(ColorManager.getColorAt(DEF_BACKCOLOR_INDEX));
		}
		
		if(pref.getBoolean(KEY_DISP_TYPE_DATETIME, false))
		{
			this.display = DisplayStyle.DATETIME;
			menuItem_Display_Time.setState(false);
			menuItem_Display_DateTime.setState(true);
		}
		else
		{
			this.display = DisplayStyle.TIME;
			menuItem_Display_Time.setState(true);
			menuItem_Display_DateTime.setState(false);
		}
	}

	private void writePreferences()
	{
		Preferences pref = Preferences.userNodeForPackage(this.getClass());
		
		pref.putInt(KEY_LOCATIONX, (int)this.getLocation().getX());
		pref.putInt(KEY_LOCATIONY, (int)this.getLocation().getY());
		
		String fontName = this.property.getFont().getFontName();
		fontName = fontName.substring(0, fontName.indexOf('.'));
		
		pref.put(KEY_FONT_TYPE, fontName);
		pref.putInt(KEY_FONT_SIZE, this.property.getFont().getSize());
		
		for(int i = 0; i < ColorManager.getTotal(); i++)
		{
			if(this.property.getTextColor().equals(ColorManager.getColorAt(i)))
			{
				pref.putInt(KEY_TEXTCOLOR_INDEX, i);
			}
			if(this.property.getBackColor().equals(ColorManager.getColorAt(i)))
			{
				pref.putInt(KEY_BACKCOLOR_INDEX, i);
			}
		}
		
		pref.putBoolean(KEY_DISP_TYPE_DATETIME, (this.display == DisplayStyle.DATETIME));
	}
	
	// 内部クラス
	
	private class Label_Time extends Label
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g)
		{
			Calendar time = Calendar.getInstance();
			String timeText = String.format("%02d:%02d:%02d",
				time.get(Calendar.HOUR_OF_DAY),
				time.get(Calendar.MINUTE),
				time.get(Calendar.SECOND));
			
			Image image = this.createImage(this.getWidth(), this.getHeight());
			Graphics buf = image.getGraphics();
			
			buf.setFont(property.getFont());
			buf.setColor(property.getBackColor());
			buf.clearRect(0, 0, this.getWidth(), this.getHeight());
			
			FontMetrics fm = buf.getFontMetrics();
			
			buf.setColor(property.getTextColor());
			buf.drawString(timeText,
				MARGIN_SIZE.width,
				fm.getAscent() + MARGIN_SIZE.height);
			
			g.drawImage(image, 0, 0, this);
		}
	}

	private class Label_Date extends Label
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g)
		{
			Calendar time = Calendar.getInstance();
			String timeText = String.format("%02d/%02d/%02d",
				time.get(Calendar.YEAR) - 2000,
				time.get(Calendar.MONTH),
				time.get(Calendar.DAY_OF_MONTH));
			
			Image image = this.createImage(this.getWidth(), this.getHeight());
			Graphics buf = image.getGraphics();
			
			buf.setFont(property.getFont());
			buf.setColor(property.getBackColor());
			buf.clearRect(0, 0, this.getWidth(), this.getHeight());
			
			FontMetrics fm = buf.getFontMetrics();
			
			buf.setColor(property.getTextColor());
			buf.drawString(timeText,
				MARGIN_SIZE.width,
				fm.getAscent() + MARGIN_SIZE.height);
			
			g.drawImage(image, 0, 0, this);
		}
	}
	
	private class TimerTask_Clock extends TimerTask
	{
		@Override
		public void run()
		{
			label_Time.repaint();
			label_Date.repaint();
		}
	}
	
	private class MenuItem_Display_Click implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			if(e.getSource() == menuItem_Display_Time)
			{
				display = DisplayStyle.TIME;
				menuItem_Display_Time.setState(true);
				menuItem_Display_DateTime.setState(false);
			}
			else if(e.getSource() == menuItem_Display_DateTime)
			{
				display = DisplayStyle.DATETIME;
				menuItem_Display_Time.setState(false);
				menuItem_Display_DateTime.setState(true);
			}
			else
			{
				throw new AssertionError();
			}
			
			changeState();
		}
	}
	
	private class MenuItem_Config_Click implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			PropertyDialog dialog =
				new PropertyDialog(MainFrame.this, MainFrame.this.property);
			
			dialog.setVisible(true);
			
			MainFrame.this.changeState();
		}
	}
}