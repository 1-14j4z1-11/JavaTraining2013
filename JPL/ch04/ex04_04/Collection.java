package ex04_04;

interface Iterator<T>
{
	boolean hasNext();
	T next();
}

interface Collection<T>
{
	Iterator<T> iterator();
}

interface List<T> extends Collection<T>
{
	void add(T obj);
	void insert(int index, T obj);
	void remove(T obj);
	void removeAt(int index);
	void clear();
}

interface Array<T> extends Collection<T>
{
	T getAt(int index);
	void setAt(int index, T obj);
}

interface Map<T1, T2>
{
	T2 getValue(T1 key);
	void add(T1 key, T2 value);
	void remove(T1 key);
	void clear();
}

interface LinkedList<T> extends List<T>
{
	// 実際には具象クラス
}

interface ArrayList<T> extends List<T>, Array<T>
{
	// 実際には具象クラス
}