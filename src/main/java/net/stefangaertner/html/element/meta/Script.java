package net.stefangaertner.html.element.meta;

import net.stefangaertner.html.element.AbstractElement;
import net.stefangaertner.html.element.HTMLAttribute;

public class Script extends AbstractElement {

	public Script(String name) {
		addAttribute(new HTMLAttribute("type", "text/javascript"));
		addAttribute(new HTMLAttribute("src", name));
	}

	public Script() {}

	@Override
	public String getTagName() {
		return "script";
	}

}
