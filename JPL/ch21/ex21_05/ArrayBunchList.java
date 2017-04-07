package ex21_05;

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayBunchList<E> extends AbstractList<E>
{
	private final E[][] arrays;
	private final int size;
	
	public ArrayBunchList(E[][] arrays)
	{
		this.arrays = arrays.clone();
		
		int s = 0;
		
		for(E[] array : arrays)
			s += array.length;
		
		this.size = s;
	}

	@Override
	public int size()
	{
		return this.size;
	}
	
	@Override
	public E get(int index)
	{
		int off = 0;
		
		for(int i = 0; i < arrays.length; i++)
		{
			if(index < off + arrays[i].length)
				return arrays[i][index - off];
			
			off += arrays[i].length;
		}
		
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public E set(int index, E value)
	{
		int off = 0;
		
		for(int i = 0; i < arrays.length; i++)
		{
			if(index < off + arrays[i].length)
			{
				E ret = arrays[i][index - off];
				arrays[i][index - off] = value;
				return ret;
			}
			off += arrays[i].length;
		}
		
		throw new ArrayIndexOutOfBoundsException();
	}
	
	public ListIterator<E> listIterator()
	{
		return new ABLListIterator();
	}
	
	private class ABLListIterator implements ListIterator<E>
	{
		private int off = 0;
		private int array = 0;
		private int pos = 0;
		
		public ABLListIterator()
		{
			for(array = 0; array < arrays.length; array++)
			{
				if(arrays[array].length > 0)
					break;	
			}
		}
		
		@Override
		public boolean hasNext()
		{
			return (this.off + this.pos < size());
		}

		@Override
		public E next()
		{
			if(!this.hasNext())
				throw new NoSuchElementException();
			
			E ret = arrays[array][this.pos++];
			
			while(pos >= arrays[array].length)
			{
				this.off += arrays[array++].length;
				this.pos = 0;
				if(array >= arrays.length)
					break;
			}
			
			return ret;
		}

		@Override
		public int nextIndex()
		{
			return this.off + this.pos;
		}

		@Override
		public boolean hasPrevious()
		{
			return (this.off + this.pos > 0);
		}

		@Override
		public E previous()
		{
			if(!this.hasPrevious())
				throw new NoSuchElementException();
			
			this.pos--;
			
			while(pos < 0)
			{
				this.off -= arrays[--array].length;
				this.pos = arrays[array].length - 1;
			}
			
			return arrays[array][pos];
		}

		@Override
		public int previousIndex()
		{
			return this.off + this.pos - 1;
		}

		@Override
		public void add(E e)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E e)
		{
			throw new UnsupportedOperationException();
		}
	}
}
