package net.stefangaertner.html.element.inline;

public class Code extends AbstractInlineElement {

	private boolean typeSet = false;
	
	@Override
	public String getTagName() {
		return "code";
	}
	
	public void setType(String type) {
		if (!typeSet) {
			this.addAttribute("class", type);
			typeSet = true;
		}
	}

}
