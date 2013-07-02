package org.juke.string.diff;

public class Element {

	private DiffType type;
	private char element;
	private int index;

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Element(char s, int index) {
		this.element = s;
		this.index = index;
	}

	public DiffType getType() {
		return type;
	}

	public void setType(DiffType type) {
		this.type = type;
	}

	public char getElement() {
		return element;
	}

	public void setElement(char element) {
		this.element = element;
	}

	@Override
	public String toString() {
		return String.valueOf(element);
	}
}
