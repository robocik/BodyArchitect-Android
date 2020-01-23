package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.Bundle;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.splunk.mint.Mint;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 25.04.13
 * Time: 07:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class SimpleEntryObjectActivityBase extends BANormalActivityBase
{
    public <T extends EntryObjectDTO> T getEntry()
    {
        if(ApplicationState.getCurrent()==null || ApplicationState.getCurrent().getTrainingDay()==null || ApplicationState.getCurrent().CurrentEntryId==null)
        {
            return null;
        }
        return (T)ApplicationState.getCurrent().getTrainingDay().getTrainingDay().getTypedEntry(ApplicationState.getCurrent().CurrentEntryId);
    }

    public boolean getEditMode()
    {
        EntryObjectDTO entry=getEntry();
        return  ApplicationState.getCurrent().getTrainingDay() !=null && ApplicationState.getCurrent().getTrainingDay().getTrainingDay().isMine() && (entry==null || !entry.status.equals(WS_Enums.EntryObjectStatus.System));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ApplicationState.getCurrent()!=null && getEditMode())
        {
            ApplicationState.getCurrent().SetModifiedFlag();
        }
    }

    @Override
    public void lastBreath(Exception ex) {

        if(ApplicationState.getCurrent()!=null)
        {
            ApplicationState.getCurrent().SaveState(true);
        }
//        finish();
//
        //System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        Mint.setMintCallback(this);

    }

    public Class getEntryType()
    {
        return getEntry().getClass();
    }
}
