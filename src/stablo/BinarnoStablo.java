package stablo;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import cvor.BSTCvor;

public class BinarnoStablo<T> implements Map<Integer, T> {

	private BSTCvor<T> koren;

	public BinarnoStablo() {
		this.koren = null;
	}

	@Override
	public void clear() {
		koren = null;

	}

	@Override
	public boolean containsKey(Object key) {
		if (isEmpty())
			return false;
		BSTCvor<T> cvor = nadjiKljucPrivate((Integer) key, koren);
		if (cvor == null)
			return false;
		else
			return true;
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
		if (isEmpty())
			return null;
		Set<Entry<Integer, T>> elementi = new HashSet<>();
		izvuciElemente(koren, elementi);
		return elementi;
	}

	private void izvuciElemente(BSTCvor<T> k, Set<Entry<Integer, T>> elementi) {
		if (k == null)
			return;
		Map.Entry<Integer, T> entry = new AbstractMap.SimpleEntry<Integer, T>(k.kljuc, k.vrednost);
		elementi.add(entry);
		izvuciElemente(k.levo, elementi);
		izvuciElemente(k.desno, elementi);

	}

	@Override
	public T get(Object key) {
		BSTCvor<T> cvor = nadjiKljucPrivate((Integer) key, koren);
		if (cvor == null)
			return null;
		else
			return cvor.vrednost;
	}

	@Override
	public boolean isEmpty() {
		return koren == null;
	}

	@Override
	public Set<Integer> keySet() {
		if (isEmpty())
			return null;
		return vratiKljuceve();
	}

	private Set<Integer> vratiKljuceve() {
		Set<Integer> kljucevi = new HashSet<>();
		dodajKljuceve(koren, kljucevi);
		return kljucevi;
	}

	private void dodajKljuceve(BSTCvor<T> k, Set<Integer> kljucevi) {
		if (k == null)
			return;
		kljucevi.add(k.kljuc);
		dodajKljuceve(k.levo, kljucevi);
		dodajKljuceve(k.desno, kljucevi);

	}

	@Override
	public T put(Integer key, T value) {
		BSTCvor<T> cvor = nadjiKljucPrivate(key, koren);
		if (cvor == null) {
			ubaci(key, value);
			return null;
		} else {
			T vrednost = cvor.vrednost;
			cvor.vrednost = value;
			return vrednost;
		}
	}

	private void ubaci(Integer key, T value) {
		if (isEmpty()) {
			this.koren = new BSTCvor<T>(key, value);
			return;
		}
		ubaciPrivate(key, value, koren);
	}

	private void ubaciPrivate(Integer key, T value, BSTCvor<T> k) {
		if (k.kljuc > key) {
			if (k.levo == null) {
				k.levo = new BSTCvor<T>(key, value);
			} else
				ubaciPrivate(key, value, k.levo);
		} else if (k.kljuc < key) {
			if (k.desno == null) {
				k.desno = new BSTCvor<T>(key, value);
			} else
				ubaciPrivate(key, value, k.desno);
		}
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends T> m) {
		m.forEach((k, v) -> put(k, v));
	}

	@Override
	public T remove(Object key) {
		if (isEmpty())
			return null;
		BSTCvor<T> cvor = nadjiKljucPrivate((Integer) key, koren);
		if (cvor == null)
			return null;
		if ((cvor.levo == null && cvor.desno == null) || (cvor.levo == null ^ cvor.desno == null)) {
			return izbaciListPoluList(cvor);
		} else {
			BSTCvor<T> maxL = max(cvor.levo);
			T vrednost = cvor.vrednost;
			cvor.vrednost = maxL.vrednost;
			maxL.vrednost = vrednost;
			return izbaciListPoluList(maxL);
		}
	}

	private BSTCvor<T> max(BSTCvor<T> k) {
		if (k == null)
			return null;
		if (k.desno == null)
			return k;
		return max(k.desno);
	}

	private T izbaciListPoluList(BSTCvor<T> cvor) {
		BSTCvor<T> roditelj = vratiRoditelja(koren, cvor);
		BSTCvor<T> dete = cvor.levo != null ? cvor.levo : cvor.desno;
		if (roditelj == null) {
			koren = dete;
			return cvor.vrednost;
		}
		if (roditelj.levo == cvor) {
			roditelj.levo = dete;
			return cvor.vrednost;
		} else {
			roditelj.desno = dete;
			return cvor.vrednost;
		}
	}

	private BSTCvor<T> vratiRoditelja(BSTCvor<T> k, BSTCvor<T> cvor) {
		if (k == null || k == cvor)
			return null;
		if (cvor.kljuc < k.kljuc) {
			if (k.levo == cvor) {
				return k;
			}
			return vratiRoditelja(k.levo, cvor);
		} else {
			if (k.desno == cvor) {
				return k;
			}
			return vratiRoditelja(k.desno, cvor);
		}
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
