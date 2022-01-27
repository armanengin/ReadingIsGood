package com.arman.reading.sgood.exception;

public class StockIsNegativeException extends RuntimeException{
    public StockIsNegativeException(String message){
        super(message);
    }
}
