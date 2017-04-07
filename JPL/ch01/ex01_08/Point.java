package ex01_08;

public class Point {
	// フィールド
	
	public double x, y;
	
	// コンストラクタ

	public Point() {
		clear();
	}

	public Point(double x, double y) {
		move(x, y);
	}
	
	// メソッド
	
	public void clear() {
		x = 0.0;
		y = 0.0;
	}
	
	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void toPoint(Point point) {
		if(point == null)
		{
			throw new NullPointerException();
		}
		
		point.x = x;
		point.y = y;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}
