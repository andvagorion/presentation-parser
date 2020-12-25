package net.stefangaertner.html.element;

public class Token extends AbstractElement {

	private String text;
	private boolean escape = true;
	
	public Token(String text) {
		this.text = text;
	}

	public Token(String text, boolean escape) {
		this.text = text;
		this.escape = escape;
	}
	
	public String getText() {
		return escape ? escapeHTML(text) : text;
	}
	
	private static String escapeHTML(String s) {
		StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
				out.append("&#");
				out.append((int) c);
				out.append(';');
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	@Override
	public String getTagName() {
		return null;
	}

}
