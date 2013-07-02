package org.juke.string.diff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Diff {

	private List<Chunk> chunkList;
	private DiffType prevType;
	private String diffContent = "";
	private int diffSize = -1;
	private Chunk prevChunk;

	private int prevIndex = -1;

	public Diff() {
		chunkList = new ArrayList<Chunk>();
	}

	private void addElement(Element el) {
		if (prevType != null && prevType != el.getType()) {
			flush();
		}
		if (prevIndex == -1) {
			prevIndex = el.getIndex();
		}
		prevType = el.getType();
		diffContent += String.valueOf(el.getElement());
	}

	public void flush() {
		if (diffContent != null && !diffContent.isEmpty()) {

			prevChunk = new Chunk(diffContent, prevType);
			prevChunk.setIndex(prevIndex);
			chunkList.add(prevChunk);
			diffContent = "";
			prevIndex = -1;
		}
	}

	public int getDiffSize() {
		if (diffSize == -1) {
			diffSize = 0;
			Chunk previous = null;
			for (Chunk c : chunkList) {
				if (previous != null
				// ^ (previous
				// .getType() == DiffType.EQUALS && c.getType() ==
				// DiffType.DELETED)
						&& ((previous.getIndex() == c.getIndex()))) {
					int prevChunkLength = previous.toString().length();
					int currentLength = c.toString().length();
					int size;
					if (currentLength == prevChunkLength) {
						size = 0;
					} else if (prevChunkLength > currentLength) {
						size = -currentLength;
					} else {
						size = currentLength - prevChunkLength;
					}
					diffSize += size;
				} else if (c.getType() != DiffType.EQUALS) {
					diffSize += c.toString().length();
				}
				previous = c;
			}
		}
		return diffSize;
	}

	public Collection<Chunk> getChunks() {
		return chunkList;
	}

	private List<Element> split(String str) {
		List<Element> list = new ArrayList<Element>();
		int i = 0;
		for (char c : str.toCharArray()) {
			list.add(new Element(c, i));
			i++;
		}
		return list;
	}

	public static Diff createDiff(String from, String to) {
		DiffType deleteType = DiffType.DELETED;
		DiffType insertType = DiffType.INSERTED;
		Diff differ = new Diff();
		if (from == null || to == null) {
			DiffType type = null;
			String result;
			result = to;
			if ("".equals(from) || from == null) {
				type = DiffType.INSERTED;
			} else if ("".equals(to) || to == null) {
				type = DiffType.DELETED;
			}
			differ.chunkList.add(new Chunk(result, type));
			return differ;
		}
		Collection<Element> control = differ.split(from);
		Collection<Element> revised = differ.split(to);
		Element prevRevised = null;
		for (Iterator<Element> controlIterator = control.iterator(); controlIterator
				.hasNext();) {
			Element controlElement = controlIterator.next();
			boolean isFound = false;
			for (Iterator<Element> revisedIterator = revised.iterator(); revisedIterator
					.hasNext();) {
				Element revElement = revisedIterator.next();
				if (revElement.getElement() == controlElement.getElement()) {
					controlElement.setType(DiffType.EQUALS);
					revisedIterator.remove();
					controlIterator.remove();
					if (prevRevised != null && prevRevised.getType() == null
							&& revised.size() > 1) {
						for (revisedIterator = revised.iterator(); revisedIterator
								.hasNext();) {
							Element el = revisedIterator.next();
							if (el.getIndex() <= revElement.getIndex()) {
								el.setType(insertType);
								differ.addElement(el);
								revisedIterator.remove();
							}
						}
					}
					controlElement.setType(DiffType.EQUALS);
					differ.addElement(controlElement);
					prevRevised = controlElement;
					isFound = true;
					break;
				}
				prevRevised = revElement;
			}
			if (!isFound) {
				controlElement.setType(deleteType);
				differ.addElement(controlElement);
				controlIterator.remove();
			}
		}

		for (Element el : revised) {
			el.setType(insertType);
			differ.addElement(el);
		}
		differ.flush();
		System.out.println("Diff size:" + differ.getDiffSize());
		return differ;
	}
}