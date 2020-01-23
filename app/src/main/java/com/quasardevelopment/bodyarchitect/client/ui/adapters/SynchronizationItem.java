package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import com.quasardevelopment.bodyarchitect.client.model.GPSPointsBag;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GPSTrackerEntryDTO;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 22.07.13
 * Time: 09:52
 * To change this template use File | Settings | File Templates.
 */
public class SynchronizationItem
{
    static public enum ItemType{TrainingDay,GPSCoordinates}

    public enum MergeAction
    {
        InConflictSkip(0),
        UseServer(1),
        UseLocal(2);

        private int code;

        MergeAction(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }

    }

    public enum MergeState
    {
        None(0),
        Processing(1),
        Finished(2),
        Error (3);

        private int code;

        MergeState(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }

        }

    public GPSTrackerEntryDTO GPSEntry;
    public GPSPointsBag GPSBag;

    public MergeState State=MergeState.None;

    public TrainingDayInfo Day;

    public SynchronizationItem(TrainingDayInfo day)
    {
        Day=day;
        Type = ItemType.TrainingDay;
    }

    public SynchronizationItem(TrainingDayInfo dayInfo,GPSTrackerEntryDTO entry,GPSPointsBag bag)
    {
        Day = dayInfo;
        Type = ItemType.GPSCoordinates;
        GPSBag = bag;
        GPSEntry = entry;

    }

    public ItemType Type;

}
