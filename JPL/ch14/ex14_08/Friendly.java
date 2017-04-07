package ex14_08;

public class Friendly
{	
	private Friendly partner;
	private String name;
	
	public Friendly(String name)
	{
		this.name = name;
	}
	
	public synchronized void hug()
	{
		System.out.println(Thread.currentThread().getName() + 
			" in " + name + ".hug() trying to invoke " + partner.name + ".hugBack()");
		
		// デッドロック発生のための遅延
		try
		{
			Thread.sleep(10);
		}
		catch(InterruptedException e)
		{ }
		
		// デッドロック対策
		// Friendlyオブジェクトの1つだけが自身のロックを解除することでロックの対称性を崩す
		try
		{
			synchronized(Friendly.class)
			{
				this.wait(10);
			}
		}
		catch(InterruptedException e)
		{ }

		partner.hugback();
	}
	
	public synchronized void hugback()
	{
		System.out.println(Thread.currentThread().getName() + 
			" in " + name + ".hugBack()");
	}
	
	public void becomeFriend(Friendly partner)
	{
		this.partner = partner;
	}
	
	private static void friend()
	{
		final Friendly jareth = new Friendly("jareth");
		final Friendly cory = new Friendly("cory");
		
		jareth.becomeFriend(cory);
		cory.becomeFriend(jareth);
		
		new Thread(new Runnable()
		{
			@Override
			public void run() { jareth.hug(); }
		}, "Thread1").start();

		new Thread(new Runnable()
		{
			@Override
			public void run() { cory.hug(); }
		}, "Thread2").start();
	}
	
	public static void main(String[] args)
	{
		friend();
	}
}
