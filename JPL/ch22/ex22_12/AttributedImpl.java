package ex22_12;

import java.util.Iterator;
import java.util.LinkedList;

public class AttributedImpl implements Attributed, Iterable<Attr>
{
	private LinkedList<Attr> list = new LinkedList<Attr>();

	public void add(Attr newAttr)
	{
		this.list.add(newAttr);
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
		return attr;
	}

	public Iterator<Attr> attrs()
	{
		return this.list.iterator();
	}

	@Override
	public Iterator<Attr> iterator()
	{
		return this.attrs();
	}
}
