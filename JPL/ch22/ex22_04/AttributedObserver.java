package ex22_04;

import java.util.Observable;
import java.util.Observer;

public class AttributedObserver implements Observer
{
	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("Attributed Changed : size = " + arg);
	}
}
