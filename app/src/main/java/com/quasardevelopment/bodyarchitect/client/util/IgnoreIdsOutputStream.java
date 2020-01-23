package com.quasardevelopment.bodyarchitect.client.util;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ExerciseLightDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MyPlaceLightDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SuplementDTO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 23.05.13
 * Time: 09:40
 * To change this template use File | Settings | File Templates.
 */


public class IgnoreIdsOutputStream extends ObjectOutputStream {
    boolean clearInstanceId;

    IgnoreIdsOutputStream(OutputStream out,boolean clearInstanceId) throws IOException {

        super(out);
        this.clearInstanceId=clearInstanceId;
        enableReplaceObject(true);
    }

    protected Object replaceObject(Object obj) throws IOException {
        if(obj instanceof ExerciseLightDTO || obj instanceof ExerciseLightDTO || obj instanceof MyPlaceLightDTO || obj instanceof SuplementDTO)
        {//for attached exercises and other fields we shouldn't serialize or clear id's
             return obj;
        }
        else if(obj instanceof BAGlobalObject )
        {
            BAGlobalObject item=(BAGlobalObject)super.replaceObject(obj);
            item.globalId=Helper.emptyGuid();
            item.instanceId=clearInstanceId?Helper.emptyGuid():UUID.randomUUID();
            return item;
        }
        else
            return super.replaceObject(obj);
    }
}
