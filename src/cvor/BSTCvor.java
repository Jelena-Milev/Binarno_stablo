package cvor;

public class BSTCvor<T> {

	private Integer kljuc;
	private T vrednost;
	private BSTCvor<T> levo, desno;

	public BSTCvor() {

	}

	public BSTCvor(Integer key, T value, BSTCvor<T> left, BSTCvor<T> right) {
		this.kljuc = key;
		this.vrednost = value;
		this.levo = left;
		this.desno = right;
	}

}
