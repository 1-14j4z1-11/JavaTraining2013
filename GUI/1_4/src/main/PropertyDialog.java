package main;

import java.awt.*;
import java.awt.event.*;

public class PropertyDialog
	extends Dialog
	implements ActionListener, ItemListener
{
	// staticフィールド

	private static final long serialVersionUID = 1L;
	
	public static final String[] FONT_TYPES = new String[]
	{
		Font.SANS_SERIF,
		Font.SERIF,
		Font.DIALOG,
		Font.MONOSPACED,
	};

	public static final int[] FONT_SIZES = new int[]
	{
		30, 40, 50, 60, 80, 100
	};
	
	private static final Dimension COMP_SIZE = new Dimension(90, 20);
	private static final Dimension MARGIN_SIZE = new Dimension(10, 10);
	
	// フィールド
	
	private TextProperty tmpProperty;
	private TextProperty property;
	
	private GridBagLayout layout = new GridBagLayout();
	
	private Label label_FontType = new Label();
	private Label label_FontSize = new Label();
	private Label label_TextColor = new Label();
	private Label label_BackColor = new Label();
	private Choice choice_FontType = new Choice();
	private Choice choice_FontSize = new Choice();
	private Choice choice_TextColor = new Choice();
	private Choice choice_BackColor = new Choice();
	
	private Label label_Space = new Label();
	
	private Button button_OK = new Button();
	private Button button_Cancel = new Button();
	
	public PropertyDialog(Frame owner, TextProperty textProperty)
	{
		super(owner, true);

		this.property = textProperty;
		this.tmpProperty = new TextProperty(textProperty);
		
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
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		label_FontType.setText("文字フォント");
		label_FontType.setBounds(20, 40, COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_FontType, gbc);
		this.add(label_FontType);

		label_FontSize.setText("文字サイズ");
		label_FontSize.setBounds(label_FontType.getX(),
			label_FontType.getY() + label_FontType.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_FontSize, gbc);
		this.add(label_FontSize);
		
		label_TextColor.setText("文字色");
		label_TextColor.setBounds(label_FontSize.getX(),
			label_FontSize.getY() + label_FontSize.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_TextColor, gbc);
		this.add(label_TextColor);

		label_BackColor.setText("背景色");
		label_BackColor.setBounds(label_TextColor.getX(),
			label_TextColor.getY() + label_TextColor.getHeight() + MARGIN_SIZE.height,
			COMP_SIZE.width, COMP_SIZE.height);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		layout.setConstraints(label_BackColor, gbc);
		this.add(label_BackColor);
		
		choice_FontType.setBounds(
			label_FontType.getX() + label_FontType.getWidth() + MARGIN_SIZE.width,
			label_FontType.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_FontType.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_FontType, gbc);
		this.add(choice_FontType);

		choice_FontSize.setBounds(
			label_FontSize.getX() + label_FontSize.getWidth() + MARGIN_SIZE.width,
			label_FontSize.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_FontSize.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		layout.setConstraints(choice_FontSize, gbc);
		this.add(choice_FontSize);

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

		choice_BackColor.setBounds(
			label_BackColor.getX() + label_BackColor.getWidth() + MARGIN_SIZE.width,
			label_BackColor.getY(),
			COMP_SIZE.width, COMP_SIZE.height);
		choice_BackColor.addItemListener(this);
		gbc.gridx = 1;
		gbc.gridy = 3;
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
		
		for(int i = 0; i < FONT_TYPES.length; i++)
		{
			choice_FontType.add(FONT_TYPES[i]);
		}

		for(int i = 0; i < FONT_SIZES.length; i++)
		{
			choice_FontSize.add(Integer.toString(FONT_SIZES[i]));
		}
		
		for(int i = 0; i < ColorManager.getTotal(); i++)
		{
			choice_TextColor.add(ColorManager.getColorNameAt(i));
		}
		
		for(int i = 0; i < ColorManager.getTotal(); i++)
		{
			choice_BackColor.add(ColorManager.getColorNameAt(i));
		}
		
		this.initChoices();
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
		this.tmpProperty.setFont(new Font(
			choice_FontType.getSelectedItem(),
			this.tmpProperty.getFont().getStyle(),
			FONT_SIZES[choice_FontSize.getSelectedIndex()]));
		
		this.tmpProperty.setTextColor(
			ColorManager.getColorAt(choice_TextColor.getSelectedIndex()));

		this.tmpProperty.setBackColor(
			ColorManager.getColorAt(choice_BackColor.getSelectedIndex()));
		
		this.tmpProperty.setChanged(true);
	}
	
	private void initChoices()
	{
		String fontName = this.property.getFont().getFontName();
		fontName = fontName.substring(0, fontName.indexOf('.'));
		
		int fontSize = this.property.getFont().getSize();
		Color textColor = this.property.getTextColor();
		Color backColor = this.property.getBackColor();
		
		for(int i = 0; i < choice_FontType.getItemCount(); i++)
		{
			if(fontName.equals(choice_FontType.getItem(i)))
			{
				choice_FontType.select(i);
			}
		}

		for(int i = 0; i < choice_FontSize.getItemCount(); i++)
		{
			if(fontSize == Integer.parseInt(choice_FontSize.getItem(i)))
			{
				choice_FontSize.select(i);
			}
		}
		
		for(int i = 0; i < choice_TextColor.getItemCount(); i++)
		{
			if(textColor.equals(ColorManager.getColorAt(i)))
			{
				choice_TextColor.select(i);
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
		this.property.setFont(this.tmpProperty.getFont());
		this.property.setTextColor(this.tmpProperty.getTextColor());
		this.property.setBackColor(this.tmpProperty.getBackColor());
	}
}
