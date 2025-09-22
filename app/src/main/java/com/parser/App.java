package com.parser;

import java.io.FileInputStream;
import java.io.IOException;

import com.parser.parsetypes.*;

import com.google.gson.JsonObject;

/**
 * The main file.
 * 
 * Java - SDK 21.
 * Gradle - 9.1.0
 * To Run - `gradle run --args="src/main/resources/example.pptx"` at project root.
 * If you want to test another .pptx file simply replace the location of the above example.pptx to the location
 * of the desired .pptx file. JSON files are always dumped into `src/main/resources/exported.json`.
 */
public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please include only a single command line argument in the form of the path to the file.");
            return;
        }

        Utils utils = new Utils();
        
        try (FileInputStream fis = new FileInputStream(args[0])){
            String ext = utils.getFileExtension(args[0]);
            JsonObject content = ParserFactory.createParser(ext).parse(fis);
            System.out.println("CONTENT: " + content.toString());
            JsonExporter exporter = new JsonExporter();
            exporter.export(content, "src/main/resources/exported.json");
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
