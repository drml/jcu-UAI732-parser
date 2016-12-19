package com.doktor;

/**
 * Custom exception thrown when the parser fails
 *
 * Created by Ondřej Doktor on 5.12.2016.
 */
class InvalidSyntaxException extends Exception {

    InvalidSyntaxException(String message)
    {
        super(message);
    }

}
