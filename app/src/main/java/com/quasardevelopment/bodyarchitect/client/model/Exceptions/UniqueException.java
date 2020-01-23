package com.quasardevelopment.bodyarchitect.client.model.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 03.06.13
 * Time: 08:55
 * To change this template use File | Settings | File Templates.
 */
public class UniqueException extends Exception
{
    public UniqueException()
    {

    }

    public UniqueException(String message)
    {
        super(message);
    }
}
