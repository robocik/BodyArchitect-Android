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

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.SupplementCycleDayRepetitions;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.TimeType;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Hashtable;

public class SupplementCycleEntryDTO implements KvmSerializable {
    
    public String comment;
    public SupplementCycleDayRepetitions repetitions;
    public boolean repetitionsSpecified;
    public TimeType timeType;
    public boolean timeTypeSpecified;
    public String globalId;
    public String id;
    public String ref;
    
    public SupplementCycleEntryDTO(){}
    
    public SupplementCycleEntryDTO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Comment"))
        {
            Object obj = soapObject.getProperty("Comment");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Comment");
                comment = j.toString();
            }else if (obj!= null && obj instanceof String){
                comment = (String) soapObject.getProperty("Comment");
            }
        }
        if (soapObject.hasProperty("Repetitions"))
        {
            Object obj = soapObject.getProperty("Repetitions");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Repetitions");
                repetitions = SupplementCycleDayRepetitions.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("RepetitionsSpecified"))
        {
            Object obj = soapObject.getProperty("RepetitionsSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RepetitionsSpecified");
                repetitionsSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                repetitionsSpecified = (Boolean) soapObject.getProperty("RepetitionsSpecified");
            }
        }
        if (soapObject.hasProperty("TimeType"))
        {
            Object obj = soapObject.getProperty("TimeType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TimeType");
                timeType = TimeType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("TimeTypeSpecified"))
        {
            Object obj = soapObject.getProperty("TimeTypeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TimeTypeSpecified");
                timeTypeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                timeTypeSpecified = (Boolean) soapObject.getProperty("TimeTypeSpecified");
            }
        }
        if (soapObject.hasProperty("GlobalId"))
        {
            Object obj = soapObject.getProperty("GlobalId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("GlobalId");
                globalId = j.toString();
            }else if (obj!= null && obj instanceof String){
                globalId = (String) soapObject.getProperty("GlobalId");
            }
        }
        if (soapObject.hasProperty("Id"))
        {
            Object obj = soapObject.getProperty("Id");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Id");
                id = j.toString();
            }else if (obj!= null && obj instanceof String){
                id = (String) soapObject.getProperty("Id");
            }
        }
        if (soapObject.hasProperty("Ref"))
        {
            Object obj = soapObject.getProperty("Ref");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Ref");
                ref = j.toString();
            }else if (obj!= null && obj instanceof String){
                ref = (String) soapObject.getProperty("Ref");
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return comment;
            case 1:
                return repetitions.toString();
            case 2:
                return repetitionsSpecified;
            case 3:
                return timeType.toString();
            case 4:
                return timeTypeSpecified;
            case 5:
                return globalId;
            case 6:
                return id;
            case 7:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 8;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Repetitions";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "RepetitionsSpecified";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TimeType";
                break;
            case 4:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TimeTypeSpecified";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
