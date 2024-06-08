package com.adventour.web.utils;

public class InvalidDataException extends  RuntimeException{
    public InvalidDataException(String message) {
        super(message);
    }
    public InvalidDataException(String message, Throwable err){
        super(message, err);
    }
}
