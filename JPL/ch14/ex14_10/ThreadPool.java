
/*
 * Copyright (C) 2012, 2013 RICOH Co., Ltd. All rights reserved.
 */

import java.util.ArrayList;

/**
 * Simple Thread Pool class.
 *
 * This class can be used to dispatch an Runnable object to
 * be executed by a thread.
 *
 * [Instruction]
 *  Implement one constructor and three methods.
 *  Don't forget to write a Test program to test this class. 
 *  Pay attention to @throws tags in the javadoc.
 *  If needed, you can put "synchronized" keyword to methods.
 *  All classes for implementation must be private inside this class.
 *  Don't use java.util.concurrent package.
 */
public class ThreadPool
{
	private static final int SLEEP_INTERVAL = 1;
	
	private boolean running = false;
	private final int queueSize;
	private final ReusableThread[] threads;
	private final ArrayList<Runnable> queue;
	private final ThreadControl threadControl;
	
    /**
     * Constructs ThreadPool.
     *
     * @param queueSize the max size of queue
     * @param numberOfThreads the number of threads in this pool.
     *
     * @throws IllegalArgumentException if either queueSize or numberOfThreads
     *         is less than 1
     */
    public ThreadPool(int queueSize, int numberOfThreads)
    {
    	if((queueSize < 1) || (numberOfThreads < 1))
		{
			throw new IllegalArgumentException();
		}
		
		this.queueSize = queueSize;
		this.threads = new ReusableThread[numberOfThreads];
		this.queue = new ArrayList<Runnable>(queueSize);
		this.threadControl = new ThreadControl();
		
		for(int i = 0; i < threads.length; i++)
		{
			this.threads[i] = new ReusableThread();
			this.threads[i].start();
		}
		
		this.threadControl.start();
    }

    /**
     * Starts threads.
     *
     * @throws IllegalStateException if threads has been already started.
     */
    public void start()
    {
    	if(this.running)
		{
			throw new IllegalStateException();
		}
		
		this.running = true;
    }   

    /**
     * Stop all threads and wait for their terminations.
     *
     * @throws IllegalStateException if threads has not been started.
     */
    public void stop()
    {
    	if(!this.running)
		{
			throw new IllegalStateException();
		}

		this.running = false;
		
		int count = 1;
		
		while((count != 0) || (this.queue.size() > 0))
		{
			count = 0;
			
			for(ReusableThread t : this.threads)
			{
				if(t.isUsing())
				{
					count++;
				}
			}
		}
		
		for(ReusableThread t : this.threads)
		{
			t.deactivate();
		}

		count = 1;
		
		while(count != 0)
		{
			count = 0;
			
			for(Thread t : this.threads)
			{
				if(t.isAlive())
				{
					count++;
				}
			}
		}
    }

    /**
     * Executes the specified Runnable object, using a thread in the pool.
     * run() method will be invoked in the thread. If the queue is full, then
     * this method invocation will be blocked until the queue is not full.
     * 
     * @param runnable Runnable object whose run() method will be invoked.
     *
     * @throws NullPointerException if runnable is null.
     * @throws IllegalStateException if this pool has not been started yet.
     */
    public synchronized void dispatch(Runnable runnable)
    {
    	if(runnable == null)
		{
			throw new NullPointerException();
		}
		
		if(!this.running)
		{
			throw new IllegalStateException();
		}
		
		synchronized(this.queue)
		{
			while(this.queue.size() >= this.queueSize)
			{
				try
				{
					this.queue.wait();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			this.queue.add(runnable);
		}
    }
	
	private class ThreadControl extends Thread
	{
		@Override
		public void run()
		{
			while(true)
			{
				synchronized(queue)
				{
					for(int i = 0; (i < threads.length) && !queue.isEmpty(); i++)
					{
						if(threads[i].isUsing())
						{
							continue;
						}
						
						threads[i].setRunnable(queue.remove(0));
					}
					
					queue.notifyAll();
				}
				
				try
				{
					Thread.sleep(SLEEP_INTERVAL);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class ReusableThread extends Thread
	{
		private Runnable runnable;
		private boolean active = true;
		
		public final boolean isUsing()
		{
			return (this.runnable != null);
		}	
		
		public final void deactivate()
		{
			this.active = false;
		}
		
		public final void setRunnable(Runnable runnable)
		{
			if(this.isUsing())
			{
				throw new IllegalStateException();
			}
			
			this.runnable = runnable;
		}
		
		@Override
		public void run()
		{
			while(this.active)
			{
				if(this.runnable != null)
				{
					this.runnable.run();
					this.runnable = null;
				}
				
				try
				{
					Thread.sleep(SLEEP_INTERVAL);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
