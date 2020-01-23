package com.quasardevelopment.bodyarchitect.client.model;

import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.ReferencesManager;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;
import org.joda.time.DateTime;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.05.13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class PagedResultOfBase <V extends Vector<T>,T extends BAGlobalObject>    implements KvmSerializable,IPagedResult<T> {

    public int allItemsCount;
    public int pageIndex;
    public V items;
    public DateTime retrievedDateTime;

    public PagedResultOfBase() throws Exception {
        items=createItemVector(null,null);
    }


    public Vector<T> getItems()
    {
        return items;
    }

    public PagedResultOfBase(SoapObject soapObject,ReferencesManager referencesTable) throws Exception
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("AllItemsCount"))
        {
            Object obj = soapObject.getProperty("AllItemsCount");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("AllItemsCount");
                allItemsCount = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                allItemsCount = (Integer) soapObject.getProperty("AllItemsCount");
            }
        }
        if (soapObject.hasProperty("Items"))
        {
            SoapObject j = (SoapObject)soapObject.getProperty("Items");
            items = createItemVector(j,referencesTable);
        }
        if (soapObject.hasProperty("PageIndex"))
        {
            Object obj = soapObject.getProperty("PageIndex");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("PageIndex");
                pageIndex = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                pageIndex = (Integer) soapObject.getProperty("PageIndex");
            }
        }
        if (soapObject.hasProperty("RetrievedDateTime"))
        {
            Object obj = soapObject.getProperty("RetrievedDateTime");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) soapObject.getProperty("RetrievedDateTime");
                retrievedDateTime = DateTimeHelper.ConvertFromWebServiceNew(j.toString());
            }else if (obj!= null && obj instanceof String){
                retrievedDateTime = DateTimeHelper.ConvertFromWebServiceNew((String) soapObject.getProperty("RetrievedDateTime"));
            }
        }
    }

    protected abstract V createItemVector(SoapObject soapObject,ReferencesManager referencesTable) throws Exception;

    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return allItemsCount;
            case 1:
                return items;
            case 2:
                return pageIndex;
            case 3:
                return retrievedDateTime;
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
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AllItemsCount";
                break;
            case 1:
                info.type = PropertyInfo.VECTOR_CLASS;
                info.name = "Items";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PageIndex";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RetrievedDateTime";
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
    }

}
