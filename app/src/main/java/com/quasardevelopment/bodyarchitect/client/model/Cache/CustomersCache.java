package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerSearchCriteria;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.PartialRetrievingInfo;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 06.07.13
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class CustomersCache extends ObjectsCacheBase<CustomerDTO>
{
    @Override
    protected IPagedResult<CustomerDTO> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        return service.GetCustomers(criteria, pageInfo);
    }

    @Override
    protected UUID GetCurrentHash()
    {
        return ApplicationState.getCurrent().getProfileInfo().dataInfo.customerHash;
    }
}
