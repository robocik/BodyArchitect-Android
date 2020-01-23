package com.quasardevelopment.bodyarchitect.client.wcf;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 11.07.13
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class BAHttpTransport extends HttpTransportSE {

    /**
     * Creates instance of HttpTransportSE with set url
     *
     * @param url
     *            the destination to POST SOAP data
     */
    public BAHttpTransport(String url) {
        super(null, url);
    }

    /**
     * Creates instance of HttpTransportSE with set url and defines a
     * proxy server to use to access it
     *
     * @param proxy
     * Proxy information or <code>null</code> for direct access
     * @param url
     * The destination to POST SOAP data
     */
    public BAHttpTransport(Proxy proxy, String url) {
        super(proxy, url);
    }

    /**
     * Creates instance of HttpTransportSE with set url
     *
     * @param url
     *            the destination to POST SOAP data
     * @param timeout
     *   timeout for connection and Read Timeouts (milliseconds)
     */
    public BAHttpTransport(String url, int timeout) {
        super(url, timeout);
    }

    public BAHttpTransport(Proxy proxy, String url, int timeout) {
        super(proxy, url, timeout);
    }

     @Override
    public ServiceConnection getServiceConnection() throws IOException {
        return new BAServiceConnection(proxy, url, timeout);
    }
}
