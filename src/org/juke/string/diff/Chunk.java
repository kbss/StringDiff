package org.juke.string.diff;

public class Chunk {
	private DiffType type;
	private String content;
	private int index;

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Chunk(String s1, DiffType type) {
		this.content = s1;
		this.type = type;
	}

	public DiffType getType() {
		return type;
	}

	public void setType(DiffType type) {
		this.type = type;
	}

	public void setString(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return content;
	}
}
