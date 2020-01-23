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

public class PaymentBasketDTO implements KvmSerializable,Serializable {
    
    public String customerId;
    public String dateTime;
    public boolean dateTimeSpecified;
    public VectorPaymentDTO payments;
    public String profileId;
    public double totalPrice;
    public boolean totalPriceSpecified;
    public String globalId;
    public String id;
    public String ref;
    
    public PaymentBasketDTO(){}
    
    public PaymentBasketDTO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("CustomerId"))
        {
            Object obj = soapObject.getProperty("CustomerId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CustomerId");
                customerId = j.toString();
            }else if (obj!= null && obj instanceof String){
                customerId = (String) soapObject.getProperty("CustomerId");
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
        if (soapObject.hasProperty("Payments"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Payments");
            payments = new VectorPaymentDTO(j);
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
        if (soapObject.hasProperty("TotalPrice"))
        {
            Object obj = soapObject.getProperty("TotalPrice");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TotalPrice");
                totalPrice = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                totalPrice = (Integer) soapObject.getProperty("TotalPrice");
            }
        }
        if (soapObject.hasProperty("TotalPriceSpecified"))
        {
            Object obj = soapObject.getProperty("TotalPriceSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TotalPriceSpecified");
                totalPriceSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                totalPriceSpecified = (Boolean) soapObject.getProperty("TotalPriceSpecified");
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
                return customerId;
            case 1:
                return dateTime;
            case 2:
                return dateTimeSpecified;
            case 3:
                return payments;
            case 4:
                return profileId;
            case 5:
                return totalPrice;
            case 6:
                return totalPriceSpecified;
            case 7:
                return globalId;
            case 8:
                return id;
            case 9:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 10;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CustomerId";
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
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Payments";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfileId";
                break;
            case 5:
                info.type = Double.class;
                info.name = "TotalPrice";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TotalPriceSpecified";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
