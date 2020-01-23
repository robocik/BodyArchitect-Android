package com.quasardevelopment.bodyarchitect.client.wcf;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingDayDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WcfInterfaces;
import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class ReferencesManager
{
    HashMap<String,Object> referencesTable = new HashMap<String, Object>();

    public void Add(String id, WcfInterfaces.IReferenceObject obj)
    {
        if(!referencesTable.containsKey(id))
        {
            referencesTable.put(id,obj);
        }
    }

    public Object get(String refId) {
        return referencesTable.get(refId);
    }

    public Object get(SoapObject soap,Class cl)
    {
        if(soap==null)
        {
            return null;
        }
        try
        {
            if(soap.hasAttribute("Ref"))
            {
                String ref=(String)soap.getAttribute("Ref");
                return referencesTable.get(ref);
            }
            else
            {
                if(soap.hasAttribute("type"))
                {
                    String type=(String)soap.getAttribute("type");
                    int index=type.indexOf(":");
                    if(index>-1)
                    {
                        type=type.substring(index+1,type.length()-(index-1));
                    }
                    String fullType=String.format("%s.%s",TrainingDayDTO.class.getPackage().getName(),type);
                    cl=Class.forName(fullType);

                }
                Constructor ctor = cl.getConstructor(SoapObject.class,ReferencesManager.class);
                return ctor.newInstance(soap,this);
            }
        }
        catch (Exception ex)
        {
               ex.printStackTrace();
            return null;
        }

    }
}
