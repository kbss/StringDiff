package org.juke.string.diff;

public class StringDiff {

	private String content;
	private DiffType type;

	public StringDiff(String diffContent, DiffType diffType) {
		this.content = diffContent;
		this.type = diffType;
	}

	public String getContent() {
		return content;
	}

	public DiffType getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(DiffType type) {
		this.type = type;
	}
}
