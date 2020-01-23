package com.quasardevelopment.bodyarchitect.client.model;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GPSTrackerEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingDayDTO;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 16.04.13
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class TrainingDayInfo implements Serializable
{
    private ConcurrentHashMap<LocalObjectKey, GPSPointsBag> _gpsCoordinates;
    boolean isModified;
    private boolean isConflict;
    DateTime dateTime;
    TrainingDayDTO trainingDay;

    public TrainingDayInfo()
    {
        //GPSCoordinates = new Dictionary<Guid, GPSPointsBag>();
        _gpsCoordinates = new ConcurrentHashMap<LocalObjectKey, GPSPointsBag>();
    }
    public TrainingDayInfo(TrainingDayDTO day)
    {
        setDateTime(DateTime.now());
        trainingDay = day;
        _gpsCoordinates = new ConcurrentHashMap<LocalObjectKey, GPSPointsBag>();
        //OriginalVersion = day.Version;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TrainingDayDTO getTrainingDay() {
        return trainingDay;
    }

    public void setTrainingDay(TrainingDayDTO trainingDay) {
        this.trainingDay = trainingDay;
    }


    public boolean isModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }

    public boolean getIsConflict() {
        return isConflict;
    }

    public void setIsConflict(boolean isConflict) {
        this.isConflict = isConflict;
    }

     public ConcurrentHashMap<LocalObjectKey, GPSPointsBag> getGPSCoordinates()
     {
         return  _gpsCoordinates;
     }
    //key is GPSTrackerEntryId
    public void setGPSCoordinates(ConcurrentHashMap<LocalObjectKey, GPSPointsBag> value)
    {
        _gpsCoordinates=value;
    }

    public void SetGpsCoordinates(GPSTrackerEntryDTO gpsEntry, Collection<GPSPoint> points, boolean isSaved)
    {
        if (gpsEntry.isNew())
        {
            _gpsCoordinates.put(new LocalObjectKey(gpsEntry.instanceId, KeyType.InstanceId), new GPSPointsBag(points, isSaved));
        }
        else
        {
            //first check if there is old element which using InstanceId for this entry. if yes then remove it
            if (_gpsCoordinates.containsKey(new LocalObjectKey(gpsEntry.instanceId, KeyType.InstanceId)))
            {
                _gpsCoordinates.remove(new LocalObjectKey(gpsEntry.instanceId, KeyType.InstanceId));
            }
            _gpsCoordinates.put(new LocalObjectKey(gpsEntry.globalId, KeyType.GlobalId), new GPSPointsBag(points, isSaved));
        }
    }

    public GPSPointsBag getGpsCoordinates(final GPSTrackerEntryDTO gpsEntry)
    {
        LocalObjectKey globalKey=new LocalObjectKey(gpsEntry.globalId,KeyType.GlobalId);
        LocalObjectKey isntanceKey=new LocalObjectKey(gpsEntry.instanceId,KeyType.InstanceId);
        if(getGPSCoordinates().containsKey(globalKey))
        {
            return _gpsCoordinates.get(globalKey);
        }
        if(getGPSCoordinates().containsKey(isntanceKey))
        {
            return _gpsCoordinates.get(isntanceKey);
        }
        return null;
    }

    public void CleanUpGpsCoordinates()
    {
        List<EntryObjectDTO> gpsEntries= filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.getClass().equals(GPSTrackerEntryDTO.class);
            }
        },trainingDay.objects);

        //find all gps coordintaes without GPSTrackerEntry and delete them
        List<LocalObjectKey> keysToRemove = new ArrayList<LocalObjectKey>();
        for (final LocalObjectKey gpsCoordinate : _gpsCoordinates.keySet())
        {
            GPSTrackerEntryDTO existingEntry= (GPSTrackerEntryDTO) Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                public boolean apply(EntryObjectDTO item) {
                    return item.globalId.equals(gpsCoordinate.id) || item.instanceId.equals(gpsCoordinate.id);
                }
            }, trainingDay.objects));


            if (existingEntry == null)
            {
                keysToRemove.add(gpsCoordinate);
            }
        }
        for (LocalObjectKey localObjectKey : keysToRemove)
        {
            _gpsCoordinates.remove(localObjectKey);
        }
        //keysToRemove.clear();

        for (Iterator<LocalObjectKey> i = _gpsCoordinates.keySet().iterator(); i.hasNext();) {
            final LocalObjectKey key= i.next();
            if (key.getKeyType().equals(KeyType.InstanceId))
            {
                GPSTrackerEntryDTO entry= (GPSTrackerEntryDTO) Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                    public boolean apply(EntryObjectDTO item) {
                        return  item.instanceId.equals(key.id);
                    }
                }, gpsEntries));



                if (entry!=null && !entry.isNew())
                {
                    GPSPointsBag bag=_gpsCoordinates.get(key);
                    LocalObjectKey newKey= new LocalObjectKey(entry.globalId,KeyType.GlobalId);
                    _gpsCoordinates.remove(key);
                    _gpsCoordinates.put(newKey,bag);
//                    key.id = entry.globalId;
//                    key.setKeyType(KeyType.GlobalId);
                }
            }
        }

        //now check if we can use GlobalId instead of InstanceId
//        for (final LocalObjectKey key : _gpsCoordinates.keySet())
//        {
//            if (key.getKeyType().equals(KeyType.InstanceId))
//            {
//                GPSTrackerEntryDTO entry= (GPSTrackerEntryDTO) Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
//                    public boolean apply(EntryObjectDTO item) {
//                        return  item.instanceId.equals(key.id);
//                    }
//                }, gpsEntries));
//
//                if (entry!=null && !entry.isNew())
//                {
//                    GPSPointsBag bag=_gpsCoordinates.get(key);
//                    LocalObjectKey newKey= new LocalObjectKey(entry.globalId,KeyType.GlobalId);
//                    _gpsCoordinates.remove(key);
//                    _gpsCoordinates.put(newKey,bag);
////                    key.id = entry.globalId;
////                    key.setKeyType(KeyType.GlobalId);
//                }
//            }
//        }
    }





    //key is GPSTrackerEntryId
    //public Dictionary<Guid, GPSPointsBag> GPSCoordinates { get; set; }

    public void Update(final EntryObjectDTO entryObject)
    {
        EntryObjectDTO entry= Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.globalId.equals(entryObject.globalId);
            }
        }, trainingDay.objects));

        if (entry != null)
        {
            trainingDay.objects.remove(entry);
            trainingDay.objects.add(entryObject);
            entryObject.trainingDay = trainingDay;
        }

    }
}
