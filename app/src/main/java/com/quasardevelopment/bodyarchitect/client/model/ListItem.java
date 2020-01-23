package com.quasardevelopment.bodyarchitect.client.model;

public class ListItem
{

    public ListItem()
    {

    }

    public ListItem(String text,Object value)
    {
        Text=text;
        Value=value;

    }
    public String Text;
    public Object Value;

    public String toString() {
        return Text;
    }
}
