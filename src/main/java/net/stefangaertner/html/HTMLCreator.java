package net.stefangaertner.html;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import net.stefangaertner.html.element.HTMLElement;
import net.stefangaertner.html.element.Token;
import net.stefangaertner.html.element.block.Div;
import net.stefangaertner.html.element.block.DropDown;
import net.stefangaertner.html.element.meta.Body;
import net.stefangaertner.html.element.meta.DocumentType;
import net.stefangaertner.html.element.meta.Footer;
import net.stefangaertner.html.element.meta.Head;
import net.stefangaertner.html.element.meta.Html;
import net.stefangaertner.html.element.meta.Meta;
import net.stefangaertner.html.element.meta.Root;
import net.stefangaertner.html.element.meta.Script;
import net.stefangaertner.html.element.meta.Stylesheet;
import net.stefangaertner.parser.NavigationItem;

public class HTMLCreator {

	public static final String LINE_SEPARATOR = "\r\n";

	public static void createSheets(Path path, HTMLElement root, List<NavigationItem> headings) throws IOException {

		HTMLElement body = createHead();

		body.addChildren(root.getChildren());

		body = addFooter(body, headings);

		try (HTMLWriter writer = HTMLWriter.getInstance(path)) {

			writeTree(writer, body.getRoot());

		}

	}

	private static HTMLElement createHead() {

		HTMLElement node = new Root();
		node.addChild(new DocumentType());
		node = node.addChild(new Html());
		node = node.addChild(new Head());

		node.addChild(new Meta("charset", "utf8"));
		node.addChild(new Meta("http-equiv", "X-UA-Compatible", "content", "chrome=1"));
		node.addChild(new Meta("name", "viewport", "content", "width=device-width, initial-scale=1, user-scalable=no"));

		node.addChild(new Stylesheet("res/css/atom-one-light.css"));
		node.addChild(new Stylesheet("res/css/styles.css"));

		node = node.getParent();
		return node.addChild(new Body());

	}

	private static HTMLElement addFooter(HTMLElement body, List<NavigationItem> headings) {

		body.addChild(new Footer()).addChild(new DropDown(headings));

		body.addChild(new Script("res/js/presentation.js"));

		body.addChild(new Script("res/js/highlight.pack.js"));
		Script script = new Script();
		script.addChild(new Token("hljs.initHighlightingOnLoad();"));
		body.addChild(script);

		Div div = new Div();
		div.addAttribute("id", "waitBox");
		div.addAttribute("class", "full-screen overlay trans-opacity");
		Div spinner = new Div();
		spinner.addAttribute("class", "spinner");
		body.addChild(div).addChild(spinner);

		return body;
	}

	private static void writeTree(HTMLWriter writer, HTMLElement node) throws IOException {
		for (HTMLElement element : node.getChildren()) {
			writeElement(writer, element, 0);
		}
	}

	private static void writeElement(HTMLWriter writer, HTMLElement element, int indentation) throws IOException {

		if (element instanceof Token) {
			writer.write(((Token) element).getText());
		} else {

			writer.startElement(element);

			if (element.hasChildren()) {
				for (HTMLElement child : element.getChildren()) {
					writeElement(writer, child, indentation + 1);
				}
			}

			if (element.needsToBeClosed()) {

				writer.endElement(element);

			}

		}

	}

}