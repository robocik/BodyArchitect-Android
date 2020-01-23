package com.quasardevelopment.bodyarchitect.client.wcf;


import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public interface IWcfMethod
{
    SoapSerializationEnvelope CreateSoapEnvelope() throws Exception;

    Object ProcessResult(SoapSerializationEnvelope envelope,SoapObject result) throws Exception;

}
