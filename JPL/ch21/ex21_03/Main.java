package ex21_03;

public class Main
{
	public static void main(String[] args)
	{
		String[] values = { new String("One"), new String("Two"), new String("Three") };
		WeakValueMap<Integer, String> map = new WeakValueMap<>();
		
		for(int i = 0; i < values.length; i++)
		{
			map.put(i, values[i]);
		}
		
		System.out.println("Map size : " + map.size() + " " + map.values());
		
		values[1] = null;
		System.gc();
		
		System.out.println("Map size : " + map.size() + " " + map.values());
		System.out.printf("[Key] 0 : [Value] %s%n", map.get(0));
		System.out.printf("[Key] 1 : [Value] %s%n", map.get(1));
		System.out.printf("[Key] 1 : [Value] %s%n", map.get(2));
	}
}
