package ex11_01;

interface ILinkedList<T>
{
	void add(T value);
	void insert(int index, T value);
	void remove(T value);
	void removeAt(int index);
}
