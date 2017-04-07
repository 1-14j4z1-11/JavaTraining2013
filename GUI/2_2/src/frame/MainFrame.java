package frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.*;
import clock.*;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

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
	
	private DisplayProperty property = new DisplayProperty(clockPanel, new MySize(400, 70), new Color(0xFFFFFFFF), new Color(0xFF808080), new Color(0xFF808080));
	
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
		
		this.initializeComponent();
		timer.start();
		
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
				clockPanel.setComponentBackground(property.getForeColor());
				clockPanel.setBackground(property.getBackColor());
			}
			
			clockPanel.updateClock();
		}
	}
}
