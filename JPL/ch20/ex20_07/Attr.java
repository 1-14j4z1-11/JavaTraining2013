package ex20_07;

import java.io.*;

public class Attr implements Serializable
{
	private static final long serialVersionUID = -433000387009636849L;
	private static final int BUF_SIZE = 1024;
	
	private String name;
	private Object value;
	
	public Attr(String name)
	{
		this.name = name;
	}

	public Attr(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}
	
	public Attr(DataInputStream stream)
	{
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		
		try
		{
			byte[] data = new byte[BUF_SIZE];
			stream.read(data);
			
			bais = new ByteArrayInputStream(data);
			ois = new ObjectInputStream(bais);
			
			Object obj = ois.readObject();
			
			if(obj instanceof Attr)
			{
				Attr attr = (Attr)obj;
				
				this.name = attr.name;
				this.value = attr.value;
			}
		}
		catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ois != null)
				{
					ois.close();
				}
				if(bais != null)
				{
					bais.close();
				}
			}
			catch(IOException e)
			{ }
		}
	}

	public String getName()
	{
		return name;
	}
	
	public Object getValue()
	{
		return value;
	}

	public Object setValue(Object value)
	{
		Object old = this.value;
		this.value = value;
		return old;
	}
	
	public boolean output(DataOutputStream stream)
	{
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		
		try
		{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			byte[] data = baos.toByteArray();
			stream.write(data);

			return true;
		}
		catch(IOException e)
		{
			return false;
		}
		finally
		{
			try
			{
				if(oos != null)
				{
					oos.close();
				}
				if(baos != null)
				{
					baos.close();
				}
			}
			catch(IOException e)
			{ }
		}
	}
	
	@Override
	public String toString()
	{
		return (name + "='" + value + "'");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Attr other = (Attr) obj;
		
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		
		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		
		return true;
	}
}
