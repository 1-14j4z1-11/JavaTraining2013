package ex21_04;

import java.util.ListIterator;
import java.util.NoSuchElementException;

// ・ListIteratorをフィルターするのが目的なので、ShortStringsを拡張する必要はない。
//　・また、ShortStrings_Listは、普通のIteratorに対しては利用できないので、拡張はすべきでない。

public class ShortStrings_List implements ListIterator<String>
{
	private ListIterator<String> iter;
	private int index = 0;
	private String prev = null;
	private String next = null;
	private final int maxLength;
	
	public ShortStrings_List(ListIterator<String> iter, int maxLength)
	{
		this.iter = iter;
		this.maxLength = maxLength;
	}
	
	@Override
	public boolean hasNext()
	{
		this.prev = null;
		
		if(this.next != null)
		{
			return true;
		}
		
		while(this.iter.hasNext())
		{
			this.next = this.iter.next();
			
			if(this.next.length() <= this.maxLength)
			{
				return true;
			}
		}
		
		this.next = null;
		return false;
	}

	@Override
	public boolean hasPrevious()
	{
		this.next = null;
		
		if(this.prev != null)
		{
			return true;
		}
		
		while(this.iter.hasPrevious())
		{
			this.prev = this.iter.previous();
			
			if(this.prev.length() <= this.maxLength)
			{
				return true;
			}
		}
		
		this.prev = null;
		return false;
	}

	@Override
	public String next()
	{
		if((this.next == null) && !this.hasNext())
		{
			throw new NoSuchElementException();
		}
		
		this.index++;
		String s = this.next;
		this.next = null;
		
		return s;
	}

	@Override
	public String previous()
	{
		if((this.prev == null) && !this.hasPrevious())
		{
			throw new NoSuchElementException();
		}

		this.index--;
		String s = this.prev;
		this.prev = null;
		
		return s;
	}

	@Override
	public int nextIndex()
	{
		return this.index;
	}

	@Override
	public int previousIndex()
	{
		return this.index - 1;
	}
	
	@Override
	public void add(String obj)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(String obj)
	{
		throw new UnsupportedOperationException();
	}
}
