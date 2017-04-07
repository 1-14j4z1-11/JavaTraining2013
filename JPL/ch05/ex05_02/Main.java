package ex05_02;

public class Main
{
	public static void main(String[] args)
	{
		BankAccount account = new BankAccount(1234567);
		
		account.deposit(10000);
		account.deposit(20000);
		account.withdraw(5000);
		account.withdraw(30000);
		account.deposit(40000);
		
		BankAccount.History history = account.history();
		
		while(true)
		{
			BankAccount.Action action = history.next();
			
			if(action == null)
				break;
			
			System.out.println(action);
		}
	}
}
