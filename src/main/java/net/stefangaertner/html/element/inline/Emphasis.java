package net.stefangaertner.html.element.inline;

import net.stefangaertner.html.element.Token;

public class Emphasis extends AbstractInlineElement {

	public Emphasis(String text) {
		this.addChild(new Token(text));
	}

	@Override
	public String getTagName() {
		return "em";
	}

}
