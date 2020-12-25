package net.stefangaertner.html.element.meta;

import net.stefangaertner.html.element.AbstractElement;
import net.stefangaertner.html.element.HTMLAttribute;
import net.stefangaertner.html.element.block.Div;

public class Footer extends AbstractElement {

	public Footer() {
		this.addAttribute(new HTMLAttribute("class", "fixed-bottom blue-border-top"));
		
		Div progress = new Div();
		progress.addAttribute(new HTMLAttribute("id", "progressBar"));
		progress.addAttribute(new HTMLAttribute("class", "gradient-light"));
		this.addChild(progress);
		
	}
	
	@Override
	public String getTagName() {
		return "div";
	}

}
