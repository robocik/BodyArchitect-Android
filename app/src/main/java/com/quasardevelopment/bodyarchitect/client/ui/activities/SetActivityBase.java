package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.CardioTrainingSessionsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.ExTimePicker;
import com.quasardevelopment.bodyarchitect.client.ui.controls.ExTimePickerDialog;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TimerControl;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ActivitiesSettings;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingItemDTO;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.05.13
 * Time: 07:48
 * To change this template use File | Settings | File Templates.
 */
public class SetActivityBase extends SimpleEntryObjectActivityBase
{
    protected SerieDTO selectedItem;
    protected EditText txtComment;
    protected ImageView imgIsRecord;
    protected TimerControl timerCtrl;
    ImageButton btnTimerStart;
    Button btnTimerBox;
    private DateTime buttonClickTime;
    private boolean stopping;
    Timer timer;

    protected void buildUI(){
        timerCtrl = (TimerControl) findViewById(R.id.ctrlTimer);
        imgIsRecord = (ImageView) findViewById(R.id.strength_training_set_item_record);
        txtComment = (EditText) findViewById(R.id.strenght_training_set_txt_comment);

        txtComment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String comment=s.toString();
                selectedItem.comment= !TextUtils.isEmpty(comment)?comment:null;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        getSupportActionBar().setSubtitle(R.string.strength_training_activity_title);
        btnTimerBox= (Button) findViewById(R.id.strength_training_timerbox);
        btnTimerBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Period period = new Period();
                if(selectedItem.endTime!=null && selectedItem.startTime!=null)
                {
                    period = new Period(selectedItem.startTime, selectedItem.endTime);
                }
                new ExTimePickerDialog(SetActivityBase.this, new ExTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(ExTimePicker view, int hour, int minute, int second, boolean cancel) {
                        if(selectedItem.startTime==null)
                        {
                            selectedItem.startTime = DateTime.now();
                        }
                        Period p = new Period(hour,minute,second,0);
                        selectedItem.endTime = selectedItem.startTime.plus(p);
                        Duration duration = new Duration(selectedItem.startTime,selectedItem.endTime);
                        selectedItem.weight = Double.valueOf(duration.getStandardSeconds());
                        btnTimerBox.setText(CardioTrainingSessionsAdapter.getCardioSessionTime(selectedItem));
                    }
                },period.getHours(),period.getMinutes(),period.getSeconds()).show();
            }
        });

        btnTimerStart= (ImageButton) findViewById(R.id.strength_training_btn_timer);
        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UpgradeAccountFragment.EnsureAccountType(SetActivityBase.this))
                {
                    return;
                }

                //after click we disable the button for one second to prevent accidential clicking second time
                buttonClickTime = DateTime.now();
                btnTimerStart.setEnabled(false);
                btnTimerBox.requestFocus();
                if (timer!=null)
                {
                    StopTimer(true);
                }
                else
                {
                    StartTimer(true);
                }
            }
        });


        setReadOnly();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgIsRecord.setVisibility(selectedItem.isRecord? View.VISIBLE:View.GONE);
        btnTimerBox.setText(CardioTrainingSessionsAdapter.getCardioSessionTime(selectedItem));
        txtComment.setText(selectedItem.comment);

        if (ActivitiesSettings.isTimerEnabled())
        {
            SetEndTime();
            StartTimer(false);
        }

        timerCtrl.setIsStarted(ApplicationState.getCurrent().getTrainingDay().getTrainingDay().isMine() && ActivitiesSettings.isGlobalTimerEnabled());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (timer!=null)
        {
            StopTimer(false);
            timer=null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerCtrl.setIsStarted(false);
        ActivitiesSettings.setTimerEnabled(timer!=null);
        if (timer!=null)
        {
            StopTimer(false);
        }
    }

    public void SetEndTime()
    {
        selectedItem.endTime = DateTime.now();

        if (Settings.getTreatSuperSetsAsOne())
        {
            int index = selectedItem.strengthTrainingItem.series.indexOf(selectedItem);
            List<StrengthTrainingItemDTO> joinedExercises = selectedItem.strengthTrainingItem.getJoinedItems();
            for (StrengthTrainingItemDTO strengthTrainingItemDto : joinedExercises)
            {
                if (strengthTrainingItemDto.series.size() > index)
                {
                    strengthTrainingItemDto.series.get(index).endTime = selectedItem.endTime;
                }
            }
        }
        TimerCalculation();
        btnTimerBox.setText(CardioTrainingSessionsAdapter.getCardioSessionTime(selectedItem));
    }


    public void SetStartTime()
    {
        selectedItem.startTime = DateTime.now();

        if (Settings.getTreatSuperSetsAsOne())
        {
            int index = selectedItem.strengthTrainingItem.series.indexOf(selectedItem);
            List<StrengthTrainingItemDTO> joinedExercises = selectedItem.strengthTrainingItem.getJoinedItems();
            for (StrengthTrainingItemDTO strengthTrainingItemDto : joinedExercises)
            {
                if (strengthTrainingItemDto.series.size() > index)
                {
                    strengthTrainingItemDto.series.get(index).startTime = selectedItem.startTime;
                }
            }
        }
    }

    protected void TimerCalculation()
    {

    }

    protected  void StopTimer(boolean resetValues)
    {
        //SelectedSetView.Set.EndTime = DateTime.Now;
        SetEndTime();
        stopping = true;
        //timer.Stop();
        btnTimerBox.setEnabled( true);
        btnTimerStart.setImageDrawable(getResources().getDrawable(R.drawable.av_play));
        if (resetValues)
        {
            ApplicationState.getCurrent().timerStartTime = DateTime.now();
        }
    }


    protected void StartTimer(boolean resetValues)
    {
        if (!ApplicationState.getCurrent().getTrainingDay().getTrainingDay().isMine())
        {
            BAMessageBox.ShowError(R.string.err_unhandled,this);
            return;
        }
        stopping = false;

        if (resetValues)
        {
            SetStartTime();
//            ApplicationState.getCurrent().timerStartTime = DateTime.now();
            ApplicationState.getCurrent().timerStartTime =buttonClickTime;
        }

        btnTimerBox.setEnabled(false);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SetActivityBase.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(timer==null)
                        { //timer has been stopped so leave this method
                            return;
                        }
                        if(!stopping)
                        { //stopping means that user click Stop timer but timer run 1 more second for disabling stop button (prevent accidential pressing start again). but we shouldn' update set time during this loop
                            SetEndTime();
                        }
                        Duration duration = new Duration(buttonClickTime, DateTime.now());
//                int seconds=set.startTime-previousSet.endTime;
                        //after 1 second we should enable again button (we disable it to prevent accidentialy pressing this button)
                        if (duration.getStandardSeconds() > 1) {
                            btnTimerStart.setEnabled(getEditMode());
                            if (stopping) {
                                timer.cancel();
                                timer=null;
                            }
                        }
                    }
                });
            }
        }, 0, 1000);
        btnTimerStart.setImageDrawable(getResources().getDrawable(R.drawable.av_stop));
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LocalObjectKey itemId=(LocalObjectKey)getIntent().getSerializableExtra("ItemId");
        StrengthTrainingEntryDTO entry=getEntry();
        selectedItem= entry.getSerie(itemId);

         buildUI();
    }

    protected void setReadOnly()
    {
        boolean readOnly=!getEditMode();
        txtComment.setEnabled(!readOnly);
        btnTimerBox.setEnabled(!readOnly);
        btnTimerStart.setVisibility(readOnly ? View.GONE : View.VISIBLE);
    }


}
