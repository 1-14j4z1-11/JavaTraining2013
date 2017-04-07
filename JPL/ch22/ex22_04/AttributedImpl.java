package ex22_04;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

public class AttributedImpl extends Observable implements Attributed
{
	private LinkedList<Attr> list = new LinkedList<Attr>();

	public void add(Attr newAttr)
	{
		this.list.add(newAttr);

		this.setChanged();
		this.notifyObservers(this.list.size());
	}

	public Attr find(String attrName)
	{
		for(Attr attr : this.list)
		{
			if (attr.getName().equals(attrName))
			{
				return attr;
			}
		}
		
		return null;
	}

	public Attr remove(String attrName)
	{
		Attr attr = find(attrName);

		if (attr == null)
		{
			return null;
		}
		
		this.list.remove(attr);
		
		this.setChanged();
		this.notifyObservers(this.list.size());
		
		return attr;
	}

	public Iterator<Attr> attrs()
	{
		return this.list.iterator();
	}
}
