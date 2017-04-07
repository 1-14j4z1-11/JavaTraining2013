package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	protected static final int ADJUST_MARGIN = 50;
	private static final Dimension NOT_CLIENT_SIZE = new Dimension(6, 49);
	
	private LayoutManager layout = new GridLayout();
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu_Config = new JMenu();
	private JCheckBoxMenuItem menuItem_Config_TopMost = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem menuItem_Config_AdjustPosition = new JCheckBoxMenuItem();
	
	private FlipClockPanel flipClockPanel = new FlipClockPanel();
	
	private Timer timer = new Timer(1000, new ActionListener_Timer());
	
	public MainFrame()
	{
		this.add(menuBar);
		this.setLocation(200, 200);
		this.setSize(NOT_CLIENT_SIZE.width + 420, NOT_CLIENT_SIZE.height + 100);
		this.setLayout(layout);
		this.setResizable(false);
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
		});
		
		this.initializeComponent();
		
		timer.start();
	}

	private void initializeComponent()
	{
		flipClockPanel.setBackground(new Color(0xFF808080));
		flipClockPanel.setComponentForeground(new Color(0xFFFFFFFF));
		flipClockPanel.setComponentBackground(new Color(0xFF000000));
		this.add(flipClockPanel);
		
		this.setJMenuBar(menuBar);
		
		menu_Config.setText("ê›íË");
		menuBar.add(menu_Config);
		
		menuItem_Config_TopMost.setText("ç≈ëOñ Ç…ï\é¶");
		menuItem_Config_TopMost.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				setAlwaysOnTop(menuItem_Config_TopMost.getState());
			}
		});
		menu_Config.add(menuItem_Config_TopMost);
		
		menuItem_Config_AdjustPosition.setText("âÊñ í[Ç…äÒÇπÇÈ");
		menu_Config.add(menuItem_Config_AdjustPosition);
	}
	
	private class ActionListener_Timer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			flipClockPanel.updateClock();
		}
	}
}
