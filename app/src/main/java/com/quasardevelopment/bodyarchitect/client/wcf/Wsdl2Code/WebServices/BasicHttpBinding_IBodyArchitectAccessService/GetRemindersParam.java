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

import java.util.Hashtable;

public class GetRemindersParam implements KvmSerializable {
    
    public VectorReminderType types;
    
    public GetRemindersParam(){}
    
    public GetRemindersParam(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Types"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Types");
            types = new VectorReminderType(j);
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return types;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 1;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Types";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}