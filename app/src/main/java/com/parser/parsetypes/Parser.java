package com.parser.parsetypes;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.gson.JsonObject;

/**
 * The generic type for parsing any type of format.
 * Further Implementation: Create more implementations of this Parser class. Add ability to parse other file
 * types such as .xls, .word, .txt, csv, etc.
 */
public interface Parser {

    public JsonObject parse(FileInputStream fis) throws IOException;
}
