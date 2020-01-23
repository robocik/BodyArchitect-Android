package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.05.13
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class ExerciseCache extends ObjectsCacheBase<ExerciseDTO>
{
    @Override
    protected IPagedResult<ExerciseDTO> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception {
        ExerciseSearchCriteria criteria = new ExerciseSearchCriteria();
        criteria.searchGroups.add(WS_Enums.ExerciseSearchCriteriaGroup.Mine);
        criteria.searchGroups.add(WS_Enums.ExerciseSearchCriteriaGroup.Global);
        return service.GetExercises(criteria, pageInfo);
    }

    @Override
    protected UUID GetCurrentHash()
    {
        return ApplicationState.getCurrent().getProfileInfo().dataInfo.exerciseHash;
    }
}
