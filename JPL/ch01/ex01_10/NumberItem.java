package ex01_10;

public class NumberItem {
	// フィールド
	
	private int number;
	private boolean isEven;
	
	// コンストラクタ
	public NumberItem()
	{ }
	
	public NumberItem(int number, boolean isEven) {
		setNumberItem(number, isEven);
	}
	
	// アクセサ
	public int getNumber() {
		return number;
	}
	
	public boolean getIsEven() {
		return isEven;
	}
	
	public void setNumberItem(int number, boolean isEven) {
		this.number = number;
		this.isEven = isEven;
	}
}
