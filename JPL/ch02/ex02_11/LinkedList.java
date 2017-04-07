package ex02_11;

public class LinkedList
{
	// 内部クラス
	
	class ListCell
	{
		// フィールド
		
		private Object obj = null;
		private ListCell next = null;
		
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
		ListCell tmp = cell.getNext();
		cell.setNext(tmp.getNext());
		tmp.setNext(null);
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
		String str = "<count = " + this.count + ">" + newLine;
		
		for(int i = 0; i < this.count; i++)
		{
			str += this.getAt(i).toString() + newLine;
		}
		
		return str;
	}
	
	// メイン関数
	
	public static void main(String[] args)
	{
		LinkedList list = new LinkedList();
		
		for(int i = 0; i < 5; i++)
		{
			list.add(i);
		}
		
		System.out.println(list.toString());
	}
}
