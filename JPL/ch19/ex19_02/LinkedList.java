package ex19_02;

public class LinkedList
{
	private static class ListCell
	{
		private Object obj = null;
		private ListCell next = null;
		
		/**
		 * リスト要素のインスタンスを生成します
		 * @param obj リストの要素の値
		 * @param next 次のリスト要素への参照
		 */
		public ListCell(Object obj, ListCell next)
		{
			this.obj = obj;
			this.next = next;
		}
		
		/**
		 * このリスト要素の値を取得します
		 */
		public Object getObject()
		{
			return this.obj;
		}
		
		/**
		 * 次のリスト要素を取得します
		 */
		public ListCell getNext()
		{
			return this.next;
		}
		
		/**
		 * 次のリスト要素を設定します
		 */
		public void setNext(ListCell next)
		{
			this.next = next;
		}
	}

	// フィールド
	
	private ListCell list = new ListCell(null, null);
	private int count = 0;
	
	// コンストラクタ
	
	/**
	 * リストのインスタンスを生成します
	 */
	public LinkedList()
	{ }
	
	// メソッド
	
	/**
	 * リストの要素数を取得します
	 */
	public int getCount()
	{
		return this.count;
	}
	
	/**
	 * 先頭からindex番目の要素を取得します
	 * @param index 取得する要素のindex
	 * @return index番目の要素
	 * @exception IndexOutOfBoundsException indexが負か要素数を超える場合
	 */
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
	
	/**
	 * リストの末尾に要素を追加します
	 * @param obj 追加する要素
	 */
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
	
	/**
	 * indexの位置に要素を挿入します
	 * @param index 要素の挿入位置
	 * @param obj 挿入する要素
	 * @exception IndexOutOfBoundsException indexが負か要素数を超える場合
	 */
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
	
	/**
	 * リスト内のindex番目の要素を削除します
	 * @param index 削除する要素の位置
	 * @exception IndexOutOfBoundsException indexが負か要素数を超える場合
	 */
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
	
	/**
	 * objと一致するリスト内の要素を全て削除します
	 * @param obj 削除対象の要素
	 */
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

	/**
	 * リストの要素数と要素を文字列に変換します
	 */
	@Override
	public String toString()
	{
		String newLine = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("<count = ").append(this.count).append(">").append(newLine);

		ListCell cell = this.list.getNext();
		
		while(cell != null)
		{
			sb.append(cell.getObject().toString() + newLine);
			cell = cell.getNext();
		}
		
		return sb.toString();
	}
}
