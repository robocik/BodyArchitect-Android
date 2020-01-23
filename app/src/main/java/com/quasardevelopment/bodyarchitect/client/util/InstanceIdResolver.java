package com.quasardevelopment.bodyarchitect.client.util;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingDayDTO;

import java.util.List;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 15.05.13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class InstanceIdResolver
{
    public static TrainingDayDTO FillInstanceId(TrainingDayDTO newDay,TrainingDayDTO oldDay)
    {
        return FillInstanceId(newDay, oldDay,false);
    }
    public static TrainingDayDTO FillInstanceId(TrainingDayDTO newDay,TrainingDayDTO oldDay,boolean fillOldGlobalId)
    {
        newDay.instanceId=oldDay.instanceId;
        if(fillOldGlobalId)
        {
            oldDay.globalId=newDay.globalId;
        }
        for (final EntryObjectDTO newEntry: newDay.objects)
        {
            //first find by GlobalId
            EntryObjectDTO oldEntry= Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return !newEntry.isNew() && item.globalId.equals(newEntry.globalId);
                }
            }, oldDay.objects));
            if(oldEntry!=null)
            {
                fillOldGlobalId(newEntry,oldEntry,fillOldGlobalId);
                continue;
            }
            //next we assume that there is only one type of entry in training day (e.g. one size entry or strength training entry)
            List<EntryObjectDTO> oldEntriesWithSameType= filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return item.getClass().equals(newEntry.getClass());
                }
            }, oldDay.objects);
            if(oldEntriesWithSameType.size()==1 && oldEntriesWithSameType.get(0).isNew())
            {
                //we must check if this single entry is new (GlobalId = empty). If not we cannot attach InstanceId
                fillOldGlobalId(newEntry, oldEntriesWithSameType.get(0),fillOldGlobalId);
                continue;
            }
            //if there are more then one entry with the same type, we check if there is only one with IsNew status in old day. If yes then we can find out instanceid
            List<EntryObjectDTO> oldIsNewEntries = filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return item.isNew();
                }
            }, oldEntriesWithSameType);
            if(oldEntriesWithSameType.size()>1 && oldIsNewEntries.size()==1)
            {
                fillOldGlobalId(newEntry, oldIsNewEntries.get(0),fillOldGlobalId);
                continue;
            }
        }
        return newDay;
    }

    private static void fillOldGlobalId(EntryObjectDTO newEntry, EntryObjectDTO oldEntry,boolean fillOldGlobalId)
    {
        newEntry.instanceId = oldEntry.instanceId;
        if (fillOldGlobalId)
        {
            oldEntry.globalId = newEntry.globalId;
        }
    }
}
