package org.privatebytes.problem186;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class SumIntList extends ArrayList<Integer> {

	private static final long serialVersionUID = 1L;
	private long elementsSum;
	
	private String name;

	
	public SumIntList(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public long getElementsSum() {
		return elementsSum;
	}
	
	@Override
	public void add(int index, Integer element) {
		modifySum(element);
		super.add(index, element);
	}

	@Override
	public boolean add(Integer e) {
		modifySum(e);
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		c.forEach(this::modifySum);
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		c.forEach(this::modifySum);
		return super.addAll(index, c);
	}

	@Override
	public Integer remove(int index) {
		Integer r = super.remove(index);

		modifySum(-r);

		return r;
	}

	@Override
	public boolean removeIf(Predicate<? super Integer> filter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		
		boolean result = super.remove(o);
		
		if(result) {
			modifySum(-1*(int)o);
		}
		
		return result;
	}
	
	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replaceAll(UnaryOperator<Integer> operator) {
		throw new UnsupportedOperationException();
	}

	private void modifySum(Integer e) {
		long newSum = elementsSum + e;

		//verify overflow at both ends
		if ((newSum < elementsSum && e > 0) || (newSum > elementsSum && e < 0)) {
			throw new IllegalArgumentException("Sum overflow when adding " + e + " to current sum " + elementsSum);
		}

		elementsSum = newSum;
	}

	
	@Override
	public String toString() {
		return name + "(" + elementsSum + ") --> " + super.toString();
	}
}
