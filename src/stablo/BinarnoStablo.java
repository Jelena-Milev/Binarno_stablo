package stablo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
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
		BSTCvor<T> cvor = nadjiKljucPrivate((Integer) key, koren);
		if (cvor == null)
			return false;
		else
			return true;
	}

	public T pretragaPoKljucu(Integer kljuc) throws Exception {
		BSTCvor<T> cvor = nadjiKljucPrivate(kljuc, koren);
		if (cvor == null)
			throw new Exception("Kljuc ne postoji u stablu");
		return cvor.vrednost;
	}

	private BSTCvor<T> nadjiKljucPrivate(Integer kljuc, BSTCvor<T> k) {
		if (k == null)
			return null;
		if (k.kljuc.equals(kljuc))
			return k;
		if (k.kljuc < kljuc)
			return nadjiKljucPrivate(kljuc, k.desno);
		else
			return nadjiKljucPrivate(kljuc, k.levo);
	}

	@Override
	public boolean containsValue(Object value) {
		return nadjiVrednost(koren, value);
	}

	private boolean nadjiVrednost(BSTCvor<T> k, Object value) {
		if (k == null)
			return false;
		if (k.vrednost.equals(value))
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
		if (koren == null)
			return null;
		return vratiKljuceve();
	}

	private Set<Integer> vratiKljuceve() {
		Set<Integer> kljucevi=new HashSet<>();
		dodajKljuceve(koren, kljucevi);
		return kljucevi;
	}

	private void dodajKljuceve(BSTCvor<T> k, Set<Integer> kljucevi) {
		if(k==null)
			return;
		kljucevi.add(k.kljuc);
		dodajKljuceve(k.levo, kljucevi);
		dodajKljuceve(k.desno, kljucevi);
		
	}

	@Override
	public T put(Integer key, T value) {
		BSTCvor<T> cvor = nadjiKljucPrivate(key, koren);
		if (cvor == null) {
			ubaci(key, value, koren);
			return null;
		} else {
			T vrednost = cvor.vrednost;
			cvor.vrednost = value;
			return vrednost;
		}
	}

	private void ubaci(Integer key, T value, BSTCvor<T> koren) {
		if (koren == null)
			koren = new BSTCvor<T>(key, value);
		if (koren.kljuc < key) {
			if (koren.levo == null) {
				koren.levo = new BSTCvor<T>(key, value);
			} else
				ubaci(key, value, koren.levo);
		}
		if (koren.kljuc > key) {
			if (koren.desno == null) {
				koren.desno = new BSTCvor<T>(key, value);
			} else
				ubaci(key, value, koren.desno);
		}
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends T> m) {
		

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
		if (koren == null)
			return null;
		return vratiVrednostiCvorova();
	}

	private Collection<T> vratiVrednostiCvorova() {
		Collection<T> vrednosti = new LinkedList<T>();
		vratiVrednostiCvorovaPrivate(koren, vrednosti);
		return vrednosti;
	}

	private void vratiVrednostiCvorovaPrivate(BSTCvor<T> k, Collection<T> vrednosti) {
		if (k == null)
			return;
		vrednosti.add(k.vrednost);
		vratiVrednostiCvorovaPrivate(k.levo, vrednosti);
		vratiVrednostiCvorovaPrivate(k.desno, vrednosti);

	}

}
