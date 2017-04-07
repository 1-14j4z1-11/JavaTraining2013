package ex14_06;

public class Main extends Thread
{
	private static Object key = new Object();
	
	public static void main(String[] args)
	{
		Thread time = new Thread(new TimeCounter(key));
		Thread interval15 = new Thread(new Interval(key, 15));
		Thread interval7 = new Thread(new Interval(key, 7));
		
		time.start();
		interval15.start();
		interval7.start();
	}
}

class TimeCounter implements Runnable
{
	private static final long NOTIFY_INTERVAL = 1000;
	private final Object key;
	private long count = 0;
	
	public TimeCounter(Object key)
	{
		this.key = key;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			synchronized (key)
			{
				try
				{
					key.notifyAll();
					Thread.sleep(NOTIFY_INTERVAL);
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}

			System.out.printf("Time : %5d%n", ++this.count);
		}	
	}
}

class Interval implements Runnable
{
	private final Object key;
	private long count = 0;
	private final long interval;
	
	public Interval(Object key, int interval)
	{
		this.key = key;
		this.interval = interval;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			while((++this.count % interval) != 0)
			{
				synchronized (key)
				{
					try
					{
						key.wait();
					}
					catch (InterruptedException e)
					{
						Thread.currentThread().interrupt();
					}
				}
			}

			System.out.printf("<< Inteval %4d >>%n", this.interval);
		}	
	}
}