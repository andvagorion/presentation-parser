package net.stefangaertner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.stefangaertner.html.HTMLCreator;
import net.stefangaertner.html.element.HTMLElement;
import net.stefangaertner.parser.Parser;
import net.stefangaertner.resource.ResourceHandler;
import net.stefangaertner.util.FileUtils;

public class Main {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Proper Usage is: pres-parser [path/to/file.txt] [path/to/presentation.html]");
			System.exit(0);
		}

		Path in = FileUtils.toAbsolutePath(args[0]);
		Path out = FileUtils.toAbsolutePath(args[1]);

		if (!Files.exists(in)) {
			System.err.println(String.format("Input path %s does not exist.", in));
			System.exit(0);
		}

		try {

			Parser parser = new Parser(in);

			HTMLElement root = parser.parseFileToTree();

			Path outFile = FileUtils.evaluatePath(out, "html");
			HTMLCreator.createSheets(outFile, root, parser.getHeadings());

			Path outDir = outFile.getParent();
			ResourceHandler.copyResources(outDir);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

}
