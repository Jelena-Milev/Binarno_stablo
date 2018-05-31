package stablo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cvor.BSTCvor;

public class BinarnoStablo<T> implements Map<Integer, T> {

	private BSTCvor<T> koren = null;

	@Override
	public void clear() {
		koren = null;

	}

	@Override
	public boolean containsKey(Object key) {
		if (koren == null)
			return false;
		BSTCvor<T> cvor=nadjiKljucPrivate((Integer)key, koren);
		if(cvor==null)
			return false;
		else return true;
	}

	public T nadjiKljuc(Integer kljuc) throws Exception{
		BSTCvor<T> cvor=nadjiKljucPrivate(kljuc, koren);
		if(cvor==null)
			throw new Exception("Kljuc ne postoji u stablu");
		return cvor.vrednost;
	}
	
	private BSTCvor<T> nadjiKljucPrivate(Integer kljuc, BSTCvor<T> k){
		if(k==null)
			return null;
		if(k.kljuc.equals(kljuc))
			return k;
		BSTCvor<T> cvor=nadjiKljucPrivate(kljuc, k.levo);
		if(cvor!=null)
			return cvor;
		else return nadjiKljucPrivate(kljuc, k.desno);
		
	}
	@Override
	public boolean containsValue(Object value) {
		return nadjiVrednost(koren, value);
	}

	private boolean nadjiVrednost(BSTCvor<T> k, Object value) {
		if(k==null)
			return false;
		if(k.vrednost.equals(value))
			return true;
		return nadjiVrednost(k.levo, value) || nadjiVrednost(k.desno, value);
	}
	
	@Override
	public Set<Entry<Integer, T>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return koren == null;
	}

	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T put(Integer key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends T> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public T remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return size(koren);
	}

	private int size(BSTCvor<T> koren) {
		if (koren == null)
			return 0;
		return 1 + size(koren.levo) + size(koren.desno);
	}

	@Override
	public Collection<T> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
