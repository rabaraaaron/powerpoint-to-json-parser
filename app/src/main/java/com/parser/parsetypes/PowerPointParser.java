package com.parser.parsetypes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Base64;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

/**
 * An implementation of Parser. The PowerPointParser parses .ppt and .pptx formatted files.
 * Further Implementation: Refactor return value of parse method to be a generic export type. This would allow for
 * mulitple different types of exports. The desired method for this assignment is to export to a .json file, but there could be an instance
 * where the users may want to export to a different file type like .txt, or even export to a database, storage system, etc. So future goal would
 * be to return an object of type `Export` that you can later call Exporter.export(), and it will export to whatever the desired
 * method of exporting is.
 */
public class PowerPointParser implements Parser {

    /**
     * Parses the .ppt/.pptx into an easily processable data format.
     *
     * @param fis The FileInputStream to the file passed into the Command Line Arguments.
     * @return A JsonObject representation of the data collected in the textContent and imageContent Lists.
     * @throws IOException if file is not readable
     */
    @Override
    public JsonObject parse(FileInputStream fis) throws IOException {
        XMLSlideShow ppt = new XMLSlideShow(fis);
        ArrayList<String> textContent = new ArrayList<String>();
        ArrayList<String> imageContent = new ArrayList<String>();

        for (XSLFSlide slide : ppt.getSlides()) {
            textContent.add(slide.getTitle());
            for (XSLFShape shape : slide.getShapes()) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) shape;
                    textContent.add(textShape.getText());
                } else if (shape instanceof XSLFPictureShape) {
                    imageContent.add(handlePictureShapes((XSLFPictureShape) shape));
                } else if (shape instanceof XSLFTable) {
                    XSLFTable table = (XSLFTable) shape;
                     for (XSLFTableRow row : table) {
                        System.out.println("ROW: " + row);
                    }
                }
            }
        }
        ppt.close();
        return convertContentToJsonObject(textContent, imageContent);
    }

    /**
     * Adds two integers and returns their sum.
     *
     * @param a The first integer operand.
     * @param b The second integer operand.
     * @return The sum of the two integers.
     * @throws IllegalArgumentException if either `a` or `b` is negative.
     * @see #subtract(int, int)
     */
    private String handlePictureShapes(XSLFPictureShape shape) {
        XSLFPictureData pictureData = shape.getPictureData();

        if (pictureData == null) {
            return "Found an externally linked image.";
        } 
        byte[] imageData = pictureData.getData();
        String contentType = pictureData.getContentType();
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return "data:" + contentType + ";base64," + base64Image;
    }

    /**
     * Adds two integers and returns their sum.
     *
     * @param a The first integer operand.
     * @param b The second integer operand.
     * @return The sum of the two integers.
     * @throws IllegalArgumentException if either `a` or `b` is negative.
     * @see #subtract(int, int)
     */
    private JsonObject convertContentToJsonObject(ArrayList<String> textContent, ArrayList<String> imageContent) {
        Gson gson = new Gson();
        JsonArray textContentJsonArray = (JsonArray) gson.toJsonTree(textContent);
        JsonArray imageContentJsonArray = (JsonArray) gson.toJsonTree(imageContent);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("texts", textContentJsonArray);
        jsonObject.add("images", imageContentJsonArray);
        return jsonObject;
    }
}
