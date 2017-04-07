package ex14_02;

import java.util.ArrayList;

public class Main
{
	public static void main(String[] args)
	{
		PrintServer server = new PrintServer();
		
		server.run();
		
		server.print(new PrintJob("Test1"));
		server.print(new PrintJob("Test2"));
		server.print(new PrintJob("Test3"));
		server.print(new PrintJob("Test4"));
	}
}

class PrintServer implements Runnable
{
	private final PrintQueue requests = new PrintQueue();
	private final Thread thread;
	
	public PrintServer()
	{
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void print(PrintJob job)
	{
		requests.add(job);
	}
	
	public void run()
	{
		if(!Thread.currentThread().equals(this.thread))
		{
			System.out.println("IllegalCall");
			return;
		}
		
		for(;;)
		{
			realPaint(requests.remove());
		}
	}

	private void realPaint(PrintJob job)
	{
		if(job != null)
		{
			System.out.println("Print : " + job.name);
		}
	}
}

class PrintJob
{
	public final String name;
	
	public PrintJob(String name)
	{
		this.name = name;
	}
}

class PrintQueue
{
	ArrayList<PrintJob> jobs = new ArrayList<PrintJob>();
	
	public void add(PrintJob job)
	{
		jobs.add(job);
	}
	
	public PrintJob remove()
	{
		if(jobs.size() == 0)
		{
			return null;
		}
		
		return jobs.remove(0);
	}
}