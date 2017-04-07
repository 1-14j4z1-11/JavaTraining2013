package ex14_09;

public class Main
{
	private static void printThreadGroup(ThreadGroup parent, int count)
	{
		Thread[] threads = new Thread[parent.activeCount()];
		parent.enumerate(threads, false);

		ThreadGroup[] threadGroups = new ThreadGroup[parent.activeGroupCount()];
		parent.enumerate(threadGroups, false);

		for(int i = 0; i < count; i++) { System.out.print("  "); }
		System.out.println(parent.getName());
		for(int i = 0; i < count; i++) { System.out.print("  "); }
		System.out.println(" <Threads>");
		
		for(Thread t : threads)
		{
			if(t == null)
			{
				continue;
			}
			
			for(int i = 0; i < count; i++) { System.out.print("  "); }
			System.out.println("  " + t.getName());
		}

		for(int i = 0; i < count; i++) { System.out.print("  "); }
		System.out.println(" <ThreadGroups>");
		
		for(ThreadGroup g : threadGroups)
		{
			if(g == null)
			{
				continue;
			}
			
			printThreadGroup(g, count + 1);
		}
	}
	
	public static void main(String[] args)
	{
		ThreadGroup group1 = new ThreadGroup("group1");
		ThreadGroup group1_1 = new ThreadGroup(group1, "group1_1");
		ThreadGroup group1_2 = new ThreadGroup(group1, "group1_2");
		ThreadGroup group1_1_1 = new ThreadGroup(group1_1, "group1_1_1");
		ThreadGroup group1_1_2 = new ThreadGroup(group1_1, "group1_1_2");
		ThreadGroup group1_2_1 = new ThreadGroup(group1_2, "group1_2_1");
		ThreadGroup group1_2_2 = new ThreadGroup(group1_2, "group1_2_2");
		
		SampleThread[] threads = new SampleThread[14];
		
		for(int i = 0; i < threads.length; i++)
		{
			ThreadGroup group = (i < 2) ? group1 :
								(i < 5) ? group1_1 :
								(i < 8) ? group1_2 :
								(i < 9) ? group1_1_1 :
								(i < 10) ? group1_1_2 :
								(i < 11) ? group1_2_1 : group1_2_2;
			
			threads[i] = new SampleThread(group, "t" + i, i * 500);
		}

		for(Thread t : threads)
		{
			t.start();
		}
		
		for(int i = 0; i < 10; i++)
		{
			printThreadGroup(group1, 0);
			System.out.println();
			
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{ }
		}
	}
}

class SampleThread extends Thread
{
	private final int time;
	
	public SampleThread(ThreadGroup group, String name, int time)
	{
		super(group, name);
		
		this.time = time;
	}
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(time);
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}