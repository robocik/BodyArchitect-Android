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
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.PublishStatus;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.TrainingPlanDifficult;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.WorkoutPlanPurpose;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;

public class SupplementCycleDefinitionDTO implements KvmSerializable,Serializable {
    
    public boolean canBeIllegal;
    public boolean canBeIllegalSpecified;
    public String comment;
    public TrainingPlanDifficult difficult;
    public boolean difficultSpecified;
    public WorkoutPlanPurpose purpose;
    public boolean purposeSpecified;
    public VectorSupplementCycleWeekDTO weeks;
    public String author;
    public String basedOnId;
    public String creationDate;
    public boolean creationDateSpecified;
    public String language;
    public String name;
    public UserDTO profile;
    public String publishDate;
    public boolean publishDateSpecified;
    public float rating;
    public boolean ratingSpecified;
    public PublishStatus status;
    public boolean statusSpecified;
    public float userRating;
    public boolean userRatingSpecified;
    public String userShortComment;
    public int version;
    public boolean versionSpecified;
    public String globalId;
    public String id;
    public String ref;
    
    public SupplementCycleDefinitionDTO(){}
    
    public SupplementCycleDefinitionDTO(SoapObject soapObject,ReferencesManager referencesTable)    throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("CanBeIllegal"))
        {
            Object obj = soapObject.getProperty("CanBeIllegal");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CanBeIllegal");
                canBeIllegal = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                canBeIllegal = (Boolean) soapObject.getProperty("CanBeIllegal");
            }
        }
        if (soapObject.hasProperty("CanBeIllegalSpecified"))
        {
            Object obj = soapObject.getProperty("CanBeIllegalSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CanBeIllegalSpecified");
                canBeIllegalSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                canBeIllegalSpecified = (Boolean) soapObject.getProperty("CanBeIllegalSpecified");
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
        if (soapObject.hasProperty("Difficult"))
        {
            Object obj = soapObject.getProperty("Difficult");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Difficult");
                difficult = TrainingPlanDifficult.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("DifficultSpecified"))
        {
            Object obj = soapObject.getProperty("DifficultSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("DifficultSpecified");
                difficultSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                difficultSpecified = (Boolean) soapObject.getProperty("DifficultSpecified");
            }
        }
        if (soapObject.hasProperty("Purpose"))
        {
            Object obj = soapObject.getProperty("Purpose");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Purpose");
                purpose = WorkoutPlanPurpose.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("PurposeSpecified"))
        {
            Object obj = soapObject.getProperty("PurposeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("PurposeSpecified");
                purposeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                purposeSpecified = (Boolean) soapObject.getProperty("PurposeSpecified");
            }
        }
        if (soapObject.hasProperty("Weeks"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Weeks");
            weeks = new VectorSupplementCycleWeekDTO(j);
        }
        if (soapObject.hasProperty("Author"))
        {
            Object obj = soapObject.getProperty("Author");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Author");
                author = j.toString();
            }else if (obj!= null && obj instanceof String){
                author = (String) soapObject.getProperty("Author");
            }
        }
        if (soapObject.hasProperty("BasedOnId"))
        {
            Object obj = soapObject.getProperty("BasedOnId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("BasedOnId");
                basedOnId = j.toString();
            }else if (obj!= null && obj instanceof String){
                basedOnId = (String) soapObject.getProperty("BasedOnId");
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
        if (soapObject.hasProperty("Language"))
        {
            Object obj = soapObject.getProperty("Language");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Language");
                language = j.toString();
            }else if (obj!= null && obj instanceof String){
                language = (String) soapObject.getProperty("Language");
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
        if (soapObject.hasProperty("Profile"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Profile");
            profile = (UserDTO)referencesTable.get(j,UserDTO.class);
            
        }
        if (soapObject.hasProperty("PublishDate"))
        {
            Object obj = soapObject.getProperty("PublishDate");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("PublishDate");
                publishDate = j.toString();
            }else if (obj!= null && obj instanceof String){
                publishDate = (String) soapObject.getProperty("PublishDate");
            }
        }
        if (soapObject.hasProperty("PublishDateSpecified"))
        {
            Object obj = soapObject.getProperty("PublishDateSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("PublishDateSpecified");
                publishDateSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                publishDateSpecified = (Boolean) soapObject.getProperty("PublishDateSpecified");
            }
        }
        if (soapObject.hasProperty("Rating"))
        {
            Object obj = soapObject.getProperty("Rating");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Rating");
                rating = Float.parseFloat(j.toString());
            }else if (obj!= null && obj instanceof Number){
                rating = (Integer) soapObject.getProperty("Rating");
            }
        }
        if (soapObject.hasProperty("RatingSpecified"))
        {
            Object obj = soapObject.getProperty("RatingSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RatingSpecified");
                ratingSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                ratingSpecified = (Boolean) soapObject.getProperty("RatingSpecified");
            }
        }
        if (soapObject.hasProperty("Status"))
        {
            Object obj = soapObject.getProperty("Status");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Status");
                status = PublishStatus.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("StatusSpecified"))
        {
            Object obj = soapObject.getProperty("StatusSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("StatusSpecified");
                statusSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                statusSpecified = (Boolean) soapObject.getProperty("StatusSpecified");
            }
        }
        if (soapObject.hasProperty("UserRating"))
        {
            Object obj = soapObject.getProperty("UserRating");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("UserRating");
                userRating = Float.parseFloat(j.toString());
            }else if (obj!= null && obj instanceof Number){
                userRating = (Integer) soapObject.getProperty("UserRating");
            }
        }
        if (soapObject.hasProperty("UserRatingSpecified"))
        {
            Object obj = soapObject.getProperty("UserRatingSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("UserRatingSpecified");
                userRatingSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                userRatingSpecified = (Boolean) soapObject.getProperty("UserRatingSpecified");
            }
        }
        if (soapObject.hasProperty("UserShortComment"))
        {
            Object obj = soapObject.getProperty("UserShortComment");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("UserShortComment");
                userShortComment = j.toString();
            }else if (obj!= null && obj instanceof String){
                userShortComment = (String) soapObject.getProperty("UserShortComment");
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
                return canBeIllegal;
            case 1:
                return canBeIllegalSpecified;
            case 2:
                return comment;
            case 3:
                return difficult.toString();
            case 4:
                return difficultSpecified;
            case 5:
                return purpose.toString();
            case 6:
                return purposeSpecified;
            case 7:
                return weeks;
            case 8:
                return author;
            case 9:
                return basedOnId;
            case 10:
                return creationDate;
            case 11:
                return creationDateSpecified;
            case 12:
                return language;
            case 13:
                return name;
            case 14:
                return profile;
            case 15:
                return publishDate;
            case 16:
                return publishDateSpecified;
            case 17:
                return rating;
            case 18:
                return ratingSpecified;
            case 19:
                return status.toString();
            case 20:
                return statusSpecified;
            case 21:
                return userRating;
            case 22:
                return userRatingSpecified;
            case 23:
                return userShortComment;
            case 24:
                return version;
            case 25:
                return versionSpecified;
            case 26:
                return globalId;
            case 27:
                return id;
            case 28:
                return ref;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 29;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CanBeIllegal";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CanBeIllegalSpecified";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Difficult";
                break;
            case 4:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "DifficultSpecified";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Purpose";
                break;
            case 6:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PurposeSpecified";
                break;
            case 7:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Weeks";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Author";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BasedOnId";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CreationDate";
                break;
            case 11:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "CreationDateSpecified";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Language";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Name";
                break;
            case 14:
                info.type = UserDTO.class;
                info.name = "Profile";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PublishDate";
                break;
            case 16:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PublishDateSpecified";
                break;
            case 17:
                info.type = Float.class;
                info.name = "Rating";
                break;
            case 18:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "RatingSpecified";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Status";
                break;
            case 20:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "StatusSpecified";
                break;
            case 21:
                info.type = Float.class;
                info.name = "UserRating";
                break;
            case 22:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "UserRatingSpecified";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UserShortComment";
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
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Id";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Ref";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
