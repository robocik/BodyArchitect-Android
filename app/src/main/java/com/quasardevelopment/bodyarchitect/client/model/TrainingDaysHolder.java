package com.quasardevelopment.bodyarchitect.client.model;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingDayDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserDTO;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import static ch.lambdaj.Lambda.filter;

public class TrainingDaysHolder    implements Serializable
{
    HashMap<DateTime, TrainingDayInfo> days = new HashMap<DateTime, TrainingDayInfo>();
    private ArrayList<DateTime> retrievedMonths = new ArrayList<DateTime>();
    UserDTO user;
    UUID customerId;

    public TrainingDaysHolder()
    {
    }

    public TrainingDaysHolder(UserDTO SelectedUser)
    {
        this.setUser( SelectedUser);
    }

    public TrainingDaysHolder(UUID customerId)
    {
        this.setCustomerId(customerId);
        setUser(ApplicationState.getCurrent().getSessionData().profile);
    }

    public HashMap<DateTime, TrainingDayInfo> getTrainingDays() {
        return days;
    }

    public void setTrainingDays(HashMap<DateTime, TrainingDayInfo> days) {
        this.days = days;
    }

    public UserDTO getUser()
    {
        return user;
    }

    public void setUser(UserDTO user)
    {
        this.user=user;
    }


    public ArrayList<DateTime> getRetrievedMonths() {
        return retrievedMonths;
    }

    public void setRetrievedMonths(ArrayList<DateTime> retrievedMonths) {
        this.retrievedMonths = retrievedMonths;
    }

    public UUID getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId(UUID customerId)
    {
        this.customerId=customerId;
    }

    public boolean isMine()
    {
        return getUser().globalId.equals(ApplicationState.getCurrent().getSessionData().profile.globalId);
    }

    public TrainingDayDTO GetFirstLoadedEntry()
    {
        //todo:need to be implemented
//        var newDay = (from day in TrainingDays.Values
//        orderby day.TrainingDay.TrainingDate ascending
//        select day).FirstOrDefault();
//        if (newDay != null)
//        {
//            return newDay.TrainingDay;
//        }
//        return null;
        return null;
    }

    public TrainingDayDTO GetLastLoadedEntry()
    {
        //todo:need to be implemented
//        var newDay = (from day in TrainingDays.Values
//        orderby day.TrainingDay.TrainingDate descending
//        select day).FirstOrDefault();
//        if (newDay!=null)
//        {
//            return newDay.TrainingDay;
//        }
//        return null;
        return null;
    }

    public TrainingDayInfo SetTrainingDay(TrainingDayDTO day)
    {
        TrainingDayInfo info;
        HashMap<DateTime, TrainingDayInfo> days=getTrainingDays();
        if (days.containsKey(day.trainingDate))
        {
            info = days.get(day.trainingDate);
            info.setTrainingDay(day);
        }
        else
        {
            info = new TrainingDayInfo(day);
            days.put(day.trainingDate, info);
        }
        return info;
    }


    public boolean isMonthLoaded(DateTime month)
    {
        return getRetrievedMonths().contains(month);
    }

    public Collection<TrainingDayInfo> GetLocalModifiedEntries()
    {
        return  filter(new Predicate<TrainingDayInfo>() {
            public boolean apply(TrainingDayInfo item) {
                return item.isModified;
            }
        }, getTrainingDays().values());
    }

    public boolean isSaved(final EntryObjectDTO entry)
    {
        if(entry==null)
        {
            return false;
        }
        boolean isAlreadySavedInLocal=false;
        if(entry.isNew())
        {
            isAlreadySavedInLocal=ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(entry.trainingDay.trainingDate);
            if(isAlreadySavedInLocal)
            {
                TrainingDayInfo dayInfo=ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(entry.trainingDay.trainingDate);
                EntryObjectDTO entrySavedInLocal= Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                    public boolean apply(EntryObjectDTO item) {
                        return item.instanceId.equals(entry.instanceId);
                    }
                }, dayInfo.getTrainingDay().objects));
                isAlreadySavedInLocal=entrySavedInLocal!=null;
            }
        }
        boolean emptyEntry = entry.isNew() && !isAlreadySavedInLocal ;
        return !emptyEntry;
    }
}