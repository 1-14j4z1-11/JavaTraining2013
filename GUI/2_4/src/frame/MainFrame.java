package frame;

import java.awt.*;
import java.awt.event.*;
import java.util.prefs.*;
import javax.swing.*;
import util.*;
import clock.*;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	// pref Key
	private static final String KEY_LOCATIONX = "x";
	private static final String KEY_LOCATIONY = "y";
	private static final String KEY_CLOCK_INDEX = "clock";
	private static final String KEY_SIZE_INDEX = "size";
	private static final String KEY_TEXTCOLOR_INDEX = "text";
	private static final String KEY_BACKCOLOR_INDEX = "back";
	
	// 初期値
	private static final Point DEF_LOCATION = new Point(100, 100);
	private static final int DEF_CLOCK_INDEX = 0;
	private static final int DEF_SIZE_INDEX = 2;
	private static final int DEF_TEXTCOLOR_INDEX = 2;
	private static final int DEF_BACKCOLOR_INDEX = 4;
	
	protected static final int ADJUST_MARGIN = 50;
	private static final Dimension NOT_CLIENT_SIZE = new Dimension(6, 49 + 23);	// メニュバー分

	private LayoutManager layout = new GridLayout();
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu_Config = new JMenu();
	private JCheckBoxMenuItem menuItem_Config_TopMost = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem menuItem_Config_AdjustPosition = new JCheckBoxMenuItem();
	private JMenuItem menuItem_Config_Config = new JMenuItem();
	
	private ClockPanel clockPanel = new FlipClockPanel();
	private Timer timer = new Timer(100, new ActionListener_Timer());
	
	private DisplayProperty property = new DisplayProperty(clockPanel, new MySize(600, 110), new Color(0xFF00FF00), new Color(0xFF000000), new Color(0xFF000000));
	
	public MainFrame()
	{
		this.add(menuBar);
		this.setLocation(200, 200);
		this.setSize(NOT_CLIENT_SIZE.width + this.property.getSize().width,
			NOT_CLIENT_SIZE.height + this.property.getSize().height);
		this.setLayout(layout);
		this.setResizable(false);	// TODO
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentMoved(ComponentEvent e)
			{
				if(!menuItem_Config_AdjustPosition.getState())
				{
					return;
				}
				
				GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				Dimension screenSize = env.getMaximumWindowBounds().getSize();
				
				int x = getX();
				int y = getY();
				
				if(x <= ADJUST_MARGIN)
				{
					x = 0;
				}
				else if(x + getWidth() >= screenSize.getWidth() - ADJUST_MARGIN)
				{
					x = (int)(screenSize.getWidth() - getWidth());
				}
				
				if(y <= ADJUST_MARGIN)
				{
					y = 0;
				}
				else if(y + getHeight() >= screenSize.getHeight() - ADJUST_MARGIN)
				{
					y = (int)(screenSize.getHeight() - getHeight());
				}
				
				setLocation(x, y);
			}
			
			@Override
			public void componentResized(ComponentEvent e)
			{
				clockPanel.updateClock();
			}
		});
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				writePreferences();
			}
		});
		
		this.initializeComponent();
		timer.start();
		this.readPreferences();
		this.property.setChanged(true);
	}
	
	private void initializeComponent()
	{
		this.add(clockPanel);
		
		// menuBar
		this.setJMenuBar(menuBar);
		
		// menu_Config
		menu_Config.setText("設定");
		menuBar.add(menu_Config);
		
		// menuItem_Config_TopMost
		menuItem_Config_TopMost.setText("最前面に表示");
		menu_Config.add(menuItem_Config_TopMost);
		
		// menuItem_Config_AdjustPosition
		menuItem_Config_AdjustPosition.setText("画面端に寄せる");
		menu_Config.add(menuItem_Config_AdjustPosition);
		
		// menuItem_Config_Config
		menuItem_Config_Config.setText("表示設定");
		menu_Config.add(menuItem_Config_Config);
		
		// イベントリスナ
		menuItem_Config_TopMost.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				setAlwaysOnTop(menuItem_Config_TopMost.getState());
			}
		});
		
		menuItem_Config_Config.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PropertyDialog dialog = new PropertyDialog(MainFrame.this, property);
				dialog.setVisible(true);
			}
		});
	}
	
	// TODO 開始時に初期設定を反映させる
	private class ActionListener_Timer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(property.isChanged())
			{
				property.setChanged(false);
				
				if(clockPanel != null)
				{
					MainFrame.this.remove(clockPanel);
				}
				clockPanel = property.getClock();
				MainFrame.this.add(clockPanel);
				
				MainFrame.this.setSize(property.getSize().width + NOT_CLIENT_SIZE.width,
					property.getSize().height + NOT_CLIENT_SIZE.height);
				clockPanel.setComponentForeground(property.getTextColor());
				clockPanel.setComponentBackground(property.getBackColor());
				clockPanel.setBackground(property.getBackColor());
			}
			
			clockPanel.updateClock();
			MainFrame.this.repaint();
		}
	}

	private void readPreferences()
	{
		Preferences pref = Preferences.userNodeForPackage(this.getClass());
		
		int x = pref.getInt(KEY_LOCATIONX, (int)DEF_LOCATION.getX());
		int y = pref.getInt(KEY_LOCATIONY, (int)DEF_LOCATION.getY());
		
		this.setLocation(new Point(x, y));
		
		int clockIndex = pref.getInt(KEY_CLOCK_INDEX, DEF_CLOCK_INDEX);
		int sizeIndex = pref.getInt(KEY_SIZE_INDEX, DEF_SIZE_INDEX);
		int textIndex = pref.getInt(KEY_TEXTCOLOR_INDEX, DEF_TEXTCOLOR_INDEX);
		int backIndex = pref.getInt(KEY_BACKCOLOR_INDEX, DEF_BACKCOLOR_INDEX);
		
		try
		{
			this.property.setClock(DisplayProperty.CLOCK_PANELS[clockIndex]);
			this.property.setSize(DisplayProperty.FRAME_SIZES[sizeIndex]);
		}
		catch(IndexOutOfBoundsException e)
		{
			this.property.setClock(DisplayProperty.CLOCK_PANELS[DEF_CLOCK_INDEX]);
			this.property.setSize(DisplayProperty.FRAME_SIZES[DEF_SIZE_INDEX]);
		}
		
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
	}

	private void writePreferences()
	{
		Preferences pref = Preferences.userNodeForPackage(this.getClass());
		
		pref.putInt(KEY_LOCATIONX, (int)this.getLocation().getX());
		pref.putInt(KEY_LOCATIONY, (int)this.getLocation().getY());
		
		for(int i = 0; i < DisplayProperty.CLOCK_PANELS.length; i++)
		{
			if(this.property.getClock() == DisplayProperty.CLOCK_PANELS[i])
			{
				pref.putInt(KEY_CLOCK_INDEX, i);
			}
		}

		for(int i = 0; i < DisplayProperty.FRAME_SIZES.length; i++)
		{
			if(this.property.getSize() == DisplayProperty.FRAME_SIZES[i])
			{
				pref.putInt(KEY_SIZE_INDEX, i);
			}
		}
		
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
	}
}
