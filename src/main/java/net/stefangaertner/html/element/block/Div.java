package net.stefangaertner.html.element.block;

import net.stefangaertner.html.element.AbstractElement;

public class Div extends AbstractElement {

	@Override
	public String getTagName() {
		return "div";
	}

	public static Div newSheet() {
		Div sheet = new Div();
		sheet.addAttribute("class", "sheet shadow-inset");
		sheet.addAttribute("style", "display: none;");
		return sheet;
	}

}
