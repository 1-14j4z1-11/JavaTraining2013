package ex10_03;

public class Main
{
	public static boolean isWorkingDay_IfElse(Weekday day)
	{
		if(day == Weekday.Monday)
			return true;
		
		else if(day == Weekday.Tuesday)
			return true;

		else if(day == Weekday.Wednesday)
			return true;

		else if(day == Weekday.Thursday)
			return true;

		else if(day == Weekday.Friday)
			return true;

		else if(day == Weekday.Saturday)
			return false;

		else if(day == Weekday.Sunday)
			return false;
		
		else
			throw new AssertionError();
	}
	
	public static boolean isWorkingDay_Switch(Weekday day)
	{
		switch(day)
		{
			case Monday:
			case Tuesday:
			case Wednesday:
			case Thursday:
			case Friday:
				return true;
			
			case Sunday:
			case Saturday:
				return false;
				
			default:
				throw new AssertionError();
		}
		
	}
	public static void main(String[] args)
	{
		System.out.println(isWorkingDay_Switch(Weekday.Monday));
		System.out.println(isWorkingDay_IfElse(Weekday.Saturday));
	}
}
