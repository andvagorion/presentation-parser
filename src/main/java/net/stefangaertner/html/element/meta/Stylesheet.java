package net.stefangaertner.html.element.meta;

import net.stefangaertner.html.element.AbstractElement;
import net.stefangaertner.html.element.HTMLAttribute;

public class Stylesheet extends AbstractElement {

	public Stylesheet(String name) {
		addAttribute(new HTMLAttribute("rel", "stylesheet"));
		addAttribute(new HTMLAttribute("type", "text/css"));
		addAttribute(new HTMLAttribute("href", name));
	}
	
	@Override
	public boolean needsToBeClosed() {
		return false;
	}

	@Override
	public String getTagName() {
		return "link";
	}

}
