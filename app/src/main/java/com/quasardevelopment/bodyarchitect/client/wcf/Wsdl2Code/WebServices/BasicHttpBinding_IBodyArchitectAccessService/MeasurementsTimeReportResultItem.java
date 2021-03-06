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

public class MeasurementsTimeReportResultItem implements KvmSerializable {
    
    public String dateTime;
    public boolean dateTimeSpecified;
    public String sizeEntryId;
    public WymiaryDTO wymiary;
    
    public MeasurementsTimeReportResultItem(){}
    
    public MeasurementsTimeReportResultItem(SoapObject soapObject,ReferencesManager referencesTable) throws ParseException
    {
        if (soapObject == null)
            return;
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
        if (soapObject.hasProperty("SizeEntryId"))
        {
            Object obj = soapObject.getProperty("SizeEntryId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("SizeEntryId");
                sizeEntryId = j.toString();
            }else if (obj!= null && obj instanceof String){
                sizeEntryId = (String) soapObject.getProperty("SizeEntryId");
            }
        }
        if (soapObject.hasProperty("Wymiary"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Wymiary");
            wymiary = (WymiaryDTO)referencesTable.get(j,WymiaryDTO.class);
            
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return dateTime;
            case 1:
                return dateTimeSpecified;
            case 2:
                return sizeEntryId;
            case 3:
                return wymiary;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 4;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DateTime";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DateTimeSpecified";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SizeEntryId";
                break;
            case 3:
                info.type = WymiaryDTO.class;
                info.name = "Wymiary";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
