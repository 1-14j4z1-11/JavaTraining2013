package ex01_15;

public interface Lookup {
	public Object find(String name);
	public boolean add(String name, Object obj);
	public void remove(String name);
}
