package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 03.06.13
 * Time: 07:43
 * To change this template use File | Settings | File Templates.
 */
public class ItemCategory <T>
{
    public String category;
    public ArrayList<T> items = new ArrayList<T>();
}
