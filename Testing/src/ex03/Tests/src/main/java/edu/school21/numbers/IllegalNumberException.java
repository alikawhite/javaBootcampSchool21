package edu.school21.numbers;


public class IllegalNumberException extends RuntimeException {

    public IllegalNumberException() {
        super("Error: The value provided is not valid. The number must be greater than 1.");
    }
}