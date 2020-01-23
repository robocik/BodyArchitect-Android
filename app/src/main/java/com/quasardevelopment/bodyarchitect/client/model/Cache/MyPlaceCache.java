package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MyPlaceLightDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MyPlaceSearchCriteria;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.PartialRetrievingInfo;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 24.05.13
 * Time: 08:50
 * To change this template use File | Settings | File Templates.
 */
public class MyPlaceCache extends ObjectsCacheBase<MyPlaceLightDTO>
{
    @Override
    protected IPagedResult<MyPlaceLightDTO> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception {
        MyPlaceSearchCriteria criteria = new MyPlaceSearchCriteria();
        return service.GetMyPlaces(criteria, pageInfo);
    }

    @Override
    protected UUID GetCurrentHash()
    {
        return ApplicationState.getCurrent().getProfileInfo().dataInfo.myPlaceHash;
    }
}