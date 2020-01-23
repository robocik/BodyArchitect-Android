package com.quasardevelopment.bodyarchitect.client.ui;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.model.CacheKey;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDaysHolder;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.Functions;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingDayDTO;
import org.joda.time.DateTime;

import java.util.*;

import static ch.lambdaj.Lambda.filter;


public class OfflineModeManager
{
    public enum ModificationType
    {
        EntryModified,
        EntryOnServerButNotOnClient
    }

    private HashMap<CacheKey, TrainingDaysHolder> state;
    private UUID myProfileId;

    public OfflineModeManager(HashMap<CacheKey, TrainingDaysHolder> state,UUID myProfileId)
    {
        this.state = state;
        this.myProfileId = myProfileId;
    }

    public boolean RetrievedDays(DateTime startMonth, int months, Collection<TrainingDayDTO> days,TrainingDaysHolder current)
    {
        boolean withoutProblems = true;

        for(TrainingDayDTO dayDto : days ){
            if (current.getTrainingDays().containsKey(dayDto.trainingDate))
            {
                TrainingDayInfo dayInfo = current.getTrainingDays().get(dayDto.trainingDate);
                if (!dayInfo.isModified())
                {
                    current.getTrainingDays().put(dayDto.trainingDate, new TrainingDayInfo(dayDto));
                }
                else
                {
                    //int version = dayDto.Version;
                    //dayDto.Version = dayInfo.OriginalVersion;
                    dayInfo.getTrainingDay().globalId = dayDto.globalId;
                    //version is changed but we must check if the content is different. If not we can merge
                    if (Helper.isModified(dayDto,dayInfo.getTrainingDay()))
                    {//still problem with InstanceId in child objects
                        dayInfo.setIsConflict(true);
                        withoutProblems = false;
                    }
                    else
                    {
                        TrainingDayDTO day=current.getTrainingDays().get(dayDto.trainingDate).getTrainingDay();
                        setVersion(day, dayDto);

                        day.globalId = dayDto.globalId;
                    }

                }
            }
            else
            {
                TrainingDayInfo info = new TrainingDayInfo(dayDto);
                current.getTrainingDays().put(dayDto.trainingDate, info);
            }
        }

        for (int i = 0; i < months; i++)
        {
            DateTime tempDate = DateTimeHelper.AddMonths(startMonth,i);
            if (!current.getRetrievedMonths().contains(tempDate))
            {
                current.getRetrievedMonths().add(tempDate);
            }
        }
        return withoutProblems;
    }

    public void ClearTrainingDays()
    {
        for(TrainingDaysHolder daysHolder : state.values() ){
            daysHolder.getRetrievedMonths().clear();
            ArrayList<DateTime> datesToRemove = new ArrayList<DateTime>();


            for(Map.Entry<DateTime, TrainingDayInfo> entry : daysHolder.getTrainingDays().entrySet()) {
                DateTime key = entry.getKey();
                TrainingDayInfo value = entry.getValue();

                if (!value.isModified())
                {
                    datesToRemove.add(key);
                }
            }

            for(DateTime dateTime : datesToRemove )
            {
                daysHolder.getTrainingDays().remove(dateTime);
            }
        }

    }

    void setVersion(TrainingDayDTO target,final TrainingDayDTO source)
    {
        if (source==null)
        {
            for(EntryObjectDTO dto : target.objects )
            {
                dto.version = 0;
                dto.globalId = Helper.emptyGuid();
            }
            return;
        }
        for (int i = 0; i < source.objects.size(); i++)
        {
            final int index=i;
//            EntryObjectDTO entry=from(target.objects).where("globalId", eq(source.objects.get(i).globalId)).first();
            EntryObjectDTO entry= Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return item.globalId.equals(source.objects.get(index).globalId);
                }
            }, target.objects));

//            var entry = target.Objects.Where(x => x.GlobalId == source.Objects[i].GlobalId).SingleOrDefault();
            if (entry != null)
            {
                entry.version = source.objects.get(i).version;
            }
        }
    }
    public void MergeNew(TrainingDayDTO fromServer, ApplicationState appState, boolean updateLocalCache, Functions.IFunc1<ModificationType,Boolean> useServerQuestion)
    {
        TrainingDayDTO day=appState.getTrainingDay().getTrainingDay();
        UUID customerId = day.customerId;
        TrainingDaysHolder holder = state.get(new CacheKey(myProfileId,customerId ));

        if (fromServer != null)
        {

            if (Helper.isModified(fromServer, day))
            {
                for (final EntryObjectDTO serverEntry : fromServer.objects)
                {

                    EntryObjectDTO localEntry= Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                        public boolean apply(EntryObjectDTO item) {
                            return item.globalId.equals(serverEntry.globalId);
                        }
                    }, day.objects));

                    if(localEntry==null)
                    {
//                        if (useServerQuestion.Func(ModificationType.EntryOnServerButNotOnClient))
//                        {
//                            day.objects.add(Helper.Copy(serverEntry));
//                        }
                        day.objects.add(Helper.Copy(serverEntry));
                        day.globalId=fromServer.globalId;
                        //todo:here we "create" two entries so mabye we should inform the user about this
                    }
                    else if(Helper.isModified(serverEntry, localEntry))
                    {
                        //the same entry has been modified
                        if (useServerQuestion.Func(ModificationType.EntryModified))
                        {
                            //user wants to server version so replace it
                            day.objects.remove(localEntry);
                            day.objects.add(serverEntry);
                        }
                        else
                        {
                            localEntry.version = serverEntry.version;
                        }
                    }
                    else
                    {
                        localEntry.version = serverEntry.version;
                    }
                }

                //now we check what entries we remove on the client
                for (int index = day.objects.size() - 1; index >= 0; index--)
                {
                    final EntryObjectDTO localEntry = day.objects.get(index);
                    if (!localEntry.isNew())
                    {
//this is saved entry (not newly added) so we must check if it exists still on the server
                        //EntryObjectDTO serverEntry=from(fromServer.objects).where("globalId",eq(localEntry.globalId)).first();

                        EntryObjectDTO serverEntry= Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                            public boolean apply(EntryObjectDTO item) {
                                return item.globalId.equals(localEntry.globalId);
                            }
                        }, fromServer.objects));

                        if (serverEntry == null)
                        {
//this entry doesn't exist on the server. we must remove it from the client
                            day.objects.remove(localEntry);
                        }
                    }
                }
            }
            else
            {
                //appState.TrainingDay.Version = fromServer.Version;
                setVersion(day, fromServer);
                if (updateLocalCache)
                {
                    holder.getTrainingDays().get(fromServer.trainingDate).setIsModified(false);
                    //state.TrainingDays[fromServer.TrainingDate].TrainingDay.Version = fromServer.Version;
                    setVersion(holder.getTrainingDays().get(fromServer.trainingDate).getTrainingDay(), fromServer);
                }
            }
            if (updateLocalCache)
            {
                holder.getTrainingDays().get(fromServer.trainingDate).setIsConflict(false);
            }
        }
        else
        {
            //if on the server entry is deleted then we must set Id to 0 (newly created object)
            //TODO: teraz gdy na serwerze wpis został usunięty po prostu robimy tak ze dalej user go ma. mozna też wyswietlic
            //pytanie że wpis został usunięty i niech user zadecyduje co z tym fantem zrobić
            day.globalId = Helper.emptyGuid();
            if (updateLocalCache)
            {
                holder.getTrainingDays().remove(day.trainingDate);
            }
            setVersion(day, null);
            //appState.TrainingDay.Version = 0;
        }
    }

}
