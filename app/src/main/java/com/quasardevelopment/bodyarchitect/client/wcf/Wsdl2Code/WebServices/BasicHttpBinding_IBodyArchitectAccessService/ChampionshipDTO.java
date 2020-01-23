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
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.ChampionshipType;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.ScheduleEntryState;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;

public class ChampionshipDTO implements KvmSerializable,Serializable {
    
    public VectorChampionshipCategoryDTO categories;
    public ChampionshipType championshipType;
    public boolean championshipTypeSpecified;
    public String comment;
    public VectorChampionshipCustomerDTO customers;
    public String endTime;
    public boolean endTimeSpecified;
    public VectorChampionshipEntryDTO entries;
    public VectorChampionshipGroupDTO groups;
    public boolean isLocked;
    public boolean isLockedSpecified;
    public String myPlaceId;
    public String name;
    public double price;
    public boolean priceSpecified;
    public String remindBefore;
    public VectorScheduleEntryReservationDTO reservations;
    public VectorChampionshipResultItemDTO results;
    public String startTime;
    public boolean startTimeSpecified;
    public ScheduleEntryState state;
    public boolean stateSpecified;
    public int teamCount;
    public boolean teamCountSpecified;
    public int version;
    public boolean versionSpecified;
    public VectorDecimal weightMenCategories;
    public VectorDecimal weightWomenCategories;
    public String globalId;
    public String id;
    public String ref;
    
    public ChampionshipDTO(){}
    
    public ChampionshipDTO(SoapObject soapObject,ReferencesManager referencesTable)    throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Categories"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Categories");
            categories = new VectorChampionshipCategoryDTO(j);
        }
        if (soapObject.hasProperty("ChampionshipType"))
        {
            Object obj = soapObject.getProperty("ChampionshipType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ChampionshipType");
                championshipType = ChampionshipType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("ChampionshipTypeSpecified"))
        {
            Object obj = soapObject.getProperty("ChampionshipTypeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ChampionshipTypeSpecified");
                championshipTypeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                championshipTypeSpecified = (Boolean) soapObject.getProperty("ChampionshipTypeSpecified");
            }
        }
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
        if (soapObject.hasProperty("Customers"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Customers");
            customers = new VectorChampionshipCustomerDTO(j);
        }
        if (soapObject.hasProperty("EndTime"))
        {
            Object obj = soapObject.getProperty("EndTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("EndTime");
                endTime = j.toString();
            }else if (obj!= null && obj instanceof String){
                endTime = (String) soapObject.getProperty("EndTime");
            }
        }
        if (soapObject.hasProperty("EndTimeSpecified"))
        {
            Object obj = soapObject.getProperty("EndTimeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("EndTimeSpecified");
                endTimeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                endTimeSpecified = (Boolean) soapObject.getProperty("EndTimeSpecified");
            }
        }
        if (soapObject.hasProperty("Entries"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Entries");
            entries = new VectorChampionshipEntryDTO(j,referencesTable);
        }
        if (soapObject.hasProperty("Groups"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Groups");
            groups = new VectorChampionshipGroupDTO(j);
        }
        if (soapObject.hasProperty("IsLocked"))
        {
            Object obj = soapObject.getProperty("IsLocked");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsLocked");
                isLocked = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isLocked = (Boolean) soapObject.getProperty("IsLocked");
            }
        }
        if (soapObject.hasProperty("IsLockedSpecified"))
        {
            Object obj = soapObject.getProperty("IsLockedSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsLockedSpecified");
                isLockedSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isLockedSpecified = (Boolean) soapObject.getProperty("IsLockedSpecified");
            }
        }
        if (soapObject.hasProperty("MyPlaceId"))
        {
            Object obj = soapObject.getProperty("MyPlaceId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("MyPlaceId");
                myPlaceId = j.toString();
            }else if (obj!= null && obj instanceof String){
                myPlaceId = (String) soapObject.getProperty("MyPlaceId");
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
        if (soapObject.hasProperty("RemindBefore"))
        {
            Object obj = soapObject.getProperty("RemindBefore");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RemindBefore");
                remindBefore = j.toString();
            }else if (obj!= null && obj instanceof String){
                remindBefore = (String) soapObject.getProperty("RemindBefore");
            }
        }
        if (soapObject.hasProperty("Reservations"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Reservations");
            reservations = new VectorScheduleEntryReservationDTO(j);
        }
        if (soapObject.hasProperty("Results"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Results");
            results = new VectorChampionshipResultItemDTO(j);
        }
        if (soapObject.hasProperty("StartTime"))
        {
            Object obj = soapObject.getProperty("StartTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("StartTime");
                startTime = j.toString();
            }else if (obj!= null && obj instanceof String){
                startTime = (String) soapObject.getProperty("StartTime");
            }
        }
        if (soapObject.hasProperty("StartTimeSpecified"))
        {
            Object obj = soapObject.getProperty("StartTimeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("StartTimeSpecified");
                startTimeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                startTimeSpecified = (Boolean) soapObject.getProperty("StartTimeSpecified");
            }
        }
        if (soapObject.hasProperty("State"))
        {
            Object obj = soapObject.getProperty("State");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("State");
                state = ScheduleEntryState.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("StateSpecified"))
        {
            Object obj = soapObject.getProperty("StateSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("StateSpecified");
                stateSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                stateSpecified = (Boolean) soapObject.getProperty("StateSpecified");
            }
        }
        if (soapObject.hasProperty("TeamCount"))
        {
            Object obj = soapObject.getProperty("TeamCount");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TeamCount");
                teamCount = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                teamCount = (Integer) soapObject.getProperty("TeamCount");
            }
        }
        if (soapObject.hasProperty("TeamCountSpecified"))
        {
            Object obj = soapObject.getProperty("TeamCountSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TeamCountSpecified");
                teamCountSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                teamCountSpecified = (Boolean) soapObject.getProperty("TeamCountSpecified");
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
        if (soapObject.hasProperty("WeightMenCategories"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("WeightMenCategories");
            weightMenCategories = new VectorDecimal(j);
        }
        if (soapObject.hasProperty("WeightWomenCategories"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("WeightWomenCategories");
            weightWomenCategories = new VectorDecimal(j);
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
                return categories;
            case 1:
                return championshipType.toString();
            case 2:
                return championshipTypeSpecified;
            case 3:
                return comment;
            case 4:
                return customers;
            case 5:
                return endTime;
            case 6:
                return endTimeSpecified;
            case 7:
                return entries;
            case 8:
                return groups;
            case 9:
                return isLocked;
            case 10:
                return isLockedSpecified;
            case 11:
                return myPlaceId;
            case 12:
                return name;
            case 13:
                return price;
            case 14:
                return priceSpecified;
            case 15:
                return remindBefore;
            case 16:
                return reservations;
            case 17:
                return results;
            case 18:
                return startTime;
            case 19:
                return startTimeSpecified;
            case 20:
                return state.toString();
            case 21:
                return stateSpecified;
            case 22:
                return teamCount;
            case 23:
                return teamCountSpecified;
            case 24:
                return version;
            case 25:
                return versionSpecified;
            case 26:
                return weightMenCategories;
            case 27:
                return weightWomenCategories;
            case 28:
                return globalId;
            case 29:
                return id;
            case 30:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 31;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Categories";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ChampionshipType";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "ChampionshipTypeSpecified";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 4:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Customers";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EndTime";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "EndTimeSpecified";
                break;
            case 7:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Entries";
                break;
            case 8:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Groups";
                break;
            case 9:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsLocked";
                break;
            case 10:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsLockedSpecified";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MyPlaceId";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Name";
                break;
            case 13:
                info.type = Double.class;
                info.name = "Price";
                break;
            case 14:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PriceSpecified";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RemindBefore";
                break;
            case 16:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Reservations";
                break;
            case 17:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Results";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "StartTime";
                break;
            case 19:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "StartTimeSpecified";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "State";
                break;
            case 21:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "StateSpecified";
                break;
            case 22:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TeamCount";
                break;
            case 23:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TeamCountSpecified";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Version";
                break;
            case 25:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "VersionSpecified";
                break;
            case 26:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "WeightMenCategories";
                break;
            case 27:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "WeightWomenCategories";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 30:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
