package com.parser;

/**
 * The class that holds generic helper methods.
 */
public class Utils {

    /**
     * Parses a path to get the file's extension.
     *
     * @param path The path to the file.
     * @return The extension type of the file
     * @throws IllegalArgumentException if path is invalid
     */
    public String getFileExtension(String path) throws IllegalArgumentException {
        int dotIndex = path.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex >= path.length() - 1) {
            throw new IllegalArgumentException("");
        }
        return path.substring(dotIndex + 1);
    }
}
