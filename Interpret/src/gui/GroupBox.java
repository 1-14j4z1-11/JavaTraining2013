package gui;

import javax.swing.JPanel;
import javax.swing.border.*;

public class GroupBox extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private TitledBorder titledBorder = new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED));
	
	public GroupBox()
	{
		super(null);
		
		titledBorder.setTitleJustification(TitledBorder.LEFT);
		titledBorder.setTitlePosition(TitledBorder.TOP);
		this.setBorder(titledBorder);
	}
	
	public void setTitle(String title)
	{
		titledBorder.setTitle(title);
	}
}