package com.quasardevelopment.bodyarchitect.client.model;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.05.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public interface IPagedResult<T extends BAGlobalObject>
        {
        Vector<T> getItems();
        }
