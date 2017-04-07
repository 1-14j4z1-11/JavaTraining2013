package clock;

import java.awt.Color;
import javax.swing.JPanel;

public abstract class ClockPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public abstract void setComponentForeground(Color color);
	public abstract void setComponentBackground(Color color);
	public abstract void updateClock();
}
