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

import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class MyTrainingDTO extends MyTrainingLightDTO implements Serializable {

    public VectorEntryObjectDTO entryObjects;

    
    public MyTrainingDTO(){}
    
    public MyTrainingDTO(SoapObject soapObject,ReferencesManager referencesTable)     throws Exception
    {
        super(soapObject,referencesTable);
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("EntryObjects"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("EntryObjects");
            entryObjects = new VectorEntryObjectDTO(j,referencesTable);
        }

    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return entryObjects;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 1+super.getPropertyCount();
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        super.getPropertyInfo(index,arg1,info);
        switch(index){
            case 0:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "EntryObjects";
                break;
        }

    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
