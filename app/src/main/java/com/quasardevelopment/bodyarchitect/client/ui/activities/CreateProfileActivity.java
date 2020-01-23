package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.accounts.NetworkErrorException;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Country;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.UniqueException;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ValidationException;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.util.SecurityHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.quasardevelopment.bodyarchitect.client.wcf.envelopes.BAService;
import com.splunk.mint.Mint;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 01.06.13
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class CreateProfileActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirm;
    EditText txtEmail;

    Button btnBirthday;
    Button btnCountry;
    Button btnCreateProfile;
    RadioButton rbGenderMale;
    RadioButton rbGenderFemale;
    RadioButton rbUnitImperial;
    RadioButton rbUnitMetric;
    ProgressDialog progressDlg;

    @Override
    protected void EnsureLogged()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        View inflate = getLayoutInflater().inflate(R.layout.activity_create_profile, null);
        setMainContent(inflate);

        //we hide action bar so additionally we must remove padding (which was set in base class)
        LinearLayout pnlMain = (LinearLayout) findViewById(R.id.pnlMain);
        pnlMain.setPadding(0,0,0,0);
        TextView tbTermsOfService= (TextView) findViewById(R.id.create_profile_terms_of_service);
        txtUsername= (EditText) findViewById(R.id.txtUsername);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        txtConfirm= (EditText) findViewById(R.id.txtConfirm);
        txtEmail= (EditText) findViewById(R.id.txtEmail);
        rbGenderMale= (RadioButton) findViewById(R.id.rbMale);
        rbGenderFemale= (RadioButton) findViewById(R.id.rbFemale);
        rbUnitImperial= (RadioButton) findViewById(R.id.rbImperial);
        rbUnitMetric= (RadioButton) findViewById(R.id.rbMetric);

        txtConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    createProfile();
                }
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        btnCreateProfile= (Button) findViewById(R.id.btnCreateProfile);
        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });
        btnCountry= (Button) findViewById(R.id.btnCountry);
        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateProfileActivity.this,CountrySelector.class);
                startActivityForResult(intent, 1);
            }
        });
        btnBirthday= (Button) findViewById(R.id.btnBirthday);
        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new DatePickerDialog(CreateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateTime date=new DateTime(year,month+1,day,0,0);
                        btnBirthday.setTag(date );
                        DateTimeFormatter frm=DateTimeFormat.shortDate();
                        btnBirthday.setText(date.toString(frm));
                    }
                },DateTime.now().getYear(),DateTime.now().getMonthOfYear()-1,DateTime.now().getDayOfMonth()).show();   }});

        tbTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
        final ImageButton btnCheckUsernameAvailability = (ImageButton) findViewById(R.id.btnCheckUsernameAvailable);
        btnCheckUsernameAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
                    @Override
                    public void Wsdl2CodeStartedRequest() {
                        btnCheckUsernameAvailability.setEnabled(false);
                    }

                    @Override
                    public void Wsdl2CodeFinished(String methodName, Object Data)
                    {
                        Boolean isAvailable=(Boolean)Data;
                        BAMessageBox.ShowToast(isAvailable?R.string.create_profile_username_available:R.string.create_profile_username_unavailable);
                    }

                    @Override
                    public void Wsdl2CodeFinishedWithException(Exception ex)
                    {
                    }

                    @Override
                    public void Wsdl2CodeEndedRequest() {
                        btnCheckUsernameAvailability.setEnabled(true);
                    }
                });

                try {
                    service.CheckProfileNameAvailabilityAsync(txtUsername.getText().toString());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                Country country=(Country)data.getSerializableExtra("Country");
                btnCountry.setTag(country);
                btnCountry.setText(country.EnglishName);
            }
        }
    }//onActivityResult

    private void createProfile() {
        Country country= (Country) btnCountry.getTag();
        DateTime birthday= (DateTime) btnBirthday.getTag();
        if(birthday==null || country==null||TextUtils.isEmpty(txtEmail.getText()) ||TextUtils.isEmpty(txtUsername.getText()) || TextUtils.isEmpty(txtPassword.getText()))
        {
            BAMessageBox.ShowToast(R.string.create_profile_err_empty_values);
            return;
        }
        if(!txtPassword.getText().toString().equals(txtConfirm.getText().toString()))
        {
            BAMessageBox.ShowToast(R.string.create_profile_err_wrong_passwords);
            return;
        }
        ProfileDTO profile=new ProfileDTO();
        profile.creationDate=DateTime.now(DateTimeZone.UTC);
        profile.birthday=birthday;
        profile.countryId=country.GeoId;
        profile.userName=txtUsername.getText().toString();
        profile.email=txtEmail.getText().toString();
        profile.gender= rbGenderMale.isChecked()? WS_Enums.Gender.Male: WS_Enums.Gender.Female;
        profile.settings=new ProfileSettingsDTO();
        if(rbUnitMetric.isChecked())
        {
            profile.settings.lengthType= WS_Enums.LengthType.Cm;
            profile.settings.weightType= WS_Enums.WeightType.Kg;
        }
        else
        {
            profile.settings.lengthType= WS_Enums.LengthType.Inchs;
            profile.settings.weightType= WS_Enums.WeightType.Pounds;
        }
        profile.privacy=new ProfilePrivacyDTO();
        profile.password= SecurityHelper.SHA1(txtPassword.getText().toString());

        try {
            BasicHttpBinding_IBodyArchitectAccessService service =new BasicHttpBinding_IBodyArchitectAccessService(this);
            service.CreateProfileAsync(BAService.CreateClientInformation(),profile);
        }
        catch (Exception ex)
        {
            Mint.logException(ex);
            BAMessageBox.ShowToast(R.string.create_profile_unexpected_exception);
        }

    }

    protected void closeProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        progressDlg= BAMessageBox.ShowProgressDlg(this, R.string.progress_creating_profile);
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data)
    {
        SessionData sessionData= (SessionData) Data;
        String password=txtPassword.getText().toString();
        String pwdHash=SecurityHelper.SHA1(password);
        LoginActivity.loginImplementation(sessionData,txtUsername.getText().toString(),pwdHash,password);
        getProfileInformation();
    }

    void getProfileInformation()
    {
        try
        {
            GetProfileInformationCriteria criteria = new GetProfileInformationCriteria();
            BAService service = new BAService(new IWsdl2CodeEvents() {
                @Override
                public void Wsdl2CodeStartedRequest() {}

                @Override
                public void Wsdl2CodeFinished(String methodName, Object Data)
                {
                    LoginActivity.getProfileInformationImplementation((ProfileInformationDTO) Data);
                    closeProgress();
                    Intent myIntent = new Intent(CreateProfileActivity.this, MainActivity.class);
                    if(android.os.Build.VERSION.SDK_INT>=11)
                    {
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }

                    startActivity(myIntent);
                    finish();
                }

                @Override
                public void Wsdl2CodeFinishedWithException(Exception ex) {
                    BAMessageBox.ShowToast(R.string.err_retrieving_profile_info);
                    closeProgress();
                }

                @Override
                public void Wsdl2CodeEndedRequest() {}
            });
            service.GetProfileInformation(criteria);
        }
        catch(NetworkErrorException ex)
        {
            closeProgress();
            BAMessageBox.ShowToast(R.string.err_network_problem);
        }
        catch (Exception ex)
        {
            BAMessageBox.ShowToast(R.string.err_retrieving_profile_info);
            closeProgress();
        }
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        closeProgress();
        if(ex instanceof ValidationException)
        {
            BAMessageBox.ShowValidationError((ValidationException) ex);
            return;
        }
        if(ex instanceof UniqueException)
        {
            BAMessageBox.ShowToast(R.string.create_profile_unique_exception);
            return;
        }
        Mint.logException(ex);
        BAMessageBox.ShowToast(R.string.create_profile_unexpected_exception);
    }

    @Override
    public void Wsdl2CodeEndedRequest() { }
}
