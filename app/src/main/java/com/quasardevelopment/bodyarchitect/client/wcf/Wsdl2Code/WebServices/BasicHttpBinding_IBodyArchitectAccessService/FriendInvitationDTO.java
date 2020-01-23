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

import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.InvitationType;
import org.joda.time.DateTime;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

public class FriendInvitationDTO implements KvmSerializable,Serializable {
    
    public DateTime createdDateTime;
    public InvitationType invitationType;
    public UserDTO invited;
    public UserDTO inviter;
    public String message;
    
    public FriendInvitationDTO(){}
    
    public FriendInvitationDTO(SoapObject soapObject,ReferencesManager referencesTable) throws Exception
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("CreatedDateTime"))
        {
            Object obj = soapObject.getProperty("CreatedDateTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CreatedDateTime");
                createdDateTime = DateTimeHelper.ConvertFromWebServiceNew(j.toString());
            }else if (obj!= null && obj instanceof String){
                createdDateTime = DateTimeHelper.ConvertFromWebServiceNew((String) soapObject.getProperty("CreatedDateTime"));
            }
        }
        if (soapObject.hasProperty("InvitationType"))
        {
            Object obj = soapObject.getProperty("InvitationType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("InvitationType");
                invitationType = InvitationType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("Invited"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Invited");
            invited = (UserDTO)referencesTable.get(j,UserDTO.class);
            
        }
        if (soapObject.hasProperty("Inviter"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Inviter");
            inviter = (UserDTO)referencesTable.get(j,UserDTO.class);
            
        }
        if (soapObject.hasProperty("Message"))
        {
            Object obj = soapObject.getProperty("Message");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("Message");
                message = j.toString();
            }else if (obj!= null && obj instanceof String){
                message = (String) soapObject.getProperty("Message");
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return createdDateTime;
            case 1:
                return invitationType.toString();
            case 2:
                return invited;
            case 3:
                return inviter;
            case 4:
                return message;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 5;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CreatedDateTime";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "InvitationType";
                break;
            case 2:
                info.type = UserDTO.class;
                info.name = "Invited";
                break;
            case 3:
                info.type = UserDTO.class;
                info.name = "Inviter";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Message";
                break;
        }
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}