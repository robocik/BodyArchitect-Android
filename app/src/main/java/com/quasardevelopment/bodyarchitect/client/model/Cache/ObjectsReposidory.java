package com.quasardevelopment.bodyarchitect.client.model.Cache;

import android.content.Context;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Constants;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.05.13
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class ObjectsReposidory  implements Serializable
{
    static ObjectsReposidory cache;
    private ExerciseCache exercises;
    private MyPlaceCache myPlaces;
    private SupplementsCache supplements;
    private MessageCache messages;
    private TrainingPlansCache trainingPlans;
    CustomersCache customers;

    public static ObjectsReposidory getCache()
    {
        if(cache==null)
        {
            cache=new ObjectsReposidory();
        }
        return cache;
    }

    public boolean shouldBeSaved()
    {
        return (exercises!=null && exercises.isModified) ||
        (myPlaces!=null && myPlaces.isModified) ||
                (supplements!=null && supplements.isModified)
                || (messages!=null && messages.isModified)
                || (trainingPlans!=null && trainingPlans.isModified)
                || (customers!=null && customers.isModified);
    }

    void resetModified()
    {
        if(customers!=null)
        {
            customers.isModified=false;
        }
        if(exercises!=null)
        {
            exercises.isModified=false;
        }
        if(myPlaces!=null)
        {
            myPlaces.isModified=false;
        }
        if(supplements!=null)
        {
            supplements.isModified=false;
        }
        if(messages!=null)
        {
            messages.isModified=false;
        }
        if(trainingPlans!=null)
        {
            trainingPlans.isModified=false;
        }
    }
    public void SaveState()
    {
        if(!shouldBeSaved())
        {
             return;
        }
        try
        {
            FileOutputStream fos = MyApplication.getAppContext().openFileOutput(Constants.CacheFileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            resetModified();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void loadState()
    {
        try {

            FileInputStream fis = MyApplication.getAppContext().openFileInput(Constants.CacheFileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            ObjectsReposidory simpleClass = (ObjectsReposidory) is.readObject();
            is.close();
            cache= simpleClass;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }


    public void Clear()
    {
        exercises=null;
        myPlaces=null;
        supplements=null;
        messages=null;
        trainingPlans=null;
    }

    public void ClearAfterLogin()
    {
        if (!ApplicationState.getCurrent().isOffline)
        {
//            Featured = null;
//            Messages = null;
        }
    }

    public ObjectsReposidory Copy()
    {
        //ObjectsReposidory cache = new ObjectsReposidory();
        //if (ApplicationState.Current.Cache.Exercises.IsLoaded)
        //{
        //    cache.Exercises = ApplicationState.Current.Cache.Exercises;
        //}
        //if (ApplicationState.Current.Cache.Supplements.IsLoaded)
        //{
        //    cache.Supplements = ApplicationState.Current.Cache.Supplements;
        //}
        //if (ApplicationState.Current.Cache.TrainingPlans.IsLoaded)
        //{
        //    cache.TrainingPlans = ApplicationState.Current.Cache.TrainingPlans;
        //}
        //if (ApplicationState.Current.Cache.Messages.IsLoaded)
        //{
        //    cache.Messages = ApplicationState.Current.Cache.Messages;
        //}
        //return cache;
        //TODO:CHECK THIS
        return this;
    }

    public CustomersCache getCustomers()
    {
        if(customers==null)
        {
            customers=new CustomersCache();
        }
        return customers;
    }

    public TrainingPlansCache getTrainingPlans()
    {
        if(trainingPlans==null)
        {
            trainingPlans=new TrainingPlansCache();
        }
        return trainingPlans;
    }

    public SupplementsCache getSupplements()
    {
        if(supplements==null)
        {
            supplements=new SupplementsCache();
        }
        return supplements;
    }

    public MyPlaceCache getMyPlaces()
    {
        if(myPlaces==null)
        {
            myPlaces=new MyPlaceCache();
        }
        return myPlaces;
    }

    public ExerciseCache getExercises()
    {
        if(exercises==null)
        {
            exercises=new ExerciseCache();
        }
        return exercises;
    }

    public MessageCache getMessages()
    {
        if(messages==null)
        {
            messages=new MessageCache();
        }
        return messages;
    }

    public static void clear() {
        cache=null;
        try
        {
            MyApplication.getAppContext().deleteFile(Constants.CacheFileName);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
