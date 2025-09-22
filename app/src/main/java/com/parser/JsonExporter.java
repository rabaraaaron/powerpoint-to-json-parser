package com.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The class that handles exporting the extracted file content into a JSON file.
 * Further implementation: Create a generic Exporter class, that this class and others will implement. The current
 * method of exporting is to a local .json file. However, there may be a desire in the future for exporting extracted data
 * to other modes. Some ideas would be exporting to a storage service like S3. We may desire to vector embedd the data,
 * and store it into a vector store database. We may want other file types to be stored, such as .txt or xls.
 */
public class JsonExporter {

    /**
     * Adds two integers and returns their sum.
     *
     * @param a The first integer operand.
     * @param b The second integer operand.
     * @return The sum of the two integers.
     * @throws IllegalArgumentException if either `a` or `b` is negative.
     * @see #subtract(int, int)
     */
    public void export(JsonObject content, String filePath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(content, writer);
        }
    }
    
}
