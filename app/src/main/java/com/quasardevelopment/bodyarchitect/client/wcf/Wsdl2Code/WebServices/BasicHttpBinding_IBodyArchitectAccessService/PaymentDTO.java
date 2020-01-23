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

public class PaymentDTO implements KvmSerializable,Serializable {
    
    public int count;
    public boolean countSpecified;
    public String paymentBasketId;
    public double price;
    public boolean priceSpecified;
    public String dateTime;
    public boolean dateTimeSpecified;
    public ProductDTO product;
    public String globalId;
    public String id;
    public String ref;
    
    public PaymentDTO(){}
    
    public PaymentDTO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Count"))
        {
            Object obj = soapObject.getProperty("Count");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Count");
                count = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                count = (Integer) soapObject.getProperty("Count");
            }
        }
        if (soapObject.hasProperty("CountSpecified"))
        {
            Object obj = soapObject.getProperty("CountSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CountSpecified");
                countSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                countSpecified = (Boolean) soapObject.getProperty("CountSpecified");
            }
        }
        if (soapObject.hasProperty("PaymentBasketId"))
        {
            Object obj = soapObject.getProperty("PaymentBasketId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("PaymentBasketId");
                paymentBasketId = j.toString();
            }else if (obj!= null && obj instanceof String){
                paymentBasketId = (String) soapObject.getProperty("PaymentBasketId");
            }
        }
        if (soapObject.hasProperty("Price"))
        {
            Object obj = soapObject.getProperty("Price");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Price");
                price = Double.parseDouble(j.toString());
            }else if (obj!= null && obj instanceof Number){
                price = (Integer) soapObject.getProperty("Price");
            }
        }
        if (soapObject.hasProperty("PriceSpecified"))
        {
            Object obj = soapObject.getProperty("PriceSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("PriceSpecified");
                priceSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                priceSpecified = (Boolean) soapObject.getProperty("PriceSpecified");
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
        if (soapObject.hasProperty("Product"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Product");
            product =  new ProductDTO (j);
            
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
                return count;
            case 1:
                return countSpecified;
            case 2:
                return paymentBasketId;
            case 3:
                return price;
            case 4:
                return priceSpecified;
            case 5:
                return dateTime;
            case 6:
                return dateTimeSpecified;
            case 7:
                return product;
            case 8:
                return globalId;
            case 9:
                return id;
            case 10:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 11;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Count";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CountSpecified";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymentBasketId";
                break;
            case 3:
                info.type = Double.class;
                info.name = "Price";
                break;
            case 4:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PriceSpecified";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DateTime";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DateTimeSpecified";
                break;
            case 7:
                info.type = ProductDTO.class;
                info.name = "Product";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}