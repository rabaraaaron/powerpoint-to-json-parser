package com.parser.exceptions;

/**
 * A custom exception type for unsupported formats from either files or export types.
 * Further Implementation: Create more custom exception types for better error handling
 */
public class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
