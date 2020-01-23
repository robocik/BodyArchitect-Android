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

import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums.AccountType;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

public class LicenceInfoDTO implements KvmSerializable,Serializable {
    
    public AccountType accountType;
    public int bAPoints;
    public AccountType currentAccountType;
    public PaymentsHolder payments;
    
    public LicenceInfoDTO(){}
    
    public LicenceInfoDTO(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("AccountType"))
        {
            Object obj = soapObject.getProperty("AccountType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("AccountType");
                accountType = AccountType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("BAPoints"))
        {
            Object obj = soapObject.getProperty("BAPoints");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("BAPoints");
                bAPoints = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                bAPoints = (Integer) soapObject.getProperty("BAPoints");
            }
        }
        if (soapObject.hasProperty("CurrentAccountType"))
        {
            Object obj = soapObject.getProperty("CurrentAccountType");
            if (obj!= null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("CurrentAccountType");
                currentAccountType = AccountType.fromString(j.toString());
            }
        }
        if (soapObject.hasProperty("Payments"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Payments");
            payments =  new PaymentsHolder (j);
            
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return accountType.toString();
            case 1:
                return bAPoints;
            case 2:
                return currentAccountType.toString();
            case 3:
                return payments;
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
                info.name = "AccountType";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "BAPoints";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CurrentAccountType";
                break;
            case 3:
                info.type = PaymentsHolder.class;
                info.name = "Payments";
                break;
        }
        info.namespace= WcfConstans.ServiceNamespace;
    }

    

    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
