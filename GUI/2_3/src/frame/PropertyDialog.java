package frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.*;

import static util.DisplayProperty.*;

public class PropertyDialog extends JDialog implements ActionListener, ItemListener
{
	// staticフィールド
	private static final long serialVersionUID = 1L;
	private static final Dimension COMP_SIZE = new Dimension(90, 20);
	private static final Dimension MARGIN_SIZE = new Dimension(10, 10);
	
	// フィールド
	private DisplayProperty tmpProperty;
	private DisplayProperty property;
	private GridBagLayout layout = new GridBagLayout();
	
	private Label label_ClockType = new Label();
	private Label label_Size = new Label();
	private Label label_TextColor = new Label();
	private Label label_ForeColor = new Label();
	private Label label_BackColor = new Label();
	private Choice choice_ClockType = new Choice();
	private Choice choice_Size = new Choice();
	private Choice choice_TextColor = new Choice();
	private Choice choice_ForeColor = new Choice();
	private Choice choice_BackColor = new Choice();
	
	private Label label_Space = new Label();
	
	private Button button_OK = new Button();
	private Button button_Cancel = new Button();
	
	public PropertyDialog(JFrame owner, DisplayProperty textProperty)
	{
		super(owner, true);

		this.property = textProperty;
		this.tmpProperty = new DisplayProperty(textProperty);
		
		this.setLayout(layout);
		this.setResizable(false);
		this.setBounds(owner.getX() + 10,
			owner.getY() + owner.getHeight() + 10,
			(COMP_SIZE.width + MARGIN_SIZE.width) * 2 + 30,
			(COMP_SIZE.height + MARGIN_SIZE.height) * 4 + 80);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				PropertyDialog.this.dispose();
			}
		});
		
		this.initializeComponent();
		this.initalizeItems();
		this.initializeSelection();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.changeProperty();
		this.setVisible(false);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		String clockName = choice_ClockType.getSelectedItem();
		MySize size = null;
		try
		{
			size = MySize.parse(choice_Size.getSelectedItem());
		}
		catch(IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
		
		for(int i = 0; i < CLOCK_PANELS.length; i++)
		{
			if(clockName.equals(CLOCK_PANELS[i].toString()))
			{
				this.tmpProperty.setClock(CLOCK_PANELS[i]);
			}
		}

		for(int i = 0; i < FRAME_SIZES.length; i++)
		{
			if(size.equals(FRAME_SIZES[i]))
			{
				this.tmpProperty.setSize(FRAME_SIZES[i]);
			}
		}
		
		this.tmpProperty.setTextColor(
			ColorManager.getColorAt(choice_TextColor.getSelectedIndex()));

		this.tmpProperty.setForeColor(
			ColorManager.getColorAt(choice_ForeColor.getSelectedIndex()));

		this.tmpProperty.setBackColor(
			ColorManager.getColorAt(choice_BackColor.getSelectedIndex()));
		
		this.tmpProperty.setChanged(true);
	}
	
	private void initializeComponent()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		label_ClockType.setText("文字フォント");
		label_ClockType.setBounds(20, 40, COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_ClockType, gbc);
		this.add(label_ClockType);

		label_Size.setText("表示サイズ");
		label_Size.setBounds(label_ClockType.getX(),
			label_ClockType.getY() + label_ClockType.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_Size, gbc);
		this.add(label_Size);
		
		label_TextColor.setText("文字色");
		label_TextColor.setBounds(label_Size.getX(),
			label_Size.getY() + label_Size.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_TextColor, gbc);
		this.add(label_TextColor);

		label_ForeColor.setText("文字背景色");
		label_ForeColor.setBounds(label_Size.getX(),
			label_Size.getY() + label_Size.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_ForeColor, gbc);
		this.add(label_ForeColor);

		label_BackColor.setText("背景色");
		label_BackColor.setBounds(label_TextColor.getX(),
			label_TextColor.getY() + label_TextColor.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_BackColor, gbc);
		this.add(label_BackColor);
		
		choice_ClockType.setBounds(
			label_ClockType.getX() + label_ClockType.getWidth() + MARGIN_SIZE.width,
			label_ClockType.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_ClockType.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_ClockType, gbc);
		this.add(choice_ClockType);

		choice_Size.setBounds(
			label_Size.getX() + label_Size.getWidth() + MARGIN_SIZE.width,
			label_Size.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_Size.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_Size, gbc);
		this.add(choice_Size);

		choice_TextColor.setBounds(
			label_TextColor.getX() + label_TextColor.getWidth() + MARGIN_SIZE.width,
			label_TextColor.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_TextColor.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_TextColor, gbc);
		this.add(choice_TextColor);

		choice_ForeColor.setBounds(
			label_TextColor.getX() + label_TextColor.getWidth() + MARGIN_SIZE.width,
			label_TextColor.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_ForeColor.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_ForeColor, gbc);
		this.add(choice_ForeColor);

		choice_BackColor.setBounds(
			label_BackColor.getX() + label_BackColor.getWidth() + MARGIN_SIZE.width,
			label_BackColor.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_BackColor.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_BackColor, gbc);
		this.add(choice_BackColor);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		layout.setConstraints(label_Space, gbc);
		this.add(label_Space);
		
		button_OK.setLabel("OK");
		button_OK.setSize(55, 30);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(button_OK, gbc);
		button_OK.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PropertyDialog.this.changeProperty();
				PropertyDialog.this.setVisible(false);
			}
		});
		this.add(button_OK);

		button_Cancel.setLabel("Cancel");
		button_Cancel.setSize(55, 30);
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(button_Cancel, gbc);
		button_Cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PropertyDialog.this.setVisible(false);
			}
		});
		this.add(button_Cancel);
	}
	
	private void initalizeItems()
	{
		for(int i = 0; i < CLOCK_PANELS.length; i++)
		{
			choice_ClockType.add(CLOCK_PANELS[i].toString());
		}

		for(int i = 0; i < FRAME_SIZES.length; i++)
		{
			choice_Size.add(FRAME_SIZES[i].toString());
		}
		
		for(int i = 0; i < ColorManager.getTotal(); i++)
		{
			choice_TextColor.add(ColorManager.getColorNameAt(i));
			choice_ForeColor.add(ColorManager.getColorNameAt(i));
			choice_BackColor.add(ColorManager.getColorNameAt(i));
		}
	}

	private void initializeSelection()
	{
		String clockName = this.property.getClock().toString();
		String sizeStr = this.property.getSize().toString();
		Color textColor = this.property.getTextColor();
		Color foreColor = this.property.getForeColor();
		Color backColor = this.property.getBackColor();
		
		for(int i = 0; i < choice_ClockType.getItemCount(); i++)
		{
			if(clockName.equals(choice_ClockType.getItem(i)))
			{
				choice_ClockType.select(i);
			}
		}
	
		for(int i = 0; i < choice_Size.getItemCount(); i++)
		{
			if(sizeStr.equals(choice_Size.getItem(i)))
			{
				choice_Size.select(i);
			}
		}
		
		for(int i = 0; i < choice_TextColor.getItemCount(); i++)
		{
			if(textColor.equals(ColorManager.getColorAt(i)))
			{
				choice_TextColor.select(i);
			}
		}

		for(int i = 0; i < choice_ForeColor.getItemCount(); i++)
		{
			if(foreColor.equals(ColorManager.getColorAt(i)))
			{
				choice_ForeColor.select(i);
			}
		}

		for(int i = 0; i < choice_BackColor.getItemCount(); i++)
		{
			if(backColor.equals(ColorManager.getColorAt(i)))
			{
				choice_BackColor.select(i);
			}
		}
	}
	
	private void changeProperty()
	{
		this.property.setChanged(true);
		this.property.setClock(this.tmpProperty.getClock());
		this.property.setSize(this.tmpProperty.getSize());
		this.property.setTextColor(this.tmpProperty.getTextColor());
		this.property.setForeColor(this.tmpProperty.getForeColor());
		this.property.setBackColor(this.tmpProperty.getBackColor());
	}
}
