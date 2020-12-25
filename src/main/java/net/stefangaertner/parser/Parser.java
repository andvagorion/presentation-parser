package net.stefangaertner.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.stefangaertner.html.element.HTMLAttribute;
import net.stefangaertner.html.element.HTMLElement;
import net.stefangaertner.html.element.Token;
import net.stefangaertner.html.element.block.Blockquote;
import net.stefangaertner.html.element.block.ColumnLayout;
import net.stefangaertner.html.element.block.Div;
import net.stefangaertner.html.element.block.Heading1;
import net.stefangaertner.html.element.block.Heading2;
import net.stefangaertner.html.element.block.Heading3;
import net.stefangaertner.html.element.block.ListItem;
import net.stefangaertner.html.element.block.ListOrdered;
import net.stefangaertner.html.element.block.ListUnordered;
import net.stefangaertner.html.element.block.Paragraph;
import net.stefangaertner.html.element.inline.Code;
import net.stefangaertner.html.element.inline.Pre;
import net.stefangaertner.html.element.inline.Span;
import net.stefangaertner.html.element.meta.Root;

public class Parser {

	private Path path;

	private int lineNumber = 1;
	private int currentSheetIndex = 0;
	private List<NavigationItem> headings;

	public List<NavigationItem> getHeadings() {
		return headings;
	}

	public Parser(Path path) {
		this.path = path;
		this.headings = new ArrayList<>();
	}

	public HTMLElement parseFileToTree() {

		// I am Rooooot
		HTMLElement currentNode = new Root();

		// create first sheet and wrapper, this is sheet #0
		currentNode = currentNode.addChild(Div.newSheet()).addChild(new Div()).addAttribute("class", "wrapper");

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(path.toFile()), "UTF8"))) {

			for (String line; (line = br.readLine()) != null;) {
				currentNode = parseLine(currentNode, line);
				lineNumber++;
			}

			br.close();

		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + path);
		} catch (IOException e) {
			System.err.println("An I/O error has occured.");
		}

		return currentNode.getRoot();
	}

	private HTMLElement parseLine(HTMLElement currentNode, String line) {

		try {

			currentNode = parseMarkup(currentNode, line);

		} catch (Exception e) {
			System.err.println("Error in line " + lineNumber + ":");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return currentNode;

	}

	private HTMLElement parseMarkup(HTMLElement currentNode, String line) throws Exception {

		if (line.isEmpty() && line.trim().isEmpty()) {
			return currentNode;
		}

		// Check start of line for markup, strip markup characters from
		// beginning of line
		char markupCharacter = line.charAt(0);
		MarkupType markupType = MarkupType.fromChar(markupCharacter);
		int markupLayer = determineLayer(line, markupCharacter);
		if (!markupType.equals(MarkupType.NONE)) {
			line = line.substring(markupLayer);
		}

		switch (markupType) {

		case HEADING:

			currentNode = currentNode.getParent(Div.class);

			HTMLElement heading = determineHeading(markupLayer);
			parseInlineMarkup(heading, line);
			currentNode.addChild(heading);
			if (heading instanceof Heading1 && !line.contains("(cont)")) {
				this.headings.add(new NavigationItem(currentSheetIndex, line.trim()));
			}

			return currentNode;

		case LIST_ITEM_ORDERED:

			currentNode = getCorrectLocation(currentNode, ListOrdered.class, markupLayer);

			HTMLElement item = new ListItem();
			parseInlineMarkup(item, line);
			currentNode.addChild(item);

			return currentNode;

		case LIST_ITEM_UNORDERED:

			currentNode = getCorrectLocation(currentNode, ListUnordered.class, markupLayer);

			HTMLElement item1 = new ListItem();
			parseInlineMarkup(item1, line);
			currentNode.addChild(item1);

			return currentNode;

		case SHEET:

			currentSheetIndex++;
			return currentNode.getRoot().addChild(Div.newSheet()).addChild(new Div()).addAttribute("class", "wrapper");

		case QUOTE:

			currentNode = getCorrectLocation(currentNode, Blockquote.class, 1);

			parseInlineMarkup(currentNode, line);

			return currentNode;

		case CODEBLOCK:

			if (markupLayer > 1) {

				currentNode = currentNode.getParent(Div.class).addChild(new Pre()).addChild(new Code());
				((Code) currentNode).setType(line);

			} else {

				currentNode = getCorrectCodeBlockLocation(currentNode);
				currentNode.addChild(new Token(line + "\r\n"));

			}

			return currentNode;

		case TITLE:

			currentNode = currentNode.getParent(Div.class).addChild(new Div()).addAttribute("class", "center-outer")
					.addChild(new Span()).addAttribute("class", "center-inner title text-gradient");
			return parseInlineMarkup(currentNode, line);

		case DONT_ESCAPE:

			currentNode = currentNode.getParent(Div.class);
			currentNode.addChild(new Token(line, false));

			return currentNode;

		case COLUMN:

			// end
			if (line.contains("end")) {
				return currentNode.getParent(ColumnLayout.class).getParent();
			}

			if (currentNode.getParent(ColumnLayout.class) == null) {
				// start
				currentNode = currentNode.getParent(Div.class).addChild(new ColumnLayout());
			} else {
				// new column
				currentNode = currentNode.getParent(ColumnLayout.class);
			}

			Div column = new Div();
			column.addAttribute("class", "column");
			parseInlineMarkup(column, line);

			return currentNode.addChild(column);

		case NONE:
		default:
			currentNode = currentNode.getParent(Div.class);
			Paragraph paragraph = new Paragraph();
			parseInlineMarkup(paragraph, line);
			currentNode.addChild(paragraph);

			return currentNode;
		}

	}

	private static HTMLElement getCorrectCodeBlockLocation(HTMLElement currentNode) {
		if (!currentNode.checkParents(new Class<?>[] { Code.class, Pre.class })) {
			return currentNode.addChild(new Pre()).addChild(new Code());
		}
		return currentNode;
	}

	private static HTMLElement parseInlineMarkup(HTMLElement currentNode, String innerHtml) {
		currentNode.addChild(new Token(innerHtml));
		return currentNode;
	}

	private static HTMLElement getCorrectLocation(HTMLElement currentNode, Class<? extends HTMLElement> elementType,
			int depth) throws Exception {

		if (!elementType.isInstance(currentNode)) {
			// Wrong list type => create new list
			return currentNode.getParent(Div.class).addChild(elementType.getDeclaredConstructor().newInstance());
		}

		int currentDepth = currentNode.determineDepth(elementType);

		while (currentDepth > depth) {
			// Too deep => traverse up
			currentNode = currentNode.getParent();
			currentDepth--;
		}

		while (currentDepth < depth) {
			// Too shallow => create new nodes
			currentNode = currentNode.addChild(elementType.getDeclaredConstructor().newInstance());
			currentDepth++;
		}

		return currentNode;
	}

	private static HTMLElement determineHeading(int layer) {
		switch (layer) {
		case 1:
			Heading1 h1 = new Heading1();
			h1.addAttribute(new HTMLAttribute("class", "text-gradient"));
			return h1;
		case 2:
			return new Heading2();
		default:
			return new Heading3();
		}
	}

	@SuppressWarnings("unused")
	private static String readMarkup(String line, char c) {
		char[] charArr = line.toCharArray();
		int index = 0;
		while (charArr[index] == c) {
			index++;
		}
		return (new String(new char[index + 1]).replace('\0', c));
	}

	private static int determineLayer(String str, char lookup) {
		int i = 0;
		for (char c : str.toCharArray()) {
			if (c == lookup) {
				i++;
			} else {
				return i;
			}
		}
		return i;
	}

	/*
	 * private static boolean hasMarkup(String line) { char c = line.charAt(0);
	 * return Markup.isMarkup(c); }
	 */

}