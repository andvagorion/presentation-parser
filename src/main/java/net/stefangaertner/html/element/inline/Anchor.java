package net.stefangaertner.html.element.inline;

import net.stefangaertner.html.element.Token;

public class Anchor extends AbstractInlineElement {

	public Anchor(String href, String title, boolean newTab) {
		this.addAttribute("href", href);
		if (newTab) {
			this.addAttribute("target", "_blank");
		}
		this.addChild(new Token(title));
	}

	public Anchor(String href, String title) {
		this(href, title, true);
	}
	
	@Override
	public String getTagName() {
		return "a";
	}

}
