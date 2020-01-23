package com.quasardevelopment.bodyarchitect.client.util;

import android.accounts.NetworkErrorException;
import android.content.Context;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.*;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ObjectNotFoundException;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.OfflineModeManager;
import com.quasardevelopment.bodyarchitect.client.wcf.WcfConstans;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.DateTime;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 12.04.13
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationState    implements Serializable
{
    transient private boolean isModified=false;
    private static ApplicationState current;
    private SessionData _sessionData;
    private String tempUserName;
    private String tempPassword;
    private ProfileInformationDTO profileInfo;
    public CustomerDTO CurrentViewCustomer;
    public LocalObjectKey CurrentEntryId;
    public ProfileInformationDTO editProfileInfo;
    boolean isTrainingDaysLoading;
    private TrainingDayInfo trainingDayInfo;
    transient BasicHttpBinding_IBodyArchitectAccessService service;
    public boolean Crash;
    private TrainingDaysHolder _currentBrowsingTrainingDays;

    private HashMap<CacheKey, TrainingDaysHolder> myDays = new HashMap<CacheKey, TrainingDaysHolder>();
    transient public boolean isOffline;
    public DateTime timerStartTime;
    //public transient boolean isTimerEnabled;

    public TrainingDayInfo getTrainingDay()
    {
         return trainingDayInfo;
    }

    public void setTrainingDay(TrainingDayInfo value)
    {
        trainingDayInfo = value;
        if (trainingDayInfo == null)
        {
            CurrentEntryId = null;
        }
        isModified=true;
    }

    public boolean isTrainingDaysLoading()
    {
        return isTrainingDaysLoading;
    }

    public void setMyDays(HashMap<CacheKey, TrainingDaysHolder> value)
    {
         myDays=value;
        isModified=true;
    }

    public  HashMap<CacheKey, TrainingDaysHolder> getMyDays()
    {
        return myDays;
    }

    public void ResetCurrents()
    {//this method is invoked mostly at the application startup. In normal case (when application runs without crash) these fields are nulled automatically. But when we have a crash then when
        //application is started again there could be a situation that for example CurrentViewCustomer is set to some customer. In this case when user press Gym in today he will create an entry
        //for this customer and not for himself (bug)
        CurrentEntryId=null;
        CurrentViewCustomer=null;
        setTrainingDay(null);
        timerStartTime=null;
    }

    public TrainingDaysHolder getCurrentBrowsingTrainingDays() {
        if (_currentBrowsingTrainingDays==null)
        {
            return this.GetTrainingDayHolder(CurrentViewCustomer != null ? CurrentViewCustomer.globalId :  null);
        }
        return _currentBrowsingTrainingDays;
    }

    public void setCurrentBrowsingTrainingDays(TrainingDaysHolder _currentBrowsingTrainingDays) {
        this._currentBrowsingTrainingDays = _currentBrowsingTrainingDays;
        isModified=true;
    }

    public TrainingDaysHolder GetTrainingDayHolder(UUID customerId)
    {
        CacheKey key=new CacheKey(getSessionData().profile.globalId,customerId);
        if (!myDays.containsKey(key))
        {
            myDays.put(key,new TrainingDaysHolder(customerId));
        }
        return myDays.get(key);
    }

    public SessionData getSessionData()
    {
        return _sessionData;
    }

    public void setSessionData(SessionData sessionData)
    {
        this._sessionData=sessionData;
        if(_sessionData!=null)
        {
            sessionData.token.language= WcfConstans.getCurrentServiceLanguage();
        }
        isModified=true;
    }

    public String getTempUserName()
    {
         return tempUserName;
    }

    public void setTempUserName(String tempUserName)
    {
        this.tempUserName=tempUserName;
        isModified=true;
    }

    public String getTempPassword()
    {
        return tempPassword;
    }

    public void setTempPassword(String pwd)
    {
        this.tempPassword=pwd;
        isModified=true;
    }

    public static ApplicationState getCurrent ()
    {
//        if(current==null)
//        {
//              String gfdgf="fdgd"+"ggg";
//            gfdgf=gfdgf.toLowerCase();
//        }
//        if(current==null)
//        {
//            current=loadState();
//        }
        return current;
    }

    public static void setCurrent(ApplicationState state)
    {
        if(current!=null)
        {
            current.stopService();
        }
        current=state;
        if(current!=null)
        {
            current.isModified=true;
        }
    }

    public void setProfileInfo(ProfileInformationDTO profileInfo) {
        this.profileInfo = profileInfo;
        isModified=true;
    }

    public ProfileInformationDTO getProfileInfo()
    {
        return profileInfo;
    }

    private void stopService()
    {
        if(service!=null)
        {
            service.cancel();
            service=null;
        }
    }

    public void RetrieveMonthAsync(DateTime monthDate, final TrainingDaysHolder holder,final IWsdl2CodeEvents eventHandler)
    {
        WorkoutDaysSearchCriteria search = new WorkoutDaysSearchCriteria();
        search.userId = holder.getUser() != null ? holder.getUser().globalId : null;
        search.customerId = holder.getCustomerId();
        search.endDate =DateTimeHelper.AddMonths(monthDate,1);
        if (Settings.getNumberOfMonthToRetrieve() > 1)
        {//-1 because we need to subtract months
            monthDate=DateTimeHelper.AddMonths(monthDate,-1 * (Settings.getNumberOfMonthToRetrieve() - 1))  ;
    //        monthDate = monthDate.AddMonths(-1 * (Settings.NumberOfMonthToRetrieve - 1));
        }
        final DateTime monthToRetrieve=   monthDate;
        search.startDate = monthDate;
        PartialRetrievingInfo pageInfo = new PartialRetrievingInfo();
        pageInfo.pageSize = 0;//we want to download all entries in selected period of time
        isTrainingDaysLoading=true;
        try
        {
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
                    PagedResultOfTrainingDayDTO5oAtqRlh result = (PagedResultOfTrainingDayDTO5oAtqRlh)Data;
                    RetrievedDays(monthToRetrieve, Settings.getNumberOfMonthToRetrieve(), result.items, holder);
                    isModified=true;
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
                    isTrainingDaysLoading=false;
                    if(eventHandler!=null)
                    {
                        eventHandler.Wsdl2CodeEndedRequest();
                    }
                    stopService();
                }
            },Settings.getEndPointUrl());
            service.GetTrainingDaysAsync(search,pageInfo);
        }
        catch(NetworkErrorException ex)
        {
            if (ApplicationState.getCurrent().isOffline)
            {
                BAMessageBox.ShowToast(R.string.err_offline_mode);
            }
            else
            {
                BAMessageBox.ShowToast(R.string.err_network_problem);
            }
        }
    //    catch (NetworkException)
    //    {
    //        if (ApplicationState.Current.IsOffline)
    //        {
    //            BAMessageBox.ShowWarning(ApplicationStrings.EntryObjectPageBase_SavedLocallyOnly);
    //        }
    //        else
    //        {
    //            BAMessageBox.ShowError(ApplicationStrings.ErrNoNetwork);
    //        }
    //    }
        catch (Exception ex)
        {
            BAMessageBox.ShowToast(R.string.calendar_view_err_retrieve_entries);
        }
    }

    public void RetrievedDays(DateTime startMonth, int months, Collection<TrainingDayDTO> days, TrainingDaysHolder holder)
    {
        OfflineModeManager manager = new OfflineModeManager(getMyDays(),getSessionData().profile.globalId);
        manager.RetrievedDays(startMonth, months, days, holder);
    }

    public static ApplicationState loadState()
    {
        try {

            FileInputStream fis = MyApplication.getAppContext().openFileInput(Constants.StateFileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            ApplicationState simpleClass = (ApplicationState) is.readObject();
            is.close();
            return simpleClass;
        }
        catch(Exception ex)
        {
            //Log.e(Constants.LogTag, ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public void SaveState(boolean crash)
    {
        try
        {
            Crash = crash;
            FileOutputStream fos = MyApplication.getAppContext().openFileOutput(Constants.StateFileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            isModified=false;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void SaveStateAsync()
    {
        if(isModified || ObjectsReposidory.getCache().shouldBeSaved())
        {
            new Thread(new Runnable() {
                public void run() {
                    if(isModified)
                    {
                        SaveState(false);
                    }
                    ObjectsReposidory.getCache().SaveState();
                }
            })  .start();
        }
    }

    public static void ClearOffline() {
        try
        {
            MyApplication.getAppContext().deleteFile(Constants.StateFileName);
            if(getCurrent()!=null)
            {
                getCurrent().isModified=true;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void clearTrainingDays() {
        OfflineModeManager manager = new OfflineModeManager(myDays, getSessionData().profile.globalId);
        manager.ClearTrainingDays();
        isModified=true;
    }

    public boolean isPremium()
    {
        return !getProfileInfo().licence.currentAccountType.equals(WS_Enums.AccountType.User);
    }

    public boolean isInstructor()
    {
        return getProfileInfo().licence.currentAccountType .equals(WS_Enums.AccountType.User) && !getProfileInfo().licence.currentAccountType.equals( WS_Enums.AccountType.PremiumUser);
    }

    public static void goToOfflineMode() throws ObjectNotFoundException {
        try
        {
            ApplicationState state = loadState();
            if(state==null )
            {
                if (getCurrent() == null)
                {
                    throw new ObjectNotFoundException("You must at lease login once to use offline mode");
                }
                else
                {
                    state=new ApplicationState();
                }
            }

            ObjectsReposidory.loadState();
            if (ApplicationState.getCurrent() != null)
            {

               // state.Cache = ApplicationState.Current.Cache.Copy();
                state.setProfileInfo(getCurrent().getProfileInfo());
                state.setSessionData(getCurrent().getSessionData());
                if(getCurrent().getMyDays()!=null)
                {
                    state.setMyDays(getCurrent().getMyDays());
                }

                state.setTempUserName(getCurrent().getTempUserName());
                state.setTempPassword( getCurrent().getTempPassword());
            }

            state.setCurrentBrowsingTrainingDays( null);
            state.isOffline = true;
            ApplicationState.setCurrent(state);
        }
        catch(Exception ex)
        {
            throw new ObjectNotFoundException("You must at lease login once to use offline mode");
        }

    }

    public ArrayList<TrainingDayInfo> getLocalModified()
    {
        ArrayList<TrainingDayInfo> items = new ArrayList<TrainingDayInfo>();
        for (TrainingDaysHolder item :ApplicationState.getCurrent().getMyDays().values())
        {
            items.addAll(item.GetLocalModifiedEntries()) ;
        }
        return items;
    }

    public void SetModifiedFlag()
    {
        isModified=true;
    }


}
