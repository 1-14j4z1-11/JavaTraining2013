package frame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import util.*;
import clock.*;
import static util.DisplayProperty.*;

public class MainWindow extends JWindow implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;

	protected static final int ADJUST_MARGIN = 50;
	private static final Dimension NOT_CLIENT_SIZE = new Dimension(0, 0);	// メニュバー分

	private static final int MARGIN = 30;
	
	private LayoutManager layout = new GridLayout();
	
	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenu menu_Config = new JMenu();
	private JMenu menu_Config_Type = new JMenu();
	private JMenu menu_Config_Size = new JMenu();
	private JMenu menu_Config_Text = new JMenu();
	private JMenu menu_Config_Back = new JMenu();
	private JCheckBoxMenuItem menuItem_TopMost = new JCheckBoxMenuItem();
	private JCheckBoxMenuItem menuItem_AdjustPosition = new JCheckBoxMenuItem();
	private JMenuItem menu_Exit = new JMenuItem();
	private JCheckBoxMenuItem[] menuItems_Config_Type;
	private JCheckBoxMenuItem[] menuItems_Config_Size;
	private JCheckBoxMenuItem[] menuItems_Config_Text;
	private JCheckBoxMenuItem[] menuItems_Config_Back;
	
	private ClockPanel clockPanel = CLOCK_PANELS[0];
	private Timer timer = new Timer(100, new ActionListener_Timer());
	
	// 設定の初期値
	private DisplayProperty property = new DisplayProperty(
		clockPanel,
		DisplayProperty.FRAME_SIZES[2],
		ColorManager.getColorAt(1),
		ColorManager.getColorAt(5),
		ColorManager.getColorAt(4));

	private boolean leftPressed = false;
	private Point pressedPoint;
	
	public MainWindow()
	{
		this.setLocation(200, 200);
		this.setLayout(layout);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.initializeComponent();
		timer.start();
		
		this.property.setChanged(true);
	}
	
	private void initializeComponent()
	{
		this.add(clockPanel);
		
		// menu_Config
		menu_Config.setText("設定");
		popupMenu.add(menu_Config);
		
		// menu_Config_Type
		menu_Config_Type.setText("時計種類");
		menu_Config.add(menu_Config_Type);

		// menu_Config_Size
		menu_Config_Size.setText("サイズ");
		menu_Config.add(menu_Config_Size);

		// menu_Config_Text
		menu_Config_Text.setText("文字色");
		menu_Config.add(menu_Config_Text);

		// menu_Config_Back
		menu_Config_Back.setText("背景色");
		menu_Config.add(menu_Config_Back);

		popupMenu.addSeparator();
		
		// menuItem_Config_TopMost
		menuItem_TopMost.setText("最前面に表示");
		menuItem_TopMost.setEnabled(false);		// TODO JWindowではうまく機能しない
		popupMenu.add(menuItem_TopMost);
		
		// menuItem_Config_AdjustPosition
		menuItem_AdjustPosition.setText("画面端に寄せる");
		popupMenu.add(menuItem_AdjustPosition);
		
		popupMenu.addSeparator();
		
		menu_Exit.setText("終了");
		popupMenu.add(menu_Exit);
		
		// イベントリスナ
		menuItem_TopMost.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				setAlwaysOnTop(menuItem_TopMost.getState());
			}
		});
		
		menu_Exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		menuItems_Config_Type = new JCheckBoxMenuItem[CLOCK_PANELS.length];
		menuItems_Config_Size = new JCheckBoxMenuItem[FRAME_SIZES.length];
		menuItems_Config_Text = new JCheckBoxMenuItem[ColorManager.getTotal()];
		menuItems_Config_Back = new JCheckBoxMenuItem[ColorManager.getTotal()];
		
		for(int i = 0; i < CLOCK_PANELS.length; i++)
		{
			final ClockPanel clock = CLOCK_PANELS[i];
			
			menuItems_Config_Type[i] = new JCheckBoxMenuItem(clock.toString());
			menuItems_Config_Type[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					property.setClock(clock);
					property.setChanged(true);
					
					for(JCheckBoxMenuItem item : menuItems_Config_Type)
					{
						item.setState(item == e.getSource());
					}
				}
			});
			menu_Config_Type.add(menuItems_Config_Type[i]);
			
			// 初期化
			if(this.property.getClock() == clock)
				menuItems_Config_Type[i].setState(true);
		}

		for(int i = 0; i < FRAME_SIZES.length; i++)
		{
			final MySize size = FRAME_SIZES[i];
			
			menuItems_Config_Size[i] = new JCheckBoxMenuItem(size.toString());
			menuItems_Config_Size[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					property.setSize(size);
					property.setChanged(true);
					
					for(JCheckBoxMenuItem item : menuItems_Config_Size)
					{
						item.setState(item == e.getSource());
					}
				}
			});
			menu_Config_Size.add(menuItems_Config_Size[i]);
			
			// 初期化
			if(this.property.getSize() == size)
				menuItems_Config_Size[i].setState(true);
		}

		for(int i = 0; i < ColorManager.getTotal(); i++)
		{
			final Color color = ColorManager.getColorAt(i);
			
			BufferedImage image = new BufferedImage(12, 12, BufferedImage.TYPE_INT_ARGB);
			ImageIcon icon = new ImageIcon(image);
			for(int h = 0; h < image.getHeight(); h++)
			{
				for(int w = 0; w < image.getWidth(); w++)
				{
					image.setRGB(w, h, 0xFF000000 | color.getRGB());
				}
			}
			
			menuItems_Config_Text[i] = new JCheckBoxMenuItem(ColorManager.getColorNameAt(i));
			menuItems_Config_Text[i].setIcon(icon);
			menuItems_Config_Text[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					property.setTextColor(color);
					property.setChanged(true);
					
					for(JCheckBoxMenuItem item : menuItems_Config_Text)
					{
						item.setState(item == e.getSource());
					}
				}
			});
			menu_Config_Text.add(menuItems_Config_Text[i]);

			menuItems_Config_Back[i] = new JCheckBoxMenuItem(ColorManager.getColorNameAt(i));
			menuItems_Config_Back[i].setIcon(icon);
			menuItems_Config_Back[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					property.setBackColor(color);
					property.setChanged(true);
					
					for(JCheckBoxMenuItem item : menuItems_Config_Back)
					{
						item.setState(item == e.getSource());
					}
				}
			});
			menu_Config_Back.add(menuItems_Config_Back[i]);
			
			// 初期化
			if(this.property.getTextColor() == color)
				menuItems_Config_Text[i].setState(true);
			if(this.property.getBackColor() == color)
				menuItems_Config_Back[i].setState(true);
		}
	}
	
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
					MainWindow.this.remove(clockPanel);
				}
				clockPanel = property.getClock();
				MainWindow.this.add(clockPanel);
				
				MainWindow.this.setSize(property.getSize().width + NOT_CLIENT_SIZE.width,
					property.getSize().height + NOT_CLIENT_SIZE.height);
				clockPanel.setComponentForeground(property.getTextColor());
				clockPanel.setComponentBackground(property.getBackColor());
				clockPanel.setBackground(property.getBackColor());
			}
			
			clockPanel.updateClock();
			MainWindow.this.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
			popupMenu.show(this, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{ }

	@Override
	public void mouseExited(MouseEvent e)
	{ }

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
			popupMenu.show(this, e.getX(), e.getY());
		}
		
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			leftPressed = true;
			pressedPoint = e.getPoint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
			popupMenu.show(this, e.getX(), e.getY());
		}
		
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			leftPressed = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(leftPressed)
		{
			int x = (int)(this.getLocation().getX() + e.getX() - pressedPoint.getX());
			int y = (int)(this.getLocation().getY() + e.getY() - pressedPoint.getY());
			
			if(menuItem_AdjustPosition.getState())
			{
				Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
				
				if(x <= MARGIN)
					x = 0;
				else if(x + this.getWidth() >= screen.width - MARGIN)
					x = screen.width - this.getWidth();
				
				if(y <= MARGIN)
					y = 0;
				else if(y + this.getHeight() >= screen.height - MARGIN)
					y = screen.height - this.getHeight();
			}
			
			this.setLocation(new Point(x, y));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{ }
}
