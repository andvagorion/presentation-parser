package net.stefangaertner.html.element.block;

import java.util.List;

import net.stefangaertner.html.element.AbstractElement;
import net.stefangaertner.html.element.HTMLElement;
import net.stefangaertner.html.element.Token;
import net.stefangaertner.parser.NavigationItem;

public class DropDown extends AbstractElement implements HTMLElement {

	public DropDown(List<NavigationItem> headings) {

		this.addAttribute("class", "drop-down");

		this.addChild(new Div()).addAttribute("class", "drop-down-button").addAttribute("id", "pager")
				.addChild(new Token("Slides"));

		Div menu = (Div) this.addChild(new Div()).addAttribute("class",
				"drop-down-list trans-opacity hidden blue-border-top");

		for (NavigationItem heading : headings) {
			menu.addChild(new Div()).addAttribute("class", "drop-down-item").addChild(new Div())
					.addAttribute("onclick", "Prez.jumpToSlide(" + heading.getSheet() + ")")
					.addChild(new Token(heading.getTitle()));
		}
	}

	@Override
	public String getTagName() {
		return "div";
	}

}
