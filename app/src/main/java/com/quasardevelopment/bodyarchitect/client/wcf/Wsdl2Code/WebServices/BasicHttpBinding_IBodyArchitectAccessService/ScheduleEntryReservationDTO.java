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

public class ScheduleEntryReservationDTO implements KvmSerializable,Serializable {
    
    public String enterDateTime;
    public boolean enterDateTimeSpecified;
    public String leaveDateTime;
    public boolean leaveDateTimeSpecified;
    public String scheduleEntryId;
    public String customerId;
    public String dateTime;
    public boolean dateTimeSpecified;
    public boolean isPaid;
    public boolean isPaidSpecified;
    public String name;
    public double price;
    public boolean priceSpecified;
    public int version;
    public boolean versionSpecified;
    public String globalId;
    public String id;
    public String ref;
    
    public ScheduleEntryReservationDTO(){}
    
    public ScheduleEntryReservationDTO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("EnterDateTime"))
        {
            Object obj = soapObject.getProperty("EnterDateTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("EnterDateTime");
                enterDateTime = j.toString();
            }else if (obj!= null && obj instanceof String){
                enterDateTime = (String) soapObject.getProperty("EnterDateTime");
            }
        }
        if (soapObject.hasProperty("EnterDateTimeSpecified"))
        {
            Object obj = soapObject.getProperty("EnterDateTimeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("EnterDateTimeSpecified");
                enterDateTimeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                enterDateTimeSpecified = (Boolean) soapObject.getProperty("EnterDateTimeSpecified");
            }
        }
        if (soapObject.hasProperty("LeaveDateTime"))
        {
            Object obj = soapObject.getProperty("LeaveDateTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("LeaveDateTime");
                leaveDateTime = j.toString();
            }else if (obj!= null && obj instanceof String){
                leaveDateTime = (String) soapObject.getProperty("LeaveDateTime");
            }
        }
        if (soapObject.hasProperty("LeaveDateTimeSpecified"))
        {
            Object obj = soapObject.getProperty("LeaveDateTimeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("LeaveDateTimeSpecified");
                leaveDateTimeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                leaveDateTimeSpecified = (Boolean) soapObject.getProperty("LeaveDateTimeSpecified");
            }
        }
        if (soapObject.hasProperty("ScheduleEntryId"))
        {
            Object obj = soapObject.getProperty("ScheduleEntryId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ScheduleEntryId");
                scheduleEntryId = j.toString();
            }else if (obj!= null && obj instanceof String){
                scheduleEntryId = (String) soapObject.getProperty("ScheduleEntryId");
            }
        }
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
        if (soapObject.hasProperty("IsPaid"))
        {
            Object obj = soapObject.getProperty("IsPaid");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsPaid");
                isPaid = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isPaid = (Boolean) soapObject.getProperty("IsPaid");
            }
        }
        if (soapObject.hasProperty("IsPaidSpecified"))
        {
            Object obj = soapObject.getProperty("IsPaidSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsPaidSpecified");
                isPaidSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isPaidSpecified = (Boolean) soapObject.getProperty("IsPaidSpecified");
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
                return enterDateTime;
            case 1:
                return enterDateTimeSpecified;
            case 2:
                return leaveDateTime;
            case 3:
                return leaveDateTimeSpecified;
            case 4:
                return scheduleEntryId;
            case 5:
                return customerId;
            case 6:
                return dateTime;
            case 7:
                return dateTimeSpecified;
            case 8:
                return isPaid;
            case 9:
                return isPaidSpecified;
            case 10:
                return name;
            case 11:
                return price;
            case 12:
                return priceSpecified;
            case 13:
                return version;
            case 14:
                return versionSpecified;
            case 15:
                return globalId;
            case 16:
                return id;
            case 17:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 18;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EnterDateTime";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "EnterDateTimeSpecified";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LeaveDateTime";
                break;
            case 3:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "LeaveDateTimeSpecified";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ScheduleEntryId";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CustomerId";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DateTime";
                break;
            case 7:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DateTimeSpecified";
                break;
            case 8:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsPaid";
                break;
            case 9:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsPaidSpecified";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Name";
                break;
            case 11:
                info.type = Double.class;
                info.name = "Price";
                break;
            case 12:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PriceSpecified";
                break;
            case 13:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Version";
                break;
            case 14:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "VersionSpecified";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}