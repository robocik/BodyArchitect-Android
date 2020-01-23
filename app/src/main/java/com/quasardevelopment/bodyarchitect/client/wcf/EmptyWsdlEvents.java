package com.quasardevelopment.bodyarchitect.client.wcf;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 29.05.13
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
public class EmptyWsdlEvents implements IWsdl2CodeEvents {
    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
