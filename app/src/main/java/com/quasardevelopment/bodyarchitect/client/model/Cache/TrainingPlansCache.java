package com.quasardevelopment.bodyarchitect.client.model.Cache;

import com.quasardevelopment.bodyarchitect.client.model.IPagedResult;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 02.07.13
 * Time: 07:00
 * To change this template use File | Settings | File Templates.
 */
public class TrainingPlansCache extends ObjectsCacheBase<TrainingPlan>
{
    @Override
    protected IPagedResult<TrainingPlan> RetrieveObjects(BasicHttpBinding_IBodyArchitectAccessService service, PartialRetrievingInfo pageInfo) throws Exception {
        WorkoutPlanSearchCriteria criteria = new WorkoutPlanSearchCriteria();
        criteria.searchGroups.add(WS_Enums.WorkoutPlanSearchCriteriaGroup.Mine);
        criteria.searchGroups.add(WS_Enums.WorkoutPlanSearchCriteriaGroup.Favorites);
        return service.GetWorkoutPlans(criteria, pageInfo);
    }

    @Override
    protected UUID GetCurrentHash()
    {
        return ApplicationState.getCurrent().getProfileInfo().dataInfo.workoutPlanHash;
    }
}
