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

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.MyPlaceOperationType;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Hashtable;

public class MyPlaceOperationParam implements KvmSerializable {
    
    public String myPlaceId;
    public MyPlaceOperationType operation;
    public boolean operationSpecified;
    
    public MyPlaceOperationParam(){}
    
    public MyPlaceOperationParam(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("MyPlaceId"))
        {
            Object obj = soapObject.getProperty("MyPlaceId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("MyPlaceId");
                myPlaceId = j.toString();
            }else if (obj!= null && obj instanceof String){
                myPlaceId = (String) soapObject.getProperty("MyPlaceId");
            }
        }
        if (soapObject.hasProperty("Operation"))
        {
            Object obj = soapObject.getProperty("Operation");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Operation");
                operation = MyPlaceOperationType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("OperationSpecified"))
        {
            Object obj = soapObject.getProperty("OperationSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("OperationSpecified");
                operationSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                operationSpecified = (Boolean) soapObject.getProperty("OperationSpecified");
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return myPlaceId;
            case 1:
                return operation.toString();
            case 2:
                return operationSpecified;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 3;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MyPlaceId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Operation";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "OperationSpecified";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
