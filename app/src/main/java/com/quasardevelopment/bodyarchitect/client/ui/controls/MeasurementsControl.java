package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDaysHolder;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.EntryObjectDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SizeEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WymiaryDTO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 29.05.13
 * Time: 07:38
 * To change this template use File | Settings | File Templates.
 */
public class MeasurementsControl  extends ScrollView
{
    TextView tbEmptyMeasurements;
    TableLayout pnlMain;

    EditText txtWeight;
    EditText txtHeight;
    EditText txtChest;
    EditText txtAbs;
    EditText txtRightBiceps;
    EditText txtLeftBiceps;
    EditText txtRightForearm;
    EditText txtLeftForearm;
    EditText txtRightLeg;
    EditText txtLeftLeg;

    TextView tbWeightType;
    TextView tbHeightType;
    TextView tbChestType;
    TextView tbAbsType;
    TextView tbRightBicepsType;
    TextView tbLeftBicepsType;
    TextView tbRightForearmType;
    TextView tbLeftForearmType;
    TextView tbRightLegType;
    TextView tbLeftLegType;


    TextView tbWeightChange;
    TextView tbHeightChange;
    TextView tbChestChange;
    TextView tbAbsChange;
    TextView tbRightBicepsChange;
    TextView tbLeftBicepsChange;
    TextView tbRightForearmChange;
    TextView tbLeftForearmChange;
    TextView tbRightLegChange;
    TextView tbLeftLegChange;
    WymiaryDTO wymiary;

    public MeasurementsControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.measurements_control, this);

        tbEmptyMeasurements= (TextView) findViewById(R.id.empty_measurements);
        pnlMain= (TableLayout) findViewById(R.id.pnlMeasurements);

        txtWeight= (EditText) findViewById(R.id.txt_weight);
        txtHeight= (EditText) findViewById(R.id.txt_height);
        txtChest= (EditText) findViewById(R.id.txt_chest);
        txtAbs= (EditText) findViewById(R.id.txt_abs);
        txtRightBiceps= (EditText) findViewById(R.id.txt_right_biceps);
        txtLeftBiceps= (EditText) findViewById(R.id.txt_left_biceps);
        txtRightForearm= (EditText) findViewById(R.id.txt_right_forearm);
        txtLeftForearm= (EditText) findViewById(R.id.txt_left_forearm);
        txtRightLeg= (EditText) findViewById(R.id.txt_right_leg);
        txtLeftLeg= (EditText) findViewById(R.id.txt_left_leg);

        tbWeightType= (TextView) findViewById(R.id.tb_weight_type);
        tbHeightType= (TextView) findViewById(R.id.tb_height_type);
        tbChestType= (TextView) findViewById(R.id.tb_chest_type);
        tbAbsType= (TextView) findViewById(R.id.tb_abs_type);
        tbRightBicepsType= (TextView) findViewById(R.id.tb_right_biceps_type);
        tbLeftBicepsType= (TextView) findViewById(R.id.tb_left_biceps_type);
        tbRightForearmType= (TextView) findViewById(R.id.tb_right_forearm_type);
        tbLeftForearmType= (TextView) findViewById(R.id.tb_left_forearm_type);
        tbRightLegType= (TextView) findViewById(R.id.tb_right_leg_type);
        tbLeftLegType= (TextView) findViewById(R.id.tb_left_leg_type);

        tbWeightChange= (TextView) findViewById(R.id.tb_weight_change);
        tbHeightChange= (TextView) findViewById(R.id.tb_height_change);
        tbChestChange= (TextView) findViewById(R.id.tb_chest_change);
        tbAbsChange= (TextView) findViewById(R.id.tb_abs_change);
        tbRightBicepsChange= (TextView) findViewById(R.id.tb_right_biceps_change);
        tbLeftBicepsChange= (TextView) findViewById(R.id.tb_left_biceps_change);
        tbRightForearmChange= (TextView) findViewById(R.id.tb_right_forearm_change);
        tbLeftForearmChange= (TextView) findViewById(R.id.tb_left_forearm_change);
        tbRightLegChange= (TextView) findViewById(R.id.tb_right_leg_change);
        tbLeftLegChange= (TextView) findViewById(R.id.tb_left_leg_change);


        txtWeight.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.weight =TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayWeight(Helper.parseDouble(s.toString()));

                     updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtHeight.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.height = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtAbs.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.pas = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtChest.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.klatka = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtLeftBiceps.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.leftBiceps =TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtRightBiceps.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.rightBiceps = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtLeftForearm.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.leftForearm = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtRightForearm.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.rightForearm = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtLeftLeg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.leftUdo =TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
        txtRightLeg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(wymiary!=null)
                {
                    wymiary.rightUdo = TextUtils.isEmpty(s.toString())?0:Helper.FromDisplayLength(Helper.parseDouble(s.toString()));
                    updateChanges();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {            }
        });
    }


    void setValue(EditText txtBox,double value)
    {
        if(value>0)
        {
            txtBox.setText(Helper.ToDisplayLengthText(value));
        }
        else
        {
            txtBox.setText("");
        }
    }
    public void fill(final WymiaryDTO wymiary,final TrainingDaysHolder holder)
    {
        this.wymiary=wymiary;

        setValue(txtChest,wymiary.klatka);
        setValue(txtLeftForearm,wymiary.leftForearm);
        setValue(txtRightForearm,wymiary.rightForearm);
        setValue(txtRightLeg,wymiary.rightUdo);
        setValue(txtLeftLeg,wymiary.leftUdo);
        setValue(txtLeftBiceps,wymiary.leftBiceps);
        setValue(txtRightBiceps,wymiary.rightBiceps);
        setValue(txtAbs,wymiary.pas);
        setValue(txtHeight,wymiary.height);

        if(wymiary.weight>0)
        {
            txtWeight.setText(Helper.ToDisplayWeightText(wymiary.weight));
        }
        else
        {
            txtWeight.setText("");
        }

        tbLeftLegType.setText(EnumLocalizer.LengthType());
        tbRightForearmType.setText(EnumLocalizer.LengthType());
        tbLeftBicepsType.setText(EnumLocalizer.LengthType());
        tbRightBicepsType.setText(EnumLocalizer.LengthType());
        tbRightLegType.setText(EnumLocalizer.LengthType());
        tbLeftForearmType.setText(EnumLocalizer.LengthType());
        tbHeightType.setText(EnumLocalizer.LengthType());
        tbAbsType.setText(EnumLocalizer.LengthType());
        tbWeightType.setText(EnumLocalizer.WeightType());
        tbChestType.setText(EnumLocalizer.LengthType());
        setReadOnly(!txtWeight.isEnabled());

        if(holder!=null)
        {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected void onPreExecute() {

                };
                @Override
                protected Void doInBackground(Void... params) {
                    updateChanges(wymiary,holder);
                    return null;
                }
                @Override
                protected void onPostExecute(Void result)
                {
                    updateChanges();
                }
            }.execute();
        }
    }

    public void setReadOnly(boolean readOnly) {
        txtWeight.setEnabled(!readOnly);
        txtHeight.setEnabled(!readOnly);
        txtChest.setEnabled(!readOnly);
        txtAbs.setEnabled(!readOnly);
        txtRightBiceps.setEnabled(!readOnly);
        txtLeftBiceps.setEnabled(!readOnly);
        txtRightForearm.setEnabled(!readOnly);
        txtLeftForearm.setEnabled(!readOnly);
        txtRightLeg.setEnabled(!readOnly);
        txtLeftLeg.setEnabled(!readOnly);
        if(readOnly && (wymiary==null || wymiary.isEmpty()))
        {
            pnlMain.setVisibility(View.GONE);
            tbEmptyMeasurements.setVisibility(View.VISIBLE);
        }
        else
        {
            pnlMain.setVisibility(View.VISIBLE);
            tbEmptyMeasurements.setVisibility(View.GONE);
        }
    }

    double previousWeight;
    double previousHeight;
    double previousPas;
    double previousKlatka;
    double previousLeftBiceps;
    double previousRightBiceps;
    double previousRightForearm;
    double previousLeftForearm;
    double previousRightUdo;
    double previousLeftUdo;

    private void updateChanges(final WymiaryDTO currentSizes,TrainingDaysHolder holder)
    {
        List<EntryObjectDTO> entries=Helper.getEntryObjects(holder.getTrainingDays().values());
        List<EntryObjectDTO> newWay= filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.getClass().equals(SizeEntryDTO.class) && item.trainingDay.trainingDate.toLocalDate().isBefore(currentSizes.time.dateTime.toLocalDate());
            }
        }, entries);


        Collections.sort(newWay, Collections.reverseOrder(new Comparator<EntryObjectDTO>() {
            @Override
            public int compare(EntryObjectDTO entryObjectDTO, EntryObjectDTO entryObjectDTO2) {
                return entryObjectDTO.trainingDay.trainingDate.compareTo(entryObjectDTO2.trainingDay.trainingDate);
            }
        }));

        SizeEntryDTO res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.weight>0;
            }
        }, newWay));
        if (res != null)
        {
            previousWeight = res.wymiary.weight;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.height>0;
            }
        }, newWay));
        if (res != null)
        {
            previousHeight = res.wymiary.height;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.pas>0;
            }
        }, newWay));
        if (res != null)
        {
            previousPas = res.wymiary.pas;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.klatka>0;
            }
        }, newWay));
        if (res != null)
        {
            previousKlatka = res.wymiary.klatka;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.leftBiceps>0;
            }
        }, newWay));
        if (res != null)
        {
            previousLeftBiceps = res.wymiary.leftBiceps;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.rightBiceps>0;
            }
        }, newWay));
        if (res != null)
        {
            previousRightBiceps = res.wymiary.rightBiceps;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.rightForearm>0;
            }
        }, newWay));
        if (res != null)
        {
            previousRightForearm = res.wymiary.rightForearm;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.leftForearm>0;
            }
        }, newWay));
        if (res != null)
        {
            previousLeftForearm = res.wymiary.leftForearm;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.rightUdo>0;
            }
        }, newWay));
        if (res != null)
        {
            previousRightUdo = res.wymiary.rightUdo;
        }

        res= (SizeEntryDTO) Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return ((SizeEntryDTO)item).wymiary.leftUdo>0;
            }
        }, newWay));
        if (res != null)
        {
            previousLeftUdo = res.wymiary.leftUdo;
        }

    }



    void setChangeText(TextView txt,double value,double previous,boolean isLength)
    {
        double change=value-previous;
        if (change !=0 && value!=0 && previous!=0)
        {
            txt.setVisibility(View.VISIBLE);
            String sign=change<0?"":"+";
            String strValue=isLength?Helper.ToDisplayLengthText(change):Helper.ToDisplayWeightText(change);
            txt.setText (String.format("(%s%s)",sign,strValue));
            txt.setTextColor(change<0? Color.RED:Color.GREEN);
        }
        else
        {
            txt.setVisibility(View.GONE);
        }
    }

    private void updateChanges()
    {
        setChangeText(tbWeightChange,wymiary!=null?wymiary.weight:0,previousWeight,false);
        setChangeText(tbHeightChange,wymiary!=null?wymiary.height:0,previousHeight,true);
        setChangeText(tbChestChange,wymiary!=null?wymiary.klatka:0,previousKlatka,true);
        setChangeText(tbRightBicepsChange,wymiary!=null?wymiary.rightBiceps:0,previousRightBiceps,true);
        setChangeText(tbLeftBicepsChange,wymiary!=null?wymiary.leftBiceps:0,previousLeftBiceps,true);
        setChangeText(tbRightForearmChange,wymiary!=null?wymiary.rightForearm:0,previousRightForearm,true);
        setChangeText(tbLeftForearmChange,wymiary!=null?wymiary.leftForearm:0,previousLeftForearm,true);
        setChangeText(tbRightLegChange,wymiary!=null?wymiary.rightUdo:0,previousRightUdo,true);
        setChangeText(tbLeftLegChange,wymiary!=null?wymiary.leftUdo:0,previousLeftUdo,true);
        setChangeText(tbAbsChange,wymiary!=null?wymiary.pas:0,previousPas,true);
    }


}
