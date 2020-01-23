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

import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import org.joda.time.DateTime;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.UUID;

public class TrainingDayInfoDTO extends BAGlobalObject implements KvmSerializable,Serializable,WcfInterfaces.IReferenceObject {
    
    public boolean allowComments;
    public String comment;
    public int commentsCount;
    public UUID customerId;
    public DateTime lastCommentDate;
    public UUID profileId;
    public DateTime trainingDate;

    
    public TrainingDayInfoDTO(){}
    
    public TrainingDayInfoDTO(SoapObject soapObject,ReferencesManager referencesTable)    throws ParseException
    {
        if (soapObject == null)
            return;
        if (soapObject.hasAttribute("Id"))
        {
            Object obj = soapObject.getAttribute("Id");
            referencesTable.Add((String) obj,this);
        }
        if (soapObject.hasProperty("AllowComments"))
        {
            Object obj = soapObject.getProperty("AllowComments");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("AllowComments");
                allowComments = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                allowComments = (Boolean) soapObject.getProperty("AllowComments");
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
        if (soapObject.hasProperty("CommentsCount"))
        {
            Object obj = soapObject.getProperty("CommentsCount");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CommentsCount");
                commentsCount = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                commentsCount = (Integer) soapObject.getProperty("CommentsCount");
            }
        }
        if (soapObject.hasProperty("CustomerId"))
        {
            Object obj = soapObject.getProperty("CustomerId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CustomerId");
                customerId = UUID.fromString(j.toString());
            }else if (obj!= null && obj instanceof String){
                customerId = UUID.fromString((String) soapObject.getProperty("CustomerId"));
            }
        }
        if (soapObject.hasProperty("LastCommentDate"))
        {
            Object obj = soapObject.getProperty("LastCommentDate");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("LastCommentDate");
                lastCommentDate =  DateTimeHelper.ConvertFromWebServiceNew(j.toString());
            }else if (obj!= null && obj instanceof String){
                lastCommentDate =  DateTimeHelper.ConvertFromWebServiceNew((String) soapObject.getProperty("LastCommentDate"));
            }
        }
        if (soapObject.hasProperty("ProfileId"))
        {
            Object obj = soapObject.getProperty("ProfileId");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("ProfileId");
                profileId = UUID.fromString(j.toString());
            }else if (obj!= null && obj instanceof String){
                profileId = UUID.fromString((String) soapObject.getProperty("ProfileId"));
            }
        }
        if (soapObject.hasProperty("TrainingDate"))
        {
            Object obj = soapObject.getProperty("TrainingDate");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("TrainingDate");
                trainingDate =  DateTimeHelper.ConvertFromWebServiceNew(j.toString());
            }else if (obj!= null && obj instanceof String){
                trainingDate =  DateTimeHelper.ConvertFromWebServiceNew((String) soapObject.getProperty("TrainingDate"));
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
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return globalId;
            case 1:
                return allowComments;
            case 2:
                return comment;
            case 3:
                return commentsCount;
            case 4:
                return customerId;
            case 5:
                return lastCommentDate;
            case 6:
                return profileId;
            case 7:
                return trainingDate;

        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 8;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GlobalId";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "AllowComments";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CommentsCount";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CustomerId";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LastCommentDate";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfileId";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TrainingDate";
                break;

        }
        info.namespace= WcfConstans.ServiceNamespace;
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }

    public boolean isMine ()
    {
        return profileId == Helper.emptyGuid() || profileId.equals(ApplicationState.getCurrent().getSessionData().profile.globalId);
    }

}
