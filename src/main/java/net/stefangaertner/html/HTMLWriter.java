package net.stefangaertner.html;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.stefangaertner.html.element.HTMLAttribute;
import net.stefangaertner.html.element.HTMLElement;

public class HTMLWriter extends BufferedWriter {

	private static final Logger logger = Logger.getLogger(HTMLWriter.class.getName());

	private static final String LINEBREAK = "\r\n";
	private static final String ELEMENT_START = "<";
	private static final String ELEMENT_END = ">";
	private static final String ESCAPE_QUOTE = "\"";
	private static final String CLOSE_TAG = "/";

	private int indentation = 0;

	public HTMLWriter(Writer writer) {
		super(writer);
	}

	public static HTMLWriter getInstance(Path path) throws IOException {

		try {
			
			System.out.println(String.format("Creating directories: %s", path.getParent()));
			Files.createDirectories(path.getParent());

			Charset cs = Charset.forName("UTF8");
			FileOutputStream is = new FileOutputStream(path.toFile());
			OutputStreamWriter osw = new OutputStreamWriter(is, cs);
			Writer writer = new BufferedWriter(osw);
			return new HTMLWriter(writer);

		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE,
					"Could not initialize HTMLWriter. File [" + path + "] could not be opened: " + e.getMessage());
			return null;
		}
	}

	public void startElement(HTMLElement element) throws IOException {

		write(ELEMENT_START + element.getTagName());

		if (element.hasAttributes()) {
			writeAttributes(element.getAttributes());
		}

		write(ELEMENT_END);

		if (!element.isInline()) {
			write(LINEBREAK);
		}
	}

	private void writeAttributes(List<HTMLAttribute> attributes) throws IOException {

		for (HTMLAttribute attribute : attributes) {
			write(" " + attribute.getKey() + "=" + quote(attribute.getValue()));
		}
	}

	public void endElement(HTMLElement element) throws IOException {

		write(ELEMENT_START + CLOSE_TAG + element.getTagName() + ELEMENT_END);

		write(LINEBREAK);

	}

	public String getIndentation() throws IOException {
		return (new String(new char[indentation]).replace("\0", "    "));
	}

	private String quote(String str) {
		return ESCAPE_QUOTE + str + ESCAPE_QUOTE;
	}

	public void setIndentation(int indentation) {
		this.indentation = indentation;
	}

}
