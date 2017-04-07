package ex02_06;

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
		
		Vehicle vehicle1 = new Vehicle();
		Vehicle vehicle2 = new Vehicle();
		Vehicle vehicle3 = new Vehicle();
		
		vehicle1.id = Vehicle.nextID++;
		vehicle1.owner = "Taro";
		vehicle1.speed = 40;
		vehicle1.angle = 20;

		vehicle2.id = Vehicle.nextID++;
		vehicle2.owner = "Hanako";
		vehicle2.speed = 60;
		vehicle2.angle = 0;

		vehicle3.id = Vehicle.nextID++;
		vehicle3.owner = "Jiro";
		vehicle3.speed = 50;
		vehicle3.angle = 50;
		
		list.add(vehicle1);
		list.add(vehicle2);
		list.add(vehicle3);
		
		System.out.println(list);
	}
}
