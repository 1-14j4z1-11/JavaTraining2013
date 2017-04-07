package ex11_01;

public class LinkedList<T> implements ILinkedList<T>
{
	public class ListCell
	{
		// フィールド
		
		private T value = null;
		private ListCell next = null;
		
		// コンストラクタ
		
		public ListCell()
		{ }
		
		public ListCell(T value, ListCell next)
		{
			this.value = value;
			this.next = next;
		}
		
		// メソッド
		
		public final T getValue()
		{
			return this.value;
		}
		
		public final void setValue(T value)
		{
			this.value = value;
		}

		public final ListCell getNext()
		{
			return this.next;
		}

		public final void setNext(ListCell next)
		{
			this.next = next;
		}
	}

	// フィールド
	
	private ListCell list = new ListCell(null, null);
	private int count = 0;
	
	// コンストラクタ
	
	public LinkedList()
	{ }
	
	// メソッド
	
	public int getCount()
	{
		return this.count;
	}
	
	public T getAt(int index)
	{
		if((index < 0) || (index >= this.count))
		{
			throw new IndexOutOfBoundsException();
		}
		
		ListCell cell = this.list.getNext();
		
		try
		{
			for(int i = 0; i < index; i++)
			{
				cell = cell.getNext();
			}
		}
		catch(NullPointerException e)
		{
			throw new IndexOutOfBoundsException();
		}
		
		return cell.getValue();
	}
	
	public void add(T value)
	{
		ListCell cell = this.list;
		
		while(cell.getNext() != null)
		{
			cell = cell.getNext();
		}

		this.count++;
		cell.setNext(new ListCell(value, null));
	}
	
	public void insert(int index, T value)
	{
		if(index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		
		ListCell cell = this.list;
		
		try
		{
			for(int i = 0; i < index; i++)
			{
				cell = cell.getNext();
			}
		}
		catch(NullPointerException e)
		{
			throw new IndexOutOfBoundsException();
		}

		this.count++;
		cell.setNext(new ListCell(value, cell.getNext()));
	}
	
	public void removeAt(int index)
	{
		if(index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		
		ListCell cell = this.list;
		
		try
		{
			for(int i = 0; i < index; i++)
			{
				cell = cell.getNext();
			}
		}
		catch(NullPointerException e)
		{
			throw new IndexOutOfBoundsException();
		}
		
		this.count--;
		ListCell tmp = cell.getNext();
		cell.setNext(tmp.getNext());
		tmp.setNext(null);
	}
	
	public void remove(T value)
	{
		ListCell cell = this.list;
		
		while(cell.getNext() != null)
		{
			ListCell tmp = cell.getNext();
			
			if(tmp.getValue().equals(value))
			{
				this.count--;
				cell.setNext(tmp.getNext());
				tmp.setNext(null);
			}
			
			else
			{
				cell = cell.getNext();
			}
		}
	}
	
	@Override
	public String toString()
	{
		String newLine = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("<count = " + this.count + ">" + newLine);

		ListCell cell = this.list.getNext();
		
		while(cell != null)
		{
			sb.append(cell.getValue().toString() + newLine);
			cell = cell.getNext();
		}
		
		return sb.toString();
	}
}
