package ex01_08;

public class Point {
	// �t�B�[���h
	
	public double x, y;
	
	// �R���X�g���N�^

	public Point() {
		clear();
	}

	public Point(double x, double y) {
		move(x, y);
	}
	
	// ���\�b�h
	
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
