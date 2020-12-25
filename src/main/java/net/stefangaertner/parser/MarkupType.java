package net.stefangaertner.parser;

public enum MarkupType {

	HEADING('#'),
	LIST_ITEM_ORDERED('1'),
	LIST_ITEM_UNORDERED('-'),
	SHEET('~'),
	QUOTE('>'),
	CODEBLOCK('$'),
	TITLE('!'),
	DONT_ESCAPE('\''),
	ROW('_'),
	COLUMN('|'),
	NONE;

	private char c;
	
	/*
	private static String chars;
	static {
		chars = "";
		for (Markup m : Markup.values()) {
			chars += m.c;
		}
	}
	
	public boolean isMarkup(char c) {
		return chars.indexOf(c) >= 0;
	}
	*/
	
	MarkupType() {}

	MarkupType(char c2) {
		this.c = c2;
	}

	public static MarkupType fromChar(char c2) {
		for (MarkupType m : MarkupType.values()) {
			if (m.c == c2) {
				return m;
			}
		}
		return MarkupType.NONE;
	}

}
