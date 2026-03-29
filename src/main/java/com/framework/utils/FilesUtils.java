package com.framework.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class FilesUtils {

	public static String readFileAsAString(String filePath) {
		try {
			return Files.readString(Paths.get(filePath));
		} catch (IOException e) {
			throw new RuntimeException("Could not read a file at " + filePath);
		}
	}

	public static boolean isFileExist(String filePath) {
		try {
			return Files.exists(Paths.get(filePath)) && Files.size(Paths.get(filePath)) > 0;
		} catch (IOException e) {
			return false;
		}
	}

	public static void deleteFiles(String filePath) {

		if (isFileExist(filePath)) {
			try (Stream<Path> stream = Files.walk(Paths.get(filePath))) {
				stream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
