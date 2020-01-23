package com.quasardevelopment.bodyarchitect.client.wcf;

import org.ksoap2.transport.ServiceConnectionSE;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 18.08.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public class BAServiceConnection extends ServiceConnectionSE {


    public BAServiceConnection(Proxy proxy, String url, int timeout) throws IOException {
        super(proxy,url,timeout);
        setRequestProperty("Connection", "close");
    }


}
