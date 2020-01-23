package com.quasardevelopment.bodyarchitect.client.wcf.envelopes;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 18.04.13
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class WcfResult <T>
{
    public Exception Exception;
    public T Result;
    public Object Tag;
}
