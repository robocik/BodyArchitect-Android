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
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.CustomerGroupRestrictedType;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;

public class CustomerGroupDTO implements KvmSerializable,Serializable {
    
    public String color;
    public String creationDate;
    public boolean creationDateSpecified;
    public VectorCustomerDTO customers;
    public String defaultActivityId;
    public int maxPersons;
    public boolean maxPersonsSpecified;
    public String name;
    public String profileId;
    public CustomerGroupRestrictedType restrictedType;
    public boolean restrictedTypeSpecified;
    public int version;
    public boolean versionSpecified;
    public String globalId;
    public String id;
    public String ref;
    
    public CustomerGroupDTO(){}
    
    public CustomerGroupDTO(SoapObject soapObject,ReferencesManager referencesTable)   throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Color"))
        {
            Object obj = soapObject.getProperty("Color");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Color");
                color = j.toString();
            }else if (obj!= null && obj instanceof String){
                color = (String) soapObject.getProperty("Color");
            }
        }
        if (soapObject.hasProperty("CreationDate"))
        {
            Object obj = soapObject.getProperty("CreationDate");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CreationDate");
                creationDate = j.toString();
            }else if (obj!= null && obj instanceof String){
                creationDate = (String) soapObject.getProperty("CreationDate");
            }
        }
        if (soapObject.hasProperty("CreationDateSpecified"))
        {
            Object obj = soapObject.getProperty("CreationDateSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CreationDateSpecified");
                creationDateSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                creationDateSpecified = (Boolean) soapObject.getProperty("CreationDateSpecified");
            }
        }
        if (soapObject.hasProperty("Customers"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Customers");
            customers = new VectorCustomerDTO(j,referencesTable);
        }
        if (soapObject.hasProperty("DefaultActivityId"))
        {
            Object obj = soapObject.getProperty("DefaultActivityId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("DefaultActivityId");
                defaultActivityId = j.toString();
            }else if (obj!= null && obj instanceof String){
                defaultActivityId = (String) soapObject.getProperty("DefaultActivityId");
            }
        }
        if (soapObject.hasProperty("MaxPersons"))
        {
            Object obj = soapObject.getProperty("MaxPersons");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("MaxPersons");
                maxPersons = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                maxPersons = (Integer) soapObject.getProperty("MaxPersons");
            }
        }
        if (soapObject.hasProperty("MaxPersonsSpecified"))
        {
            Object obj = soapObject.getProperty("MaxPersonsSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("MaxPersonsSpecified");
                maxPersonsSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                maxPersonsSpecified = (Boolean) soapObject.getProperty("MaxPersonsSpecified");
            }
        }
        if (soapObject.hasProperty("Name"))
        {
            Object obj = soapObject.getProperty("Name");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Name");
                name = j.toString();
            }else if (obj!= null && obj instanceof String){
                name = (String) soapObject.getProperty("Name");
            }
        }
        if (soapObject.hasProperty("ProfileId"))
        {
            Object obj = soapObject.getProperty("ProfileId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ProfileId");
                profileId = j.toString();
            }else if (obj!= null && obj instanceof String){
                profileId = (String) soapObject.getProperty("ProfileId");
            }
        }
        if (soapObject.hasProperty("RestrictedType"))
        {
            Object obj = soapObject.getProperty("RestrictedType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RestrictedType");
                restrictedType = CustomerGroupRestrictedType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("RestrictedTypeSpecified"))
        {
            Object obj = soapObject.getProperty("RestrictedTypeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RestrictedTypeSpecified");
                restrictedTypeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                restrictedTypeSpecified = (Boolean) soapObject.getProperty("RestrictedTypeSpecified");
            }
        }
        if (soapObject.hasProperty("Version"))
        {
            Object obj = soapObject.getProperty("Version");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Version");
                version = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                version = (Integer) soapObject.getProperty("Version");
            }
        }
        if (soapObject.hasProperty("VersionSpecified"))
        {
            Object obj = soapObject.getProperty("VersionSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("VersionSpecified");
                versionSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                versionSpecified = (Boolean) soapObject.getProperty("VersionSpecified");
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
                return color;
            case 1:
                return creationDate;
            case 2:
                return creationDateSpecified;
            case 3:
                return customers;
            case 4:
                return defaultActivityId;
            case 5:
                return maxPersons;
            case 6:
                return maxPersonsSpecified;
            case 7:
                return name;
            case 8:
                return profileId;
            case 9:
                return restrictedType.toString();
            case 10:
                return restrictedTypeSpecified;
            case 11:
                return version;
            case 12:
                return versionSpecified;
            case 13:
                return globalId;
            case 14:
                return id;
            case 15:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 16;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Color";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CreationDate";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CreationDateSpecified";
                break;
            case 3:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Customers";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DefaultActivityId";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "MaxPersons";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "MaxPersonsSpecified";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Name";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfileId";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RestrictedType";
                break;
            case 10:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "RestrictedTypeSpecified";
                break;
            case 11:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Version";
                break;
            case 12:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "VersionSpecified";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
