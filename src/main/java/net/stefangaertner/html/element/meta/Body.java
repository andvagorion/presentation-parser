package net.stefangaertner.html.element.meta;

import net.stefangaertner.html.element.AbstractElement;
import net.stefangaertner.html.element.HTMLAttribute;

public class Body extends AbstractElement {

	public Body() {
		this.addAttribute(new HTMLAttribute("class", "gradient-light"));
	}
	
	@Override
	public String getTagName() {
		return "body";
	}

}
