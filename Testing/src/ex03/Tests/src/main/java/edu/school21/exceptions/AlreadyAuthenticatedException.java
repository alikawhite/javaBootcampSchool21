package edu.school21.exceptions;

public class AlreadyAuthenticatedException extends RuntimeException{
    public AlreadyAuthenticatedException() {
        super("Error! Authentication has already been performed.");
    }
}
