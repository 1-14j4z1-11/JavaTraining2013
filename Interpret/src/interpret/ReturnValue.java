package interpret;

public class ReturnValue
{
	private final Object returnObj;
	private final Throwable exception;
	
	public ReturnValue(Object returnObj, Throwable exception)
	{
		this.returnObj = returnObj;
		this.exception = exception;
	}
	
	public boolean isException()
	{
		return (this.exception != null);
	}
	
	public final Object getReturnObject()
	{
		return this.returnObj;
	}

	public final Throwable getException()
	{
		return this.exception;
	}
}
