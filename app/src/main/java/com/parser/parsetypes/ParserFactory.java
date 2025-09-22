package com.parser.parsetypes;

import com.parser.exceptions.UnsupportedFormatException;

/**
 * The Factory for creating the necessary parser for the type of file
 * Further Implementation: Create more types of parsers to allow for parsing more file types.
 */
public class ParserFactory {
    
    /**
     * Creates the correct parser based on the extension parameter.
     *
     * @param extension The extention type of the file that will be parsed.
     * @return An implementation of Parser.
     * @throws UnsupportedFormatException if the extenstion's format is not supported.
     */
    public static Parser createParser(String extension) throws UnsupportedFormatException {
        switch(extension.toLowerCase()) {
            case "pptx":
                return new PowerPointParser();
            default:
                throw new UnsupportedFormatException("File type " + extension + " is currently unsupported.");
        }
    }
}