package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GetMessagesCriteria;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MessageDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.PartialRetrievingInfo;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 07.06.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class MessageCache extends ObjectsCacheBase<MessageDTO> {
    @Override
    protected IPagedResult<MessageDTO> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception {
        GetMessagesCriteria criteria = new GetMessagesCriteria();
        criteria.sortAscending = false;
        return service.GetMessages(criteria, pageInfo);
    }

    @Override
    protected UUID GetCurrentHash()
    {
        return ApplicationState.getCurrent().getProfileInfo()!=null?ApplicationState.getCurrent().getProfileInfo().dataInfo.messageHash: Helper.emptyGuid();
    }
}
