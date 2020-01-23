package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDaysHolder;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.CalendarView;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;
import org.joda.time.format.DateTimeFormat;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 07.07.13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class CustomerInfoGeneralFragment extends Fragment implements TitleProvider {
    TextView tbUsername;
    TextView tbGender;
    TextView tbDateTime;
    TextView tbStatus;
    BAPicture imgUser;
    Button btnShowCalendar;
    TextView tbLastEntryDate;
    CustomerDTO customer;
    private TextView tbBirthday;
    private LinearLayout pnlVirtualCustomer;
    private TextView tbEmail;
    private TextView tbPhone;
    private TextView tbLabelBirthday;
    private TextView tbLabelEmail;
    private TextView tbLabelPhone;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.customer_info_general_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_customer_info_general, container, false);
        tbUsername= (TextView) rootView.findViewById(R.id.tbUsername);
        tbGender= (TextView) rootView.findViewById(R.id.tbGender);
        tbLabelBirthday= (TextView) rootView.findViewById(R.id.tbLabelBirthday);
        tbBirthday= (TextView) rootView.findViewById(R.id.tbBirthday);
        pnlVirtualCustomer= (LinearLayout) rootView.findViewById(R.id.pnlVirtualCustomer);
        tbEmail= (TextView) rootView.findViewById(R.id.tbEmail);
        tbPhone= (TextView) rootView.findViewById(R.id.tbPhone);
        tbLabelEmail= (TextView) rootView.findViewById(R.id.tbLabelEmail);
        tbLabelPhone= (TextView) rootView.findViewById(R.id.tbLabelPhone);
//        View.OnClickListener phoneDialer=new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    if (Helper.isFeatureAvailable(getActivity(), PackageManager.FEATURE_TELEPHONY)) {
//
//                        String uri = "tel:" + customer.phoneNumber.trim() ;
//                        Intent intent = new Intent(Intent.ACTION_DIAL );
//                        intent.setData(Uri.parse(uri));
//                        startActivity(intent);
//                    }
//                }
//                catch (Exception ex)
//                {
//                     ex.printStackTrace();
//                }
//
//            }
//        };
//        tbPhone.setOnClickListener(phoneDialer);
//        tbLabelPhone.setOnClickListener(phoneDialer);

//        View.OnClickListener emailSender=new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                            "mailto",customer.email, null));
//                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
//                }
//                catch (Exception ex)
//                {
//                    ex.printStackTrace();
//                }
//
//            }
//        };

//        tbEmail.setOnClickListener(emailSender);
//        tbLabelEmail.setOnClickListener(emailSender);
        tbDateTime= (TextView) rootView.findViewById(R.id.tbDateTime);
        tbLastEntryDate= (TextView) rootView.findViewById(R.id.tbLastEntryDate);
        btnShowCalendar= (Button)rootView.findViewById(R.id.btnShowCalendar);

        tbStatus= (TextView) rootView.findViewById(R.id.tbStatus);
        imgUser= (BAPicture) rootView.findViewById(R.id.imgUser);

        btnShowCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });



        return rootView;
    }

    private void showCalendar() {
        ApplicationState.getCurrent().setCurrentBrowsingTrainingDays(new TrainingDaysHolder(customer.globalId));
        Intent intent = new Intent(getActivity(),CalendarView.class);
        startActivity(intent);
    }

    public void Fill(CustomerDTO user) {
        customer=user;
    }

    void updateGUI()
    {
        pnlVirtualCustomer.setVisibility(customer.isVirtual?View.VISIBLE:View.GONE);
        tbUsername.setText(customer.getFullName());
        tbGender.setText(EnumLocalizer.Translate(customer.gender));
        tbDateTime.setText(DateTimeHelper.toRelativeDate(DateTimeHelper.toLocal(customer.creationDate)));
        tbPhone.setText(customer.phoneNumber);
        tbLabelPhone.setVisibility(TextUtils.isEmpty(customer.phoneNumber)?View.GONE:View.VISIBLE);
        tbLabelBirthday.setVisibility(customer.birthday==null?View.GONE:View.VISIBLE);
        tbLabelEmail.setVisibility(TextUtils.isEmpty(customer.email)?View.GONE:View.VISIBLE);
        imgUser.fill(customer.picture);
        tbEmail.setText(customer.email);
        if(customer.birthday!=null)
        {
            tbBirthday.setText(customer.birthday.toString(DateTimeFormat.longDate()));
        }
        else
        {
            tbBirthday.setText("");
        }


        boolean isOffline=ApplicationState.getCurrent().isOffline;
        btnShowCalendar.setVisibility(!isOffline ? View.VISIBLE : View.GONE);
    }
    @Override
    public void onResume() {
        super.onResume();

        updateGUI();
    }
}
