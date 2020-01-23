package com.quasardevelopment.bodyarchitect.client.model.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 13.06.13
 * Time: 09:01
 * To change this template use File | Settings | File Templates.
 */
public class ObjectNotFoundException extends Exception
{
    public ObjectNotFoundException()
    {

    }

    public ObjectNotFoundException(String message)
    {
        super(message);
    }
}
