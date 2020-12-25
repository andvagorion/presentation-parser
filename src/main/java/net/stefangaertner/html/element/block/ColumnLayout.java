package net.stefangaertner.html.element.block;

import net.stefangaertner.html.element.AbstractElement;

public class ColumnLayout extends AbstractElement {

	public ColumnLayout() {
		this.addAttribute("class", "column-layout");
	}
	
	@Override
	public String getTagName() {
		return "div";
	}

}
