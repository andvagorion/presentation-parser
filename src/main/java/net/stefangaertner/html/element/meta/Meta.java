package net.stefangaertner.html.element.meta;

import net.stefangaertner.html.element.AbstractElement;
import net.stefangaertner.html.element.HTMLAttribute;

public class Meta extends AbstractElement {

	public Meta(String... attributes) throws IllegalArgumentException {
		if (attributes.length % 2 != 0) {
			throw new IllegalArgumentException("Attributes must be supplied in pairs of two.");
		}

		for (int i = 0; i < attributes.length; i += 2) {
			this.addAttribute(new HTMLAttribute(attributes[i], attributes[i + 1]));
		}
	}
	
	@Override
	public boolean needsToBeClosed() {
		return false;
	}

	@Override
	public String getTagName() {
		return "meta";
	}

}
