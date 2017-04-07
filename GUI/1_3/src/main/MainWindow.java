package main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainWindow extends Window implements MouseListener, MouseMotionListener
{
	// staticフィールド
	private static final long serialVersionUID = 1L;
	
	private static final Dimension NOT_CLIENT_SIZE = new Dimension(0, 0);
	private static final Dimension MARGIN_SIZE = new Dimension(10, 0);
	private static final Point DEF_LOCATION = new Point(100, 100);
	private static final Color DEF_FORECOLOR = new Color(0x00, 0xFF, 0x00);
	private static final Color DEF_BACKCOLOR = new Color(0x00, 0x00, 0x00);
	private static final Font DEF_FONT = new Font(Font.DIALOG, Font.PLAIN, 40);
	private static final int TIMER_PERIOD = 100;
	private static final int MARGIN = 40;
	
	private static final String[] FONT_TYPES = new String[]
	{
		Font.SANS_SERIF,
		Font.SERIF,
		Font.DIALOG,
		Font.MONOSPACED,
	};

	private static final int[] FONT_SIZES = new int[]
	{
		30, 40, 50, 60, 80, 100
	};
	
	// フィールド
	
	boolean leftPressed = false;
	Point pressedPoint = new Point();
	
	private TextProperty property =
		new TextProperty(DEF_FONT, DEF_FORECOLOR, DEF_BACKCOLOR);
	ColorManager textColor = new ColorManager(0);
	ColorManager backColor = new ColorManager(0);
	
	private Label_Time label_Time;
	private Timer timer;
	private PopupMenu popupMenu = new PopupMenu();
	private Menu menu_Font = new Menu();
	private Menu menu_Size = new Menu();
	private Menu menu_Text = new Menu();
	private Menu menu_Back = new Menu();
	private MenuItem menuItem_Exit = new MenuItem();
	private CheckboxMenuItem[] menuItems_Font;
	private CheckboxMenuItem[] menuItems_Size;
	private CheckboxMenuItem[] menuItems_Text;
	private CheckboxMenuItem[] menuItems_Back;
	
	// コンストラクタ
	
	public MainWindow()
	{
		super(null);
		
		this.setLocation(DEF_LOCATION);
		this.setLayout(new GridLayout(1, 1));
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		// label_Time
		label_Time = new Label_Time();
		label_Time.setForeground(DEF_FORECOLOR);
		label_Time.setBackground(DEF_BACKCOLOR);
		label_Time.addMouseListener(this);
		label_Time.addMouseMotionListener(this);
		this.add(label_Time);

		this.add(popupMenu);
		
		menu_Font.setLabel("フォント");
		popupMenu.add(menu_Font);

		menu_Size.setLabel("サイズ");
		popupMenu.add(menu_Size);

		menu_Text.setLabel("文字色");
		popupMenu.add(menu_Text);

		menu_Back.setLabel("背景色");
		popupMenu.add(menu_Back);
		
		popupMenu.addSeparator();
		
		menuItem_Exit.setLabel("終了");
		popupMenu.add(menuItem_Exit);
		menuItem_Exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		menuItems_Font = new CheckboxMenuItem[FONT_TYPES.length];
		ItemListener il_Font = new ItemListener_Font();
		
		for(int i = 0 ; i < FONT_TYPES.length; i++)
		{
			menuItems_Font[i] = new CheckboxMenuItem(FONT_TYPES[i]);
			menuItems_Font[i].addItemListener(il_Font);
			menu_Font.add(menuItems_Font[i]);
			
			String fontName = property.getFont().getFontName();
			fontName = fontName.substring(0, fontName.indexOf('.'));
			
			if(fontName.equals(FONT_TYPES[i]))
			{
				menuItems_Font[i].setState(true);
			}
		}

		menuItems_Size = new CheckboxMenuItem[FONT_SIZES.length];
		ItemListener il_Size = new ItemListener_Size();
		
		for(int i = 0 ; i < FONT_SIZES.length; i++)
		{
			menuItems_Size[i] = new CheckboxMenuItem(Integer.toString(FONT_SIZES[i]));
			menuItems_Size[i].addItemListener(il_Size);
			menu_Size.add(menuItems_Size[i]);
			
			if(property.getFont().getSize() == FONT_SIZES[i])
			{
				menuItems_Size[i].setState(true);
			}
		}

		menuItems_Text = new CheckboxMenuItem[ColorManager.getTotal()];
		ItemListener il_Text = new ItemListener_Text();
		
		for(int i = 0 ; i < menuItems_Text.length; i++)
		{
			menuItems_Text[i] = new CheckboxMenuItem(ColorManager.getColorNameAt(i));
			menuItems_Text[i].addItemListener(il_Text);
			menu_Text.add(menuItems_Text[i]);
			
			if(property.getTextColor().equals(ColorManager.getColorAt(i)))
			{
				menuItems_Text[i].setState(true);
				textColor.setSelectedIndex(i);
			}
		}

		menuItems_Back = new CheckboxMenuItem[ColorManager.getTotal()];
		ItemListener il_Back = new ItemListener_Back();
		
		for(int i = 0 ; i < menuItems_Back.length; i++)
		{
			menuItems_Back[i] = new CheckboxMenuItem(ColorManager.getColorNameAt(i));
			menuItems_Back[i].addItemListener(il_Back);
			menu_Back.add(menuItems_Back[i]);
			
			if(property.getBackColor().equals(ColorManager.getColorAt(i)))
			{
				menuItems_Back[i].setState(true);
				backColor.setSelectedIndex(i);
			}
		}
		
		// timer
		timer = new Timer();
		timer.schedule(new TimerTask_Clock(), 0, TIMER_PERIOD);

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
		
		this.setSize(size);
		
		label_Time.setFont(this.property.getFont());
		label_Time.setForeground(this.property.getTextColor());
		label_Time.setBackground(this.property.getBackColor());
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

	private class TimerTask_Clock extends TimerTask
	{
		@Override
		public void run()
		{
			label_Time.repaint();
		}
	}

	private class ItemListener_Font implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			for(int i = 0; i < menuItems_Font.length; i++)
			{
				if(menuItems_Font[i] == e.getSource())
				{
					menuItems_Font[i].setState(true);
					
					property.setFont(new Font(FONT_TYPES[i],
						property.getFont().getStyle(),
						property.getFont().getSize()));
				}
				else
				{
					menuItems_Font[i].setState(false);
				}
			}
			
			changeState();
		}
	}

	private class ItemListener_Size implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			for(int i = 0; i < menuItems_Size.length; i++)
			{
				if(menuItems_Size[i] == e.getSource())
				{
					String fontName = property.getFont().getFontName();
					fontName = fontName.substring(0, fontName.indexOf('.'));
					
					menuItems_Size[i].setState(true);
					
					property.setFont(new Font(fontName,
						property.getFont().getStyle(), FONT_SIZES[i]));
				}
				else
				{
					menuItems_Size[i].setState(false);
				}
			}
			
			changeState();
		}
	}

	private class ItemListener_Text implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			for(int i = 0; i < menuItems_Text.length; i++)
			{
				if(menuItems_Text[i] == e.getSource())
				{
					menuItems_Text[i].setState(true);
					textColor.setSelectedIndex(i);
					property.setTextColor(textColor.getSelectedColor());
				}
				else
				{
					menuItems_Text[i].setState(false);
				}
			}
			
			changeState();
		}
	}

	private class ItemListener_Back implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			for(int i = 0; i < menuItems_Back.length; i++)
			{
				if(menuItems_Back[i] == e.getSource())
				{
					menuItems_Back[i].setState(true);
					backColor.setSelectedIndex(i);
					property.setBackColor(backColor.getSelectedColor());
				}
				else
				{
					menuItems_Back[i].setState(false);
				}
			}
			
			changeState();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(leftPressed)
		{
			int x = (int)(this.getLocation().getX() + e.getX() - pressedPoint.getX());
			int y = (int)(this.getLocation().getY() + e.getY() - pressedPoint.getY());
			
			Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			
			if(x <= MARGIN)
			{
				x = 0;
			}
			else if(x + this.getWidth() >= screen.width - MARGIN)
			{
				x = screen.width - this.getWidth();
			}
			
			if(y <= MARGIN)
			{
				y = 0;
			}
			else if(y + this.getHeight() >= screen.height - MARGIN)
			{
				y = screen.height - this.getHeight();
			}
			
			this.setLocation(new Point(x, y));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{ }

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
}