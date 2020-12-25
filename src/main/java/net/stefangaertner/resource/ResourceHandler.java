package net.stefangaertner.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

public class ResourceHandler {

	public static void copyResources(Path destinationFolder) throws IOException {

		Path out = destinationFolder.resolve("res");
		System.out.println(String.format("Creating directory: %s", out));
		Files.createDirectories(out);

		String[] resources = new String[] { "css/atom-one-light.css", "css/styles.css", "img/spinner.gif",
				"js/highlight.pack.js", "js/presentation.js" };

		System.out.println("Copying resources.");
		for (String resource : resources) {
			copy(out, resource);
		}

	}

	private static void copy(Path out, String resource) throws IOException {

		Path outPath = out.resolve(resource);
		String resourcePath = "/assets/" + resource;
		
		System.out.println(String.format("Copying %s -> %s", resourcePath, outPath));

		try (InputStream stream = ResourceHandler.class.getResourceAsStream(resourcePath)) {
			Files.createDirectories(outPath.getParent());
			Files.copy(stream, outPath);
		} catch (FileAlreadyExistsException e) {
			System.out.println(String.format("%s already exists. Aborting operation.", outPath));
		}
	}

}
