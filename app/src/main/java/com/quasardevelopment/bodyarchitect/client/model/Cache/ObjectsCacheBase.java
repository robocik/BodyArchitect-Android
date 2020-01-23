package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Functions;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.PartialRetrievingInfo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.05.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public abstract class ObjectsCacheBase<T extends BAGlobalObject>    implements Serializable
{
    private HashMap<UUID, T> dictExercises;

    public DateTime Date ;

    public UUID Hash;

    public transient  boolean isModified;

    public boolean isLoaded()
    {
        return dictExercises != null && !shouldRefresh();
    }

    private boolean shouldRefresh()
    {
        return shouldRefreshImplementation(Date, dictExercises);
    }

    protected UUID GetCurrentHash()
    {
        return null;
    }

    boolean shouldRefreshImplementation(DateTime date, Object dict)
    {
        if (ApplicationState.getCurrent().isOffline)
        {
            return false;
        }

        if (date == null || dict == null)
        {
            return true;
        }

        if (Hash!=null && !Hash.equals(GetCurrentHash()))
        {
            return true;
        }
        return false;
    }

    public void Clear()
    {
        Date = null;
        dictExercises = null;
    }

    public void Refresh(BasicHttpBinding_IBodyArchitectAccessService service) throws Exception {
        Clear();
        Load(service);
    }

    public HashMap<UUID, T> getItems ()
    {
        return dictExercises;
    }

    public T GetItem(UUID id)
    {
        if (dictExercises.containsKey(id))
        {
            return dictExercises.get(id);
        }
        return null;
    }

    public void Remove(UUID globalId)
    {
        dictExercises.remove(globalId);
        isModified=true;
    }

    protected abstract IPagedResult<T> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception;

    public void Load(BasicHttpBinding_IBodyArchitectAccessService service) throws Exception {
        PartialRetrievingInfo pageInfo = new PartialRetrievingInfo();
        pageInfo.pageSize = 0;


        IPagedResult<T> res= null;
        res = RetrieveObjects(service,pageInfo);
        Date = DateTime.now(DateTimeZone.UTC);
        Hash = GetCurrentHash();
        isModified=true;

        dictExercises= Helper.ToDictionary(res.getItems());
        isModified=true;
    }
    BasicHttpBinding_IBodyArchitectAccessService service=null;

    public void cancel()
    {
        if(service!=null)
        {
            service.cancel();
            service=null;
        }
    }

    public void LoadAsync(final IWsdl2CodeEvents eventHandler)
    {
        if (ApplicationState.getCurrent()!=null && !ApplicationState.getCurrent().isOffline && (dictExercises == null || shouldRefresh()))
        {
//            service = new BasicHttpBinding_IBodyArchitectAccessService(eventHandler, Settings.getEndPointUrl());
//
//            service.executeAsync(new Functions.IFunc<HashMap<UUID, T>>() {
//                @Override
//                public HashMap<UUID, T> Func() throws Exception {
//                    Load(service);
//                    return dictExercises;
//                }
//            },"Test",false);

            service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
                @Override
                public void Wsdl2CodeStartedRequest() {
                    if(eventHandler!=null)
                    {
                        eventHandler.Wsdl2CodeStartedRequest();
                    }
                }

                @Override
                public void Wsdl2CodeFinished(String methodName, Object Data) {
                    if(eventHandler!=null)
                    {
                        eventHandler.Wsdl2CodeFinished(methodName,Data);
                    }
                }

                @Override
                public void Wsdl2CodeFinishedWithException(Exception ex) {
                    if(eventHandler!=null)
                    {
                        eventHandler.Wsdl2CodeFinishedWithException(ex);
                    }
                }

                @Override
                public void Wsdl2CodeEndedRequest() {
                    service=null;
                    if(eventHandler!=null)
                    {
                        eventHandler.Wsdl2CodeEndedRequest();
                    }
                }
            }, Settings.getEndPointUrl());

            service.executeAsync(new Functions.IFunc<HashMap<UUID, T>>() {
                @Override
                public HashMap<UUID, T> Func() throws Exception {
                    Load(service);
                    return dictExercises;
                }
            },"Test",false);
        }

    }

    public void put(T item) {
        dictExercises.put(item.globalId,item);
        isModified=true;
    }
}
