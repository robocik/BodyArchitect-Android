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
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.text.ParseException;
import java.util.Hashtable;

public class FeaturedEntryObjectDTO implements KvmSerializable {
    
    public String comment;
    public String dateTime;
    public boolean dateTimeSpecified;
    public UserDTO user;
    public String globalId;
    public String id;
    public String ref;
    
    public FeaturedEntryObjectDTO(){}
    
    public FeaturedEntryObjectDTO(SoapObject soapObject,ReferencesManager referencesTable)      throws ParseException
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
        if (soapObject.hasProperty("DateTime"))
        {
            Object obj = soapObject.getProperty("DateTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("DateTime");
                dateTime = j.toString();
            }else if (obj!= null && obj instanceof String){
                dateTime = (String) soapObject.getProperty("DateTime");
            }
        }
        if (soapObject.hasProperty("DateTimeSpecified"))
        {
            Object obj = soapObject.getProperty("DateTimeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("DateTimeSpecified");
                dateTimeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                dateTimeSpecified = (Boolean) soapObject.getProperty("DateTimeSpecified");
            }
        }
        if (soapObject.hasProperty("User"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("User");
            user = (UserDTO)referencesTable.get(j,UserDTO.class);
            
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
                return dateTime;
            case 2:
                return dateTimeSpecified;
            case 3:
                return user;
            case 4:
                return globalId;
            case 5:
                return id;
            case 6:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 7;
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
                info.name = "DateTime";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DateTimeSpecified";
                break;
            case 3:
                info.type = UserDTO.class;
                info.name = "User";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
