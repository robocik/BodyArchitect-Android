package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.GPSHelper;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;
import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.05.13
 * Time: 07:48
 * To change this template use File | Settings | File Templates.
 */
public class CardioSetActivity extends SetActivityBase
{
    EditText txtDistance;
    TextView tbDistanceType;
    TextView tbCalories;
    TextView tbTimerDescription;


    @Override
    protected void setReadOnly() {
        super.setReadOnly();
        boolean readOnly=!getEditMode();
        tbTimerDescription.setVisibility(!readOnly? View.VISIBLE:View.GONE);
        txtDistance.setEnabled(!readOnly);
        btnTimerStart.setEnabled(!readOnly);
        btnTimerBox.setEnabled(!readOnly);
    }

    @Override
    protected void buildUI(){
        View inflate = getLayoutInflater().inflate(R.layout.activity_cardio_set, null);
        setMainContent(inflate);


        txtDistance= (EditText) findViewById(R.id.cardio_set_distance);
        tbDistanceType= (TextView) findViewById(R.id.cardio_set_distance_type);
        tbTimerDescription= (TextView) findViewById(R.id.cardio_set_timer_description);
        tbCalories= (TextView) findViewById(R.id.cardio_set_calories);

        txtDistance.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                selectedItem.repetitionNumber = TextUtils.isEmpty(s.toString())?null:Helper.FromDisplayDistance(Helper.parseDouble(s.toString()));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        super.buildUI();


    }


    @Override
    protected void TimerCalculation() {
        Duration duration = new Duration(selectedItem.startTime, selectedItem.endTime);
        selectedItem.weight = Double.valueOf(duration.getStandardSeconds());
        if (ApplicationState.getCurrent().getTrainingDay().getTrainingDay().customerId!=null)
        {
            CustomerDTO customer = ObjectsReposidory.getCache().getCustomers().GetItem(ApplicationState.getCurrent().getTrainingDay().getTrainingDay().customerId);
            selectedItem.calculatedValue=   GPSHelper.CalculateCalories(this.selectedItem.strengthTrainingItem.exercise.met, selectedItem.weight,customer);
        }
        else
        {
            selectedItem.calculatedValue=   GPSHelper.CalculateCalories(this.selectedItem.strengthTrainingItem.exercise.met, selectedItem.weight, ApplicationState.getCurrent().getProfileInfo());
        }
        tbCalories.setText(String.format("%.0f",selectedItem.calculatedValue));
    }
}
