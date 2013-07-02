package org.juke.string.diff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s1;
		String s2;

		s1 = "1212311745725512311745725567452412512514446745241251251444123117457255674524125125144431174572556745241251251444";
		s2 = "625648625648679o78956845125174576625648679o7895684512517457625648679o789568451251745779o7895684512517457";
		test2(s1, s2);
	}

	private static void test2(String s1, String s2) {
		long start = System.nanoTime();
		Diff differ = Diff.createDiff(s1, s2);
		System.out.println();
		// System.out.println();
		float tmp = (System.nanoTime() - start) / 1000000f;
		System.out.println("SK: " + tmp + " ms.");
		StringBuilder sb = new StringBuilder();
		//
		Collection<Chunk> ch = differ.getChunks();
		for (Chunk c : ch) {
			if (c.getType() == DiffType.DELETED) {
				System.out
						.print("<=" + c.getIndex() + "-" + c.toString() + ">");
				//
			} else if (c.getType() == DiffType.INSERTED) {
				System.out
						.print("{=" + c.getIndex() + "-" + c.toString() + "}");
				sb.append(c);
			} else {
				sb.append(c);
				System.out.print(c.toString());
			}
		}
		System.out.println();
		start = System.nanoTime();
		System.out.println(StringUtils.getLevenshteinDistance(s1, s2));
		tmp = (System.nanoTime() - start) / 1000000f;
		System.out.println("Levenstain: " + tmp + " ms.");
	}

	private static void test(String s1, String s2) {
		float lev = 0;
		float sk = 0;
		String ss = s1;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			getDiff1(s1, s2);
			// System.out.println();
			float tmp = (System.nanoTime() - start) / 1000000f;
			System.out.println("SK: " + tmp + " ms.");
			sk += tmp;
			start = System.nanoTime();
			System.out.println(StringUtils.getLevenshteinDistance(s1, s2));
			tmp = (System.nanoTime() - start) / 1000000f;
			lev += tmp;
			System.out.println("Levenstain: " + tmp + " ms.");
			s1 = s1 + s2;
			s2 = s2 + s1;
			// if (s1.length() > 10000) {
			// s1 = ss+ss;
			// }
		}
		System.out.println("SK total: " + sk + " ms.");
		System.out.println("Levenstain total: " + lev + " ms.");

	}

	private static Collection<Element> toList(String s) {
		Collection<Element> list = new ArrayList<Element>();
		int i = 0;
		for (char c : s.toCharArray()) {
			list.add(new Element(c, i));
			i++;
		}
		return list;
	}

//	public static Diff getDiff(String from, String to) {
//		System.out
//				.println("---------------------------------------------------------");
//
//		String controlString = from;
//		String revisedString = to;
//		String deleted = "+";
//		String insertedType = "-";
//		DiffType deleteType = DiffType.DELETED;
//		DiffType insertType = DiffType.INSERTED;
//		// if (controlString.length() < revisedString.length()) {
//		//
//		// inserted = deleted;
//		// deleted = "+";
//		// insertType = DiffType.DELETED;
//		// deletedType = DiffType.INSERTED;
//		// }
//		System.out.println(from);
//		System.out.println(to);
//
//		System.out
//				.println("---------------------------------------------------------");
//
//		Collection<Element> control = toList(controlString);
//		Collection<Element> revised = toList(revisedString);
//
//		Iterator<Element> controlIterator = control.iterator();
//		Element prevRevised = null;
//		Diff differ = new Diff();
//		while (controlIterator.hasNext()) {
//			Element controlElement = controlIterator.next();
//			Iterator<Element> revisedIterator = revised.iterator();
//			boolean isFound = false;
//			while (revisedIterator.hasNext()) {
//				Element revElement = revisedIterator.next();
//				int correction = 0;
//				if (revElement.getElement() == controlElement.getElement()) {
//					controlElement.setType(DiffType.EQUALS);
//					revisedIterator.remove();
//					controlIterator.remove();
//					if (prevRevised != null && prevRevised.getType() == null
//							&& revised.size() > 1) {
//						revisedIterator = revised.iterator();
//						while (revisedIterator.hasNext()) {
//							Element el = revisedIterator.next();
//							if (el.getIndex() <= revElement.getIndex()) {
//								el.setType(insertType);
////								el.setIndex(controlElement.getIndex());
//								System.out.print(el);
//								differ.addElement(el);
//								revisedIterator.remove();
//							}
//						}
//					}
////					controlElement.setIndex(revElement.getIndex()
////							+ correction);
//					controlElement.setType(DiffType.EQUALS);
//					differ.addElement(controlElement);
//					System.out.print(controlElement);
//					prevRevised = controlElement;
//					isFound = true;
//					break;
//				}
//				prevRevised = revElement;
//
//			}
//			if (!isFound) {
//				controlElement.setType(deleteType);
//				// System.out.print( controlElement );
//				// System.out.print( controlElement );
//				System.out.print(controlElement);
//				differ.addElement(controlElement);
//				controlIterator.remove();
//
//			}
//
//		}
//
//		for (Element el : revised) {
//			// System.out.print(deleted + el + deleted);
//			el.setType(insertType);
//			differ.addElement(el);
//		}
//		System.out.println();
//		differ.flush();
//
//		StringBuilder sb = new StringBuilder();
//		//
//		Collection<Chunk> ch = differ.getChunks();
//		for (Chunk c : ch) {
//			if (c.getType() == deleteType) {
//				System.out
//						.print("<=" + c.getIndex() + "-" + c.toString() + ">");
//				//
//			} else if (c.getType() == insertType) {
//				System.out
//						.print("{=" + c.getIndex() + "-" + c.toString() + "}");
//				sb.append(c);
//			} else {
//				sb.append(c);
//				System.out.print(c.toString());
//			}
//		}
//		//
//		System.out.println();
//		System.out.println("----------------------------------------");
//		System.out.println(sb);
//		if (!sb.toString().equals(to)) {
//			System.err.println("Alarm!!!!!!!!");
//		}
//		// System.out.println("----------------------------------------");
//		System.out.println(differ.getDiffSize());
//		return differ;
//	}

	public static Diff getDiff1(String from, String to) {
		System.out
				.println("---------------------------------------------------------");

		String controlString = from;
		String revisedString = to;
		// String deleted = "+";
		// String inserted = "-";
		// DiffType insertType = DiffType.INSERTED;
		// DiffType deletedType = DiffType.DELETED;
		// if (controlString.length() < revisedString.length()) {

		// inserted = deleted;
		// deleted = "+";
		// inserÑtType = DiffType.DELETED;
		// deletedType = DiffType.INSERTED;
		// }
		// System.out.println(from);
		// System.out.println(to);

		// System.out
		// .println("---------------------------------------------------------");

		Collection<Element> control = toList(controlString);
		Collection<Element> revised = toList(revisedString);

		// Iterator<Element> controlIterator = control.iterator();
		Element prevRevised = null;
		// Diff differ = new Diff();
		int count = 0;
		for (Iterator<Element> controlIterator = control.iterator(); controlIterator
				.hasNext();) {
			Element controlElement = controlIterator.next();
			// Iterator<Element> revisedIterator = revised.iterator();
			boolean isFound = false;
			for (Iterator<Element> revisedIterator = revised.iterator(); revisedIterator
					.hasNext();) {
				Element revElement = revisedIterator.next();
				// int correction = 0;
				if (revElement.getElement() == controlElement.getElement()) {
					// controlElement.setType(DiffType.EQUALS);
					revisedIterator.remove();
					// controlIterator.remove();
					if (prevRevised != null && prevRevised.getType() == null
							&& revised.size() > 1) {
						for (revisedIterator = revised.iterator(); revisedIterator
								.hasNext();) {
							Element el = revisedIterator.next();
							if (el.getIndex() <= revElement.getIndex()) {
								// el.setType(deletedType);
								// System.out.print(el);
								// differ.addElement(el);
								revisedIterator.remove();
							}
						}
					}
					// controlElement.setIndex(controlElement.getIndex()
					// + correction);
					// controlElement.setType(DiffType.EQUALS);
					// differ.addElement(controlElement);
					// System.out.print(controlElement);
					prevRevised = controlElement;
					isFound = true;
					break;
				}
				prevRevised = revElement;

			}
			if (!isFound) {
				// controlElement.setType(insertType);
				// System.out.print( controlElement );
				// System.out.print( controlElement );
				// differ.addElement(controlElement);
				count++;
				controlIterator.remove();

			}

		}

		// for (Element el : revised) {
		// System.out.print(deleted + el + deleted);
		// el.setType(deletedType);
		// differ.addElement(el);
		// }
		// System.out.println();
		// differ.flush();

		// StringBuilder sb = new StringBuilder();
		//
		// Collection<Chunk> ch = differ.getChunks();
		// for (Chunk c : ch) {
		// if (c.getType() == insertType) {
		// System.out.print("-" + c.toString() + "-");
		//
		// } else if (c.getType() == deletedType) {
		// System.out.print("+" + c.toString() + "+");
		// sb.append(c);
		// } else {
		// sb.append(c);
		// System.out.print(c.toString());
		// }
		// }
		//
		// System.out.println();
		// System.out.println("----------------------------------------");
		// System.out.println(sb);
		// if (!sb.toString().equals(to)) {
		// System.err.println("Alarm!!!!!!!!");
		// }
		// System.out.println("----------------------------------------");
		System.out.println(count);
		return null;
	}

}