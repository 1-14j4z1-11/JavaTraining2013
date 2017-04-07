package ex03_10;

public class LinkedList implements Cloneable
{
	// 内部クラス
	
	class ListCell
	{
		// フィールド
		
		private Object obj;
		private ListCell next;
		
		// コンストラクタ
		
		public ListCell()
		{ }
		
		public ListCell(Object obj, ListCell next)
		{
			this.obj = obj;
			this.next = next;
		}
		
		// メソッド
		
		public Object getObject()
		{
			return this.obj;
		}
		
		public void setObject(Object obj)
		{
			this.obj = obj;
		}

		public ListCell getNext()
		{
			return this.next;
		}

		public void setNext(ListCell next)
		{
			this.next = next;
		}
	}

	// フィールド
	
	private ListCell list = new ListCell();
	private int count = 0;
	
	// コンストラクタ
	
	public LinkedList()
	{ }
	
	// メソッド
	
	public int getCount()
	{
		return this.count;
	}
	
	public Object getAt(int index)
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
		
		return cell.getObject();
	}
	
	public void add(Object obj)
	{
		ListCell cell = this.list;
		
		while(cell.getNext() != null)
		{
			cell = cell.getNext();
		}

		this.count++;
		cell.setNext(new ListCell(obj, null));
	}
	
	public void insert(int index, Object obj)
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
		cell.setNext(new ListCell(obj, cell.getNext()));
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
		cell.setNext(cell.getNext().getNext());
	}
	
	public void remove(Object obj)
	{
		ListCell cell = this.list;
		
		while(cell.getNext() != null)
		{
			ListCell tmp = cell.getNext();
			
			if(tmp.getObject().equals(obj))
			{
				this.count--;
				cell.setNext(tmp.getNext());
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
		String str = "<count = " + this.count + ">" + newLine;
		
		for(int i = 0; i < this.count; i++)
		{
			str += this.getAt(i).toString() + newLine;
		}
		
		return str;
	}
	
	@Override
	public LinkedList clone()
	{
		try
		{
			LinkedList newList = (LinkedList)super.clone();
			
			newList.count = 0;
			newList.list = new ListCell();
			
			for(int i = 0; i < count; i++)
			{
				newList.add(getAt(i));
			}
			
			return newList;
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}
}
