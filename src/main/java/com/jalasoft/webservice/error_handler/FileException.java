package com.jalasoft.webservice.error_handler;

public class FileException extends Exception {
    public FileException(String message, Throwable e) {
        super(message, e);
    }
}
