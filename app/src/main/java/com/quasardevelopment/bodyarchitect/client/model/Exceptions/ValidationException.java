package com.quasardevelopment.bodyarchitect.client.model.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 18.04.13
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends Exception
{
    public ValidationException()
    {

    }

    public ValidationException(String message)
    {
        super(message);
    }
}
