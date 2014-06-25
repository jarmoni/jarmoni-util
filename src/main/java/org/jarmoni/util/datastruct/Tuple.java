package org.jarmoni.util.datastruct;

public class Tuple<F, S> {

	private F first;
	private S second;

	public Tuple(final F first, final S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return this.first;
	}

	public void setFirst(final F first) {
		this.first = first;
	}

	public S getSecond() {
		return this.second;
	}

	public void setSecond(final S second) {
		this.second = second;
	}
}
