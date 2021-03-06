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

import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.IRatingable;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.PublishStatus;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.TrainingPlanDifficult;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.TrainingType;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.WorkoutPlanPurpose;
import org.joda.time.DateTime;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.UUID;

public class TrainingPlan extends BAGlobalObject implements KvmSerializable,Serializable,WcfInterfaces.IReferenceObject,IRatingable {
    
    public String comment;
    public VectorTrainingPlanDay days;
    public TrainingPlanDifficult difficult;
    public WorkoutPlanPurpose purpose;
    public int restSeconds;
    public TrainingType trainingType;
    public String author;
    public UUID basedOnId;
    public DateTime creationDate;
    public String language;
    public String name;
    public UserDTO profile;
    public DateTime publishDate;
    public float rating;
    public PublishStatus status;
    public float userRating;
    public String userShortComment;
    public String url;
    public int version;
    
    public TrainingPlan(){}
    
    public TrainingPlan(SoapObject soapObject,ReferencesManager referencesTable)     throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasAttribute("Id"))
        {
            Object obj = soapObject.getAttribute("Id");
            referencesTable.Add((String) obj, this);
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
        if (soapObject.hasProperty("Days"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Days");
            days = new VectorTrainingPlanDay(j,referencesTable);
        }
        if (soapObject.hasProperty("Difficult"))
        {
            Object obj = soapObject.getProperty("Difficult");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Difficult");
                difficult = TrainingPlanDifficult.fromString(j.toString());
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
        if (soapObject.hasProperty("RestSeconds"))
        {
            Object obj = soapObject.getProperty("RestSeconds");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RestSeconds");
                restSeconds = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                restSeconds = (Integer) soapObject.getProperty("RestSeconds");
            }
        }
        if (soapObject.hasProperty("TrainingType"))
        {
            Object obj = soapObject.getProperty("TrainingType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TrainingType");
                trainingType = TrainingType.fromString(j.toString());
            }
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
                basedOnId = UUID.fromString(j.toString());
            }else if (obj!= null && obj instanceof String){
                basedOnId = UUID.fromString((String) soapObject.getProperty("BasedOnId"));
            }
        }
        if (soapObject.hasProperty("CreationDate"))
        {
            Object obj = soapObject.getProperty("CreationDate");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CreationDate");
                creationDate = DateTimeHelper.ConvertFromWebServiceNew(j.toString());
            }else if (obj!= null && obj instanceof String){
                creationDate = DateTimeHelper.ConvertFromWebServiceNew((String) soapObject.getProperty("CreationDate"));
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
                publishDate = DateTimeHelper.ConvertFromWebServiceNew(j.toString());
            }else if (obj!= null && obj instanceof String){
                publishDate = DateTimeHelper.ConvertFromWebServiceNew((String) soapObject.getProperty("PublishDate"));
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
        if (soapObject.hasProperty("Status"))
        {
            Object obj = soapObject.getProperty("Status");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Status");
                status = PublishStatus.fromString(j.toString());
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
        if (soapObject.hasProperty("GlobalId"))
        {
            Object obj = soapObject.getProperty("GlobalId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("GlobalId");
                globalId = UUID.fromString(j.toString());
            }else if (obj!= null && obj instanceof String){
                globalId = UUID.fromString((String) soapObject.getProperty("GlobalId"));
            }
        }
        if (soapObject.hasProperty("Url"))
        {
            Object obj = soapObject.getProperty("Url");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Url");
                url = j.toString();
            }else if (obj!= null && obj instanceof String){
                url = (String) soapObject.getProperty("Url");
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return globalId;
            case 1:
                return comment;
            case 2:
                return days;
            case 3:
                return difficult.toString();
            case 4:
                return purpose.toString();
            case 5:
                return restSeconds;
            case 6:
                return trainingType.toString();
            case 7:
                return author;
            case 8:
                return basedOnId;
            case 9:
                return creationDate;
            case 10:
                return language;
            case 11:
                return name;
            case 12:
                return profile;
            case 13:
                return publishDate;
            case 14:
                return rating;
            case 15:
                return status.toString();
            case 16:
                return userRating;
            case 17:
                return userShortComment;
            case 18:
                return version;

        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 19;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 2:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Days";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Difficult";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Purpose";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RestSeconds";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TrainingType";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Author";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BasedOnId";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CreationDate";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Language";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Name";
                break;
            case 12:
                info.type = UserDTO.class;
                info.name = "Profile";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PublishDate";
                break;
            case 14:
                info.type = Float.class;
                info.name = "Rating";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Status";
                break;
            case 16:
                info.type = Float.class;
                info.name = "UserRating";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UserShortComment";
                break;
            case 18:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Version";
                break;
        }
        info.namespace= WcfConstans.ServiceNamespace;
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }

    public boolean isMine() {
        if(profile==null || !ApplicationState.getCurrent().getSessionData().profile.globalId.equals(profile.globalId))
        {
            return false;
        }
        return true;
    }

    public boolean isFavorite ()
    {

        boolean contains = ObjectsReposidory.getCache().getTrainingPlans().getItems().containsKey(globalId);
        if (!contains)
        {
            return false;
        }
        return profile != null && !profile.globalId.equals(ApplicationState.getCurrent().getSessionData().profile.globalId);
    }

    @Override
    public UUID getGlobalId() {
        return globalId;
    }

    @Override
    public float getRating() {
        return rating;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Float getUserRating() {
        return userRating;
    }

    @Override
    public String getUserShortComment() {
        return userShortComment;
    }

    @Override
    public UserDTO getProfile() {
        return profile;
    }
}
