package com.quasardevelopment.bodyarchitect.client.util;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 17.04.13
 * Time: 08:49
 * To change this template use File | Settings | File Templates.
 */
public class Functions
{

    public interface IFunc<Res>
    {
         Res Func() throws Exception;
    }

    public interface IFunc1<T,Res>
    {
        Res Func(T p);
    }

    public interface IFunc2<T1,T2,Res>
    {
        Res Func(T1 p1,T2 p2);
    }

    public interface IAction
    {
        void Action();
    }

    public interface IAction1 <T>
    {
        void Action(T p1);
    }
}
