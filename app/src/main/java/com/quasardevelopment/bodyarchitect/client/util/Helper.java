package com.quasardevelopment.bodyarchitect.client.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.TypedValue;
import android.widget.ListView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.KeyType;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.viewpagerindicator.TitlePageIndicator;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.selectUnique;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 17.04.13
 * Time: 08:29
 * To change this template use File | Settings | File Templates.
 */
public class Helper {

    public final static boolean isFeatureAvailable(Context context, String feature) {
        final PackageManager packageManager = context.getPackageManager();
        final FeatureInfo[] featuresList = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo f : featuresList) {
            if (f.name != null && f.name.equals(feature)) {
                return true;
            }
        }

        return false;
    }

    public final static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    public static <T extends BAGlobalObject> HashMap<UUID, T> ToDictionary(Vector<T> list) {
        HashMap<UUID, T> map = new HashMap<UUID, T>(list.size());
        for (T obj : list) {
            map.put(obj.globalId, obj);
        }
        return map;
    }

    public static void SetSelection(final ListView list, final int position) {
        list.post(new Runnable() {
            @Override
            public void run() {
                list.requestFocusFromTouch();
                list.setSelection(position);
                list.requestFocus();
            }
        });

    }


    public static boolean isModified(Object objA, Object objB) {
        try
        {
            //first make a local copy because without this ids of original object will be cleared
            objA=Copy(objA);
            objB=Copy(objB);
            return !Arrays.equals(serializeToArray(objA,true,true),serializeToArray(objB,true,true));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    static byte[] serializeToArray(Object source,boolean ignoreIds,boolean clearInstanceId) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream out;
        if(ignoreIds)
        {
             out =new IgnoreIdsOutputStream(b,clearInstanceId);
        }
        else
        {
             out=new ObjectOutputStream(b);
        }

        out.writeObject(source);
        out.close();
        return b.toByteArray();
    }


    public static UUID emptyGuid() {
        return new UUID(0, 0);
    }

    //https://weblogs.java.net/blog/emcmanus/archive/2007/04/cloning_java_ob.html
    public static <T> T Copy(T source)  {
         return Copy(source,false);
    }

    public static <T> T Copy(T source,boolean ignoreIds)  {
        try
        {
            ByteArrayInputStream bi = new ByteArrayInputStream(serializeToArray(source,false,false));
            ObjectInputStream in =new ObjectInputStream(bi);
            Object no = in.readObject();
            if(ignoreIds)
            {//ten if jest rozwiązaniem problemu. gdyby pierwsza iteracja miała czyścic globalId to w tym momencie czysciła by tez source a tego nie chcemy
                //także najpierw robimy kopię zeby nie działać na source a potem czyszczenie
                bi = new ByteArrayInputStream(serializeToArray(no,true,false));
                in =new ObjectInputStream(bi);
                no = in.readObject();
            }
            return (T)no;
        } catch (Exception ex)  {
            ex.printStackTrace();
        }
        return null;
    }

    public static Double FromDisplayDistance(Double miles)
    {
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs)
        {
            miles = miles * 1.609344;  //to km
        }
        return miles;
    }
    public static Double ToDisplayWeight(Double weight) {
        if (ApplicationState.getCurrent().getProfileInfo().settings.weightType == WS_Enums.WeightType.Pounds) {
            weight = weight / 0.454;
        }
        return weight;
    }

    public static Double ToDisplayDistance(Double m) {

        double km = m/1000;//to kilometers
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs)
        {
            km = km * 0.621371;
        }
        return km;
    }

    public static Double ToDisplaySpeed(Double mPerSec) {

        double kmPerHour = mPerSec*3.6;
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs)
        {
            kmPerHour = kmPerHour*0.621371;
        }
        return kmPerHour;
    }

    public static Double ToDisplayPace(Double mPerSec)
    {
        return WilksFormula.SpeedToPace(mPerSec,ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs);
    }

    public static String ToDisplaySpeedText(Double mPerSec) {

        return ToDisplayText(ToDisplaySpeed(mPerSec));
    }

    public static String ToDisplayDistanceText(Double weight) {

        return ToDisplayText(ToDisplayDistance(weight));
    }

//    public static String NormalizeDouble(String doubleValue) {
//        if(doubleValue!=null)
//        {
//            DecimalFormat df = new DecimalFormat("#.##");
//
//            return df.format(m);
//        }
//        return "";
//
//    }
    public static Double parseDouble(String text)
    {
        text=text.replace(',','.') ;
        return Double.parseDouble(text);
    }

    public static String ToDisplayText(Double m) {
        if(m!=null)
        {
            DecimalFormat df = new DecimalFormat("#.##");

            return df.format(m);
        }
        return "";

    }


    public static String ToDisplayText(Float m) {
        if(m!=null)
        {
            DecimalFormat df = new DecimalFormat("#.##");

            return df.format(m);
        }
        return "";

    }


    public static Double ToDisplayLength(Double cm) {

        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs)
        {
            cm=cm/2.54;
        }
        return cm;
    }

    public static Double FromDisplayWeight(Double kg)
    {
        if(kg==null)
        {
            return null;
        }
        if(ApplicationState.getCurrent().getProfileInfo().settings.weightType == WS_Enums.WeightType.Pounds)
        {
            kg=kg*0.454;
        }
        return kg;
    }

    public static Double FromDisplayLength(Double cm)
    {
        if(cm==null)
        {
            return null;
        }
        if(ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs)
        {
            cm=cm*2.54;
        }
         return cm;
    }

    public static String ToDisplayLengthText(Double weight) {
        return ToDisplayText(ToDisplayLength(weight));
    }

    public static String ToDisplayWeightText(Double weight) {

        return ToDisplayText(ToDisplayWeight(weight));
    }

    public static <T> T FirstOrDefault(List<T> items) {
        if (items.size() == 0) {
            return null;
        }
        return items.get(0);
    }

    public static <T> T SingleOrDefault(List<T> items) {
        if (items.size() > 1) {
            throw new IndexOutOfBoundsException();
        } else if (items.size() == 0) {
            return null;
        }
        return items.get(0);
    }

    public static boolean isEmpty(UUID id)
    {
        return id==null || id.equals(emptyGuid());
    }

    public static String GetDisplayText(SerieDTO serie)
    {
        return GetDisplayText(serie,true);
    }

    public static String GetDisplayText(TrainingPlanSerie serie)
    {
        String dropSetString = "";
        String repType = "";
        String superSlow = "";
        String restPause = "";

        if (serie.isSuperSlow)
        {
            superSlow = " SS";
        }
        if (serie.isRestPause)
        {
            restPause = " RP";
        }
        if (!serie.dropSet.equals(WS_Enums.DropSetType.None))
        {
            dropSetString = "^" + serie.dropSet.getCode();
        }
        if (!serie.repetitionsType .equals(WS_Enums.SetType.Normalna))
        {
            repType = EnumLocalizer.Translate(serie.repetitionsType);
        }
        String format = ToStringRepetitionsRange(serie);

        return String.format("%s %s%s%s%s", format, repType, dropSetString,superSlow,restPause);
    }

    public static String ToStringRepetitionsRange(TrainingPlanSerie set)
    {
        String format = "%d-%d";
        if (set.repetitionNumberMin == set.repetitionNumberMax)
        {
            format = "%d";
        }
        else if (set.repetitionNumberMin == null)
        {
            format = "-%d";
        }
        else if (set.repetitionNumberMax == null)
        {
            format = "%d-";
        }
        return String.format(format, set.repetitionNumberMin, set.repetitionNumberMax);
    }

    public static String GetDisplayText(String text) {
        return text==null?"":text;
    }
    public static String GetDisplayText(SerieDTO serie, boolean longSetType)
    {
        String dropSetString = "";
        String repType = "";
        String superSlow = "";
        if (serie.strengthTrainingItem.exercise.exerciseType.equals(WS_Enums.ExerciseType.Cardio))
        {
            double seconds = serie.weight!=null ? serie.weight : 0;

            return DateTimeHelper.fromSeconds(Math.round(seconds));
        }
        if (!serie.dropSet .equals(WS_Enums.DropSetType.None))
        {
            dropSetString = "^" + serie.dropSet.getCode();
        }
        if(serie.isSuperSlow)
        {
            superSlow = "SS";
        }
        if (!serie.setType .equals(WS_Enums.SetType.Normalna))
        {
            if (longSetType)
            {
                repType = EnumLocalizer.Translate(serie.setType);
            }
            else
            {
                repType = getShortSetType(serie.setType);
            }
        }
        Double weight = null;
        if(serie.weight!=null)
        {
            weight = Helper.ToDisplayWeight(serie.weight);
        }
        String format = String.format("%.0fx%s", serie.repetitionNumber,ToDisplayWeightText(weight));

        return String.format("%s %s%s%s", format, repType, dropSetString,superSlow);
    }

    private static String getShortSetType(WS_Enums.SetType setType)
    {
        //todo:translate
        if (setType == WS_Enums.SetType.Max)
        {
            return "max";
        }
        if (setType == WS_Enums.SetType.MuscleFailure)
        {
            return "MF";
        }
        if (setType == WS_Enums.SetType.PrawieMax)
        {
            return "AM";
        }
        if (setType == WS_Enums.SetType.Rozgrzewkowa)
        {
            return "R";
        }
        return "";
    }


    public static int toColor(String colorString)
    {
        if(!colorString.startsWith("#"))
        {
            colorString="#"+colorString;
        }
        return Color.parseColor(colorString);
    }

    public static List<EntryObjectDTO>   getEntryObjects(Collection<TrainingDayInfo> dayInfos)
    {
        List<EntryObjectDTO> entries = new ArrayList<EntryObjectDTO>();
        for(TrainingDayInfo info: dayInfos)
        {
            entries.addAll(info.getTrainingDay().objects);
        }
        return entries;
    }

    public static int toDp(int value)
    {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, MyApplication.getAppContext().getResources().getDisplayMetrics());
    }

    public static <T extends BAGlobalObject> void RemoveFromCollection(final T objToRemove,Collection<T> list)
    {
        T userToDelete= Helper.SingleOrDefault(filter(new Predicate<T>() {
            public boolean apply(T item) {
                return item.globalId.equals(objToRemove.globalId);
            }
        },  list));

        list.remove(userToDelete);
    }

    public static <T> T LastOrDefault(List<T> items) {
        if (items.size() == 0) {
            return null;
        }
        return items.get(items.size()-1);
    }

    public static void OpenUrl(String url,Context cotnext)
    {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            cotnext.startActivity(browserIntent);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }

    }

    static public <T extends BAGlobalObject> T getItem(final LocalObjectKey key,Collection collection)
    {
        if (key.getKeyType().equals(KeyType.InstanceId))
        {
            return selectUnique(collection, new Predicate<T>() {
                public boolean apply(T item) {
                    return item.instanceId.equals(key.getId());
                }});
        }
        else
        {
            return selectUnique(collection, new Predicate<T>() {
                public boolean apply(T item) {
                    return item.globalId!=null && item.globalId.equals(key.getId());
                }});
        }
    }

    public static void prepare(TitlePageIndicator indicator)
    {
        indicator.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        indicator.setFooterIndicatorPadding(50);
    }
}
