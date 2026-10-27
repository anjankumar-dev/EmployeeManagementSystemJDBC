package com.anjan.exceptions;

public class EmployeeAlreadyExistException extends Exception{
    public EmployeeAlreadyExistException(String msg){
        super(msg);
    }
}
