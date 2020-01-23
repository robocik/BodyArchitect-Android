package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 26.05.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class SupplementsCache extends ObjectsCacheBase<SuplementDTO>
{
    @Override
    protected IPagedResult<SuplementDTO> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception {
        GetSupplementsParam param = new GetSupplementsParam();
        param.sortOrder= WS_Enums.SearchSortOrder.Name;


        IPagedResult<SuplementDTO> result= service.GetSuplements(param, new PartialRetrievingInfo());

        return result;
    }
}