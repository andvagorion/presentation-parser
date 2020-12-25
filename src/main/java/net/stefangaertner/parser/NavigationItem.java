package net.stefangaertner.parser;

public class NavigationItem {

	private int sheet;
	private String title;

	public NavigationItem(int sheet, String title) {
		this.sheet = sheet;
		this.title = title;
	}

	public int getSheet() {
		return sheet;
	}

	public String getTitle() {
		return title;
	}

}
