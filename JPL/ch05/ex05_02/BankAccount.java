package ex05_02;

import java.util.ArrayList;

public class BankAccount
{
	private final int MAX_ACTION = 10;
	
	private long number;
	private long balance;
	private ArrayList<Action> actions = new ArrayList<>();
	
	public BankAccount(long number)
	{
		this.number = number;
	}
	
	public void deposit(long amount)
	{
		this.balance += amount;
		this.actions.add(new Action("deposit", amount));
		
		if(actions.size() == MAX_ACTION + 1)
		{
			actions.remove(0);
		}
	}

	public void withdraw(long amount)
	{
		this.balance -= amount;
		this.actions.add(new Action("withdraw", amount));
		
		if(actions.size() == MAX_ACTION + 1)
		{
			actions.remove(0);
		}
	}
	
	public History history()
	{
		return new History();
	}
	
	public class Action
	{
		private String act;
		private long amount;
		
		Action(String act, long amount)
		{
			this.act = act;
			this.amount = amount;
		}
		
		@Override
		public String toString()
		{
			return BankAccount.this.number + ": "
				+ this.act + " " + this.amount;
		}
	}
	
	// BankAccountのフィールドへのアクセスが必要なので非static内部クラスとすべき
	public class History
	{
		private int index = 0;
		
		public Action next()
		{
			if(index >= BankAccount.this.actions.size())
				return null;
			else
				return BankAccount.this.actions.get(index++);
		}
	}
	
	@Override
	public String toString()
	{
		return this.number + ":" + this.balance;
	}
}
