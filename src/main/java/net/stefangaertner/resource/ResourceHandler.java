package net.stefangaertner.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

public class ResourceHandler {

	public static void unzip(Path destinationFolder) throws IOException {

		Path zip = Paths.get(".").toAbsolutePath().normalize().resolve("res.zip");
		
		destinationFolder = destinationFolder.resolve("res");
		System.out.println(String.format("Creating directory: %s", destinationFolder));
		Files.createDirectories(destinationFolder);

		try (ZipFile zipFile = new ZipFile(zip.toFile(), ZipFile.OPEN_READ, StandardCharsets.UTF_8)) {

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {

				ZipEntry entry = entries.nextElement();
				Path entryPath = destinationFolder.resolve(entry.getName());

				if (entry.isDirectory()) {
					Files.createDirectories(entryPath);
				} else {
					Files.createDirectories(entryPath.getParent());
					try (InputStream in = zipFile.getInputStream(entry)) {
						try (OutputStream out = new FileOutputStream(entryPath.toFile())) {
							IOUtils.copy(in, out);
						}
					}
				}
			}
		}
	}

}
