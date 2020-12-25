package net.stefangaertner.util;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

public class FileUtils {

	private static final String DEFAULT_HTML = "presentation.html";

	public static Path evaluatePath(Path path, String extension) {
		extension = extension.startsWith(".") ? extension : "." + extension;

		if (path.toString().endsWith(extension)) {
			return path;
		} else {
			String msg = String.format("Path does not match extension %s. Appending filename %s.", extension, DEFAULT_HTML);
			System.out.println(msg);
			return path.resolve(DEFAULT_HTML);
		}
	}

	public static Path toAbsolutePath(String pathStr) {
		String in = FileSystems.getDefault().getPath(pathStr).normalize().toAbsolutePath().toString();
		String normalized = FilenameUtils.normalize(in);
		return Paths.get(normalized);
	}

}
