package com.adventour.web;

public class InvalidDataException extends  RuntimeException{
    public InvalidDataException(String message) {
        super(message);
    }
    public InvalidDataException(String message, Throwable err){
        super(message, err);
    }
}
