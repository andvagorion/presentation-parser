package net.stefangaertner.html.element.meta;

import net.stefangaertner.html.element.AbstractElement;

public class DocumentType extends AbstractElement {

	@Override
	public String getTagName() {
		return "!doctype html";
	}
	
	@Override
	public boolean needsToBeClosed() {
		return false;
	}

}
