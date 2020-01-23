package com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.4
//
// Date Of Creation: 4/11/2013 5:22:00 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

public class A6WDay implements KvmSerializable,Serializable {
    
    public int day;
    public int repetitionNumber;
    public int setNumber;
    
    public A6WDay(){}
    
    public A6WDay(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("day"))
        {
            Object obj = soapObject.getProperty("day");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("day");
                day = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                day = (Integer) soapObject.getProperty("day");
            }
        }
        if (soapObject.hasProperty("repetitionNumber"))
        {
            Object obj = soapObject.getProperty("repetitionNumber");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("repetitionNumber");
                repetitionNumber = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                repetitionNumber = (Integer) soapObject.getProperty("repetitionNumber");
            }
        }
        if (soapObject.hasProperty("setNumber"))
        {
            Object obj = soapObject.getProperty("setNumber");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("setNumber");
                setNumber = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                setNumber = (Integer) soapObject.getProperty("setNumber");
            }
        }
    }
    @Override
    public Object getProperty(int propertyIndex) {
        if(propertyIndex==0)
        {
            return day;
        }
        if(propertyIndex==1)
        {
            return repetitionNumber;
        }
        if(propertyIndex==2)
        {
            return setNumber;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 3;
    }
    
    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "day";
            info.namespace= "http://schemas.datacontract.org/2004/07/BodyArchitect.Service.V2.Model";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "repetitionNumber";
            info.namespace= "http://schemas.datacontract.org/2004/07/BodyArchitect.Service.V2.Model";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "setNumber";
            info.namespace= "http://schemas.datacontract.org/2004/07/BodyArchitect.Service.V2.Model";
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}