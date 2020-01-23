package com.quasardevelopment.bodyarchitect.client.model.Exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 09.07.13
 * Time: 07:59
 * To change this template use File | Settings | File Templates.
 */
public class ConsistencyException extends Exception
{
    public ConsistencyException()
    {

    }

    public ConsistencyException(String message)
    {
        super(message);
    }
}