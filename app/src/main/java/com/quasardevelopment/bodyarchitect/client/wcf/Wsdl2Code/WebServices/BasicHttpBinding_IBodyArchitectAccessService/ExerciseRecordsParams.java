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

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.RecordMode;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Hashtable;

public class ExerciseRecordsParams implements KvmSerializable {
    
    public String exerciseId;
    public RecordMode mode;
    public boolean modeSpecified;
    
    public ExerciseRecordsParams(){}
    
    public ExerciseRecordsParams(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("ExerciseId"))
        {
            Object obj = soapObject.getProperty("ExerciseId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ExerciseId");
                exerciseId = j.toString();
            }else if (obj!= null && obj instanceof String){
                exerciseId = (String) soapObject.getProperty("ExerciseId");
            }
        }
        if (soapObject.hasProperty("Mode"))
        {
            Object obj = soapObject.getProperty("Mode");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Mode");
                mode = RecordMode.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("ModeSpecified"))
        {
            Object obj = soapObject.getProperty("ModeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ModeSpecified");
                modeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                modeSpecified = (Boolean) soapObject.getProperty("ModeSpecified");
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return exerciseId;
            case 1:
                return mode.toString();
            case 2:
                return modeSpecified;
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
                info.name = "ExerciseId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Mode";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ModeSpecified";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
