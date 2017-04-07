package ex22_04;

public class Attr
{
	private final String name;
	private Object value;

	public Attr()
	{
		this.name = null;
		this.value = null;
	}

	public Attr(String name)
	{
		this.name = name;
		this.value = null;
	}

	public Attr(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return this.name;
	}

	public Object getValue()
	{
		return this.value;
	}

	public Object setValue(Object obj)
	{
		Object old = this.value;
		this.value = obj;
		return old;
	}

	public String toString()
	{
		return name + "='" + this.value.toString() + "'";
	}
}
