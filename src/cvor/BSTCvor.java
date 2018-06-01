package cvor;

public class BSTCvor<T> {

	public Integer kljuc;
	public T vrednost;
	public BSTCvor<T> levo, desno;

	public BSTCvor(Integer key, T value) {
		this.kljuc = key;
		this.vrednost = value;
		this.levo = null;
		this.desno = null;
	}
	
	
	public BSTCvor(Integer key, T value, BSTCvor<T> left, BSTCvor<T> right) {
		this.kljuc = key;
		this.vrednost = value;
		this.levo = left;
		this.desno = right;
	}

	
}
