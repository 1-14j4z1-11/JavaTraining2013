package ex01_10;

public class NumberItem {
	// �t�B�[���h
	
	private int number;
	private boolean isEven;
	
	// �R���X�g���N�^
	public NumberItem()
	{ }
	
	public NumberItem(int number, boolean isEven) {
		setNumberItem(number, isEven);
	}
	
	// �A�N�Z�T
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
