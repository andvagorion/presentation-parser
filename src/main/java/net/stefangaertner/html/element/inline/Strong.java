package net.stefangaertner.html.element.inline;

import net.stefangaertner.html.element.Token;

public class Strong extends AbstractInlineElement {

	public Strong(String text) {
		this.addChild(new Token(text));
	}

	@Override
	public String getTagName() {
		return "strong";
	}

}
