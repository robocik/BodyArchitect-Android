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

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.ChampionshipCategoryType;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.ChampionshipWinningCategories;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.Gender;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

public class ChampionshipCategoryDTO implements KvmSerializable,Serializable {
    
    public ChampionshipWinningCategories category;
    public boolean categorySpecified;
    public Gender gender;
    public boolean genderSpecified;
    public boolean isAgeStrict;
    public boolean isAgeStrictSpecified;
    public boolean isOfficial;
    public boolean isOfficialSpecified;
    public ChampionshipCategoryType type;
    public boolean typeSpecified;
    public String globalId;
    public String id;
    public String ref;
    
    public ChampionshipCategoryDTO(){}
    
    public ChampionshipCategoryDTO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Category"))
        {
            Object obj = soapObject.getProperty("Category");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Category");
                category = ChampionshipWinningCategories.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("CategorySpecified"))
        {
            Object obj = soapObject.getProperty("CategorySpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CategorySpecified");
                categorySpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                categorySpecified = (Boolean) soapObject.getProperty("CategorySpecified");
            }
        }
        if (soapObject.hasProperty("Gender"))
        {
            Object obj = soapObject.getProperty("Gender");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Gender");
                gender = Gender.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("GenderSpecified"))
        {
            Object obj = soapObject.getProperty("GenderSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("GenderSpecified");
                genderSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                genderSpecified = (Boolean) soapObject.getProperty("GenderSpecified");
            }
        }
        if (soapObject.hasProperty("IsAgeStrict"))
        {
            Object obj = soapObject.getProperty("IsAgeStrict");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsAgeStrict");
                isAgeStrict = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isAgeStrict = (Boolean) soapObject.getProperty("IsAgeStrict");
            }
        }
        if (soapObject.hasProperty("IsAgeStrictSpecified"))
        {
            Object obj = soapObject.getProperty("IsAgeStrictSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsAgeStrictSpecified");
                isAgeStrictSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isAgeStrictSpecified = (Boolean) soapObject.getProperty("IsAgeStrictSpecified");
            }
        }
        if (soapObject.hasProperty("IsOfficial"))
        {
            Object obj = soapObject.getProperty("IsOfficial");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsOfficial");
                isOfficial = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isOfficial = (Boolean) soapObject.getProperty("IsOfficial");
            }
        }
        if (soapObject.hasProperty("IsOfficialSpecified"))
        {
            Object obj = soapObject.getProperty("IsOfficialSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("IsOfficialSpecified");
                isOfficialSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isOfficialSpecified = (Boolean) soapObject.getProperty("IsOfficialSpecified");
            }
        }
        if (soapObject.hasProperty("Type"))
        {
            Object obj = soapObject.getProperty("Type");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Type");
                type = ChampionshipCategoryType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("TypeSpecified"))
        {
            Object obj = soapObject.getProperty("TypeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TypeSpecified");
                typeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                typeSpecified = (Boolean) soapObject.getProperty("TypeSpecified");
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
                return category.toString();
            case 1:
                return categorySpecified;
            case 2:
                return gender.toString();
            case 3:
                return genderSpecified;
            case 4:
                return isAgeStrict;
            case 5:
                return isAgeStrictSpecified;
            case 6:
                return isOfficial;
            case 7:
                return isOfficialSpecified;
            case 8:
                return type.toString();
            case 9:
                return typeSpecified;
            case 10:
                return globalId;
            case 11:
                return id;
            case 12:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 13;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Category";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CategorySpecified";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Gender";
                break;
            case 3:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "GenderSpecified";
                break;
            case 4:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsAgeStrict";
                break;
            case 5:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsAgeStrictSpecified";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsOfficial";
                break;
            case 7:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsOfficialSpecified";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Type";
                break;
            case 9:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "TypeSpecified";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}