package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Country;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MeasurementsControl;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MeasurementsTimeControl;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.util.SecurityHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.splunk.mint.Mint;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.InputStream;
import java.util.EnumSet;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 05.06.13
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public class ProfileEditActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    private final static int PICK_IMAGE = 3;
    final static int PICK_COUNTRY=1;
    Bitmap newBitmap;

    ProgressDialog progressDlg;
    TabHost tabHost;
    TextView tbUsername;
    TextView tbHeaderMeasurementsTime;
    TextView tbEmail;
    TextView tbAutoUpdateDescription;
    Button btnCountry;
    Button btnBirthday;
    EditText txtPassword;
    EditText txtConfirm;
    EditText txtAbout;
    RadioButton rbGenderMale;
    RadioButton rbGenderFemale;
    RadioButton rbUnitImperial;
    RadioButton rbUnitMetric;
    MeasurementsControl ctrlSizes;
    android.support.v7.widget.SwitchCompat tsAllowComments;
    android.support.v7.widget.SwitchCompat tsAutoUpdateMeasurements;
    //WymiaryDTO currentWymiary;
    Spinner cmbPrivacyCalendarEntries;
    Spinner cmbPrivacyMeasurements;
    Spinner cmbPrivacyFriends;
    Spinner cmbPrivacyBirthday;

    CheckBox chkNotificationMessageFriendAddedEntry;
    CheckBox chkNotificationEMailFriendAddedEntry;
    CheckBox chkNotificationMessageFollowerAddedEntry;
    CheckBox chkNotificationEMailFollowerAddedEntry;
    CheckBox chkNotificationMessageDayCommented;
    CheckBox chkNotificationEMailDayCommented;
    CheckBox chkNotificationMessageItemVoted;
    CheckBox chkNotificationEMailItemVoted;
    CheckBox chkNotificationMessageSocialEvent;
    CheckBox chkNotificationEMailSocialEvent;

    BAPicture imgPicture;
    private MeasurementsTimeControl sizesTimeCtrl;
    private android.view.MenuItem mnuSave;
    private android.view.MenuItem mnuRemovePicture;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_profile_edit, null);
        setMainContent(inflate);


        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.profile_edit_activity_tab_info));
        spec1.setContent(R.id.profile_edit_tab_info);
        spec1.setIndicator(getString(R.string.profile_edit_activity_tab_info));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.profile_edit_activity_tab_settings));
        spec2.setIndicator(getString(R.string.profile_edit_activity_tab_settings));
        spec2.setContent(R.id.profile_edit_tab_settings);

        TabHost.TabSpec spec3=tabHost.newTabSpec(getString(R.string.profile_edit_activity_tab_sizes));
        spec3.setIndicator(getString(R.string.profile_edit_activity_tab_sizes));
        spec3.setContent(R.id.profile_edit_tab_sizes);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);

        tbHeaderMeasurementsTime= (TextView) findViewById(R.id.tbHeaderMeasurementsTime);
        tsAllowComments=(android.support.v7.widget.SwitchCompat)findViewById(R.id.swAllowComments);
        tsAutoUpdateMeasurements= (android.support.v7.widget.SwitchCompat) findViewById(R.id.swAutoUpdatesMeasurements);
        tbAutoUpdateDescription= (TextView) findViewById(R.id.tbAutoUpdateDescription);
        imgPicture= (BAPicture) findViewById(R.id.image);
        sizesTimeCtrl= (MeasurementsTimeControl) findViewById(R.id.sizesTimeCtrl);
        ctrlSizes= (MeasurementsControl) findViewById(R.id.sizesCtrl);
        tbUsername= (TextView) findViewById(R.id.tbUsername);
        tbEmail= (TextView) findViewById(R.id.tbEmail);
        txtAbout= (EditText) findViewById(R.id.txtAbout);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        txtConfirm= (EditText) findViewById(R.id.txtConfirm);
        rbGenderMale= (RadioButton) findViewById(R.id.rbMale);
        rbGenderFemale= (RadioButton) findViewById(R.id.rbFemale);
        rbUnitImperial= (RadioButton) findViewById(R.id.rbImperial);
        rbUnitMetric= (RadioButton) findViewById(R.id.rbMetric);

        getSupportActionBar().setSubtitle(R.string.profile_edit_title);

        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,getString(R.string.profile_edit_activity_select_picture)), PICK_IMAGE);
            }
        });
        rbUnitImperial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationState.getCurrent().editProfileInfo.settings.lengthType= WS_Enums.LengthType.Inchs;
                ApplicationState.getCurrent().editProfileInfo.settings.weightType= WS_Enums.WeightType.Pounds;
            }
        });
        rbUnitMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationState.getCurrent().editProfileInfo.settings.lengthType= WS_Enums.LengthType.Cm;
                ApplicationState.getCurrent().editProfileInfo.settings.weightType= WS_Enums.WeightType.Kg;
            }
        });

        rbGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationState.getCurrent().editProfileInfo.user.gender= WS_Enums.Gender.Male;
            }
        });
        rbGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationState.getCurrent().editProfileInfo.user.gender= WS_Enums.Gender.Female;
            }
        });

        btnCountry= (Button) findViewById(R.id.btnCountry);
        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileEditActivity.this,CountrySelector.class);
                startActivityForResult(intent, PICK_COUNTRY);
            }
        });

        txtAbout.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String comment=s.toString();
                ApplicationState.getCurrent().editProfileInfo.aboutInformation= !TextUtils.isEmpty(comment)?comment:null;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        btnBirthday= (Button) findViewById(R.id.btnBirthday);
        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new DatePickerDialog(ProfileEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateTime date=new DateTime(year,month+1,day,0,0);
                        btnBirthday.setTag(date );
                        DateTimeFormatter frm= DateTimeFormat.shortDate();
                        btnBirthday.setText(date.toString(frm));
                        ApplicationState.getCurrent().editProfileInfo.birthday=date;
                    }
                },ApplicationState.getCurrent().editProfileInfo.birthday.getYear(),ApplicationState.getCurrent().editProfileInfo.birthday.getMonthOfYear()-1,ApplicationState.getCurrent().editProfileInfo.birthday.getDayOfMonth()).show();   }});

        tsAllowComments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                ApplicationState.getCurrent().editProfileInfo.settings.allowTrainingDayComments = checked;
            }
        });

        tsAutoUpdateMeasurements.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                ApplicationState.getCurrent().editProfileInfo.settings.automaticUpdateMeasurements = checked;
                updateMeasurementsGui();
            }
        });

        createPrivacy();
        createNotifications();
    }

    void updateMeasurementsGui()
    {
        boolean checked=ApplicationState.getCurrent().editProfileInfo.settings.automaticUpdateMeasurements;
        tbAutoUpdateDescription.setVisibility(checked?View.VISIBLE:View.GONE);
        ctrlSizes.setReadOnly(checked);
        tbHeaderMeasurementsTime.setVisibility(checked?View.GONE:View.VISIBLE);
        sizesTimeCtrl.setVisibility(checked?View.GONE:View.VISIBLE);
        cmbPrivacyCalendarEntries.setEnabled(ApplicationState.getCurrent().isPremium());
        cmbPrivacyMeasurements.setEnabled(ApplicationState.getCurrent().isPremium());
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ProfileInformationDTO editInfo=ApplicationState.getCurrent().editProfileInfo;
        if(editInfo==null)
        {
            return;
        }
        sizesTimeCtrl.fill(editInfo.wymiary,true);
        ctrlSizes.fill(editInfo.wymiary,null);
        tbUsername.setText(editInfo.user.userName);
        tbEmail.setText(ApplicationState.getCurrent().getSessionData().profile.email);
        if(newBitmap==null)
        {
            imgPicture.fill(editInfo.user.picture);
        }

        cmbPrivacyFriends.setSelection(editInfo.user.privacy.friends.getCode());
        if(ApplicationState.getCurrent().isPremium())
        {
            cmbPrivacyCalendarEntries.setSelection(editInfo.user.privacy.calendarView.getCode());
            cmbPrivacyMeasurements.setSelection(editInfo.user.privacy.sizes.getCode());
        }
        else
        {
            cmbPrivacyCalendarEntries.setSelection(WS_Enums.Privacy.Public.getCode());
            cmbPrivacyMeasurements.setSelection(WS_Enums.Privacy.Public.getCode());
        }
        cmbPrivacyBirthday.setSelection(editInfo.user.privacy.birthdayDate.getCode());
        tsAllowComments.setChecked(editInfo.settings.allowTrainingDayComments);
        DateTimeFormatter frm= DateTimeFormat.mediumDate();
        btnBirthday.setText(editInfo.birthday.toString(frm));

        btnCountry.setText(Country.getCountry(editInfo.user.countryId).EnglishName);
        fillNotifications();
        rbGenderMale.setChecked(editInfo.user.gender== WS_Enums.Gender.Male);
        rbGenderFemale.setChecked(editInfo.user.gender== WS_Enums.Gender.Female);
        rbUnitImperial.setChecked(editInfo.settings.lengthType== WS_Enums.LengthType.Inchs);
        rbUnitMetric.setChecked(editInfo.settings.lengthType== WS_Enums.LengthType.Cm);
        tsAutoUpdateMeasurements.setChecked(editInfo.settings.automaticUpdateMeasurements);
        updateMeasurementsGui();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_COUNTRY) {

            if(resultCode == RESULT_OK){
                Country country=(Country)data.getSerializableExtra("Country");
                btnCountry.setTag(country);
                btnCountry.setText(country.NativeName);
                ApplicationState.getCurrent().editProfileInfo.user.countryId=country.GeoId;
            }
        }
        else if(requestCode==PICK_IMAGE && data != null && data.getData() != null)
        {
            Uri _uri = data.getData();
            if (_uri != null) {
                //User had pick an image.
                try {
                    InputStream imageStream = getContentResolver().openInputStream(_uri);
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                    newBitmap = Bitmap.createScaledBitmap(bmp, 120,120, true);

                    ApplicationState.getCurrent().editProfileInfo.user.picture = new PictureInfoDTO();
                    ApplicationState.getCurrent().editProfileInfo.user.picture.pictureId=UUID.randomUUID();
                    ApplicationState.getCurrent().editProfileInfo.user.picture.hash="test";
                    imgPicture.setImageBitmap(newBitmap);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    BAMessageBox.ShowToast(R.string.profile_edit_activity_err_select_picture);
                }

            }
        }
    }//onActivityResult

    private void createPrivacy() {
        cmbPrivacyCalendarEntries= (Spinner) findViewById(R.id.cmbPrivacyCalendarEntries);
        cmbPrivacyMeasurements= (Spinner) findViewById(R.id.cmbPrivacyMeasurements);
        cmbPrivacyFriends= (Spinner) findViewById(R.id.cmbPrivacyFriends);
        cmbPrivacyBirthday= (Spinner) findViewById(R.id.cmbPrivacyBirthday);

        final String array_spinner[]=new String[3];
        array_spinner[0]= getResources().getString(R.string.Privacy_Private);
        array_spinner[1]= getResources().getString(R.string.Privacy_FriendsOnly);
        array_spinner[2]= getResources().getString(R.string.Privacy_Public);

        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPrivacyMeasurements.setAdapter(adapter);

        cmbPrivacyMeasurements.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        ApplicationState.getCurrent().editProfileInfo.user.privacy.sizes= WS_Enums.Privacy.values()[position];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });

        adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPrivacyCalendarEntries.setAdapter(adapter);

        cmbPrivacyCalendarEntries.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        ApplicationState.getCurrent().editProfileInfo.user.privacy.calendarView= WS_Enums.Privacy.values()[position];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });


        adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPrivacyFriends.setAdapter(adapter);

        cmbPrivacyFriends.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        ApplicationState.getCurrent().editProfileInfo.user.privacy.friends= WS_Enums.Privacy.values()[position];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });

        adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPrivacyBirthday.setAdapter(adapter);

        cmbPrivacyBirthday.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        ApplicationState.getCurrent().editProfileInfo.user.privacy.birthdayDate= WS_Enums.Privacy.values()[position];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }


    void createNotifications()
    {
        chkNotificationMessageFriendAddedEntry= (CheckBox) findViewById(R.id.chkNotificationMessageFriendAddedEntry);
        chkNotificationEMailFriendAddedEntry= (CheckBox) findViewById(R.id.chkNotificationEMailFriendAddedEntry);
        chkNotificationMessageFollowerAddedEntry= (CheckBox) findViewById(R.id.chkNotificationMessageFollowerAddedEntry);
        chkNotificationEMailFollowerAddedEntry= (CheckBox) findViewById(R.id.chkNotificationEMailFollowerAddedEntry);
        chkNotificationMessageDayCommented= (CheckBox) findViewById(R.id.chkNotificationMessageDayCommented);
        chkNotificationEMailDayCommented= (CheckBox) findViewById(R.id.chkNotificationEMailDayCommented);
        chkNotificationMessageItemVoted= (CheckBox) findViewById(R.id.chkNotificationMessageItemVoted);
        chkNotificationEMailItemVoted= (CheckBox) findViewById(R.id.chkNotificationEMailItemVoted);
        chkNotificationMessageSocialEvent= (CheckBox) findViewById(R.id.chkNotificationMessageSocialEvent);
        chkNotificationEMailSocialEvent= (CheckBox) findViewById(R.id.chkNotificationEMailSocialEvent);

        View.OnClickListener listener =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNotification(ApplicationState.getCurrent().editProfileInfo.settings.notificationVoted,chkNotificationMessageItemVoted.isChecked(),chkNotificationEMailItemVoted.isChecked());
                getNotification(ApplicationState.getCurrent().editProfileInfo.settings.notificationBlogCommentAdded,chkNotificationMessageDayCommented.isChecked(),chkNotificationEMailDayCommented.isChecked());
                getNotification(ApplicationState.getCurrent().editProfileInfo.settings.notificationFriendChangedCalendar,chkNotificationMessageFriendAddedEntry.isChecked(), chkNotificationEMailFriendAddedEntry.isChecked());
                getNotification(ApplicationState.getCurrent().editProfileInfo.settings.notificationSocial,chkNotificationMessageSocialEvent.isChecked(), chkNotificationEMailSocialEvent.isChecked());
                getNotification(ApplicationState.getCurrent().editProfileInfo.settings.notificationFollowersChangedCalendar,chkNotificationMessageFollowerAddedEntry.isChecked(), chkNotificationEMailFollowerAddedEntry.isChecked());
            }
        };
        chkNotificationMessageFriendAddedEntry.setOnClickListener(listener);
        chkNotificationEMailFriendAddedEntry.setOnClickListener(listener);
        chkNotificationMessageFollowerAddedEntry.setOnClickListener(listener);
        chkNotificationEMailFollowerAddedEntry.setOnClickListener(listener);
        chkNotificationMessageDayCommented.setOnClickListener(listener);
        chkNotificationEMailDayCommented.setOnClickListener(listener);
        chkNotificationMessageItemVoted.setOnClickListener(listener);
        chkNotificationEMailItemVoted.setOnClickListener(listener);
        chkNotificationMessageSocialEvent.setOnClickListener(listener);
        chkNotificationEMailSocialEvent.setOnClickListener(listener);
    }

    void fillNotifications()
    {
        chkNotificationMessageFriendAddedEntry.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationFriendChangedCalendar.contains(WS_Enums.ProfileNotification.Message));
        chkNotificationEMailFriendAddedEntry.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationFriendChangedCalendar.contains(WS_Enums.ProfileNotification.Email));
        chkNotificationMessageFollowerAddedEntry.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationFollowersChangedCalendar.contains(WS_Enums.ProfileNotification.Message));
        chkNotificationEMailFollowerAddedEntry.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationFollowersChangedCalendar.contains(WS_Enums.ProfileNotification.Email));
        chkNotificationMessageDayCommented.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationBlogCommentAdded.contains(WS_Enums.ProfileNotification.Message));
        chkNotificationEMailDayCommented.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationBlogCommentAdded.contains(WS_Enums.ProfileNotification.Email));
        chkNotificationMessageItemVoted.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationVoted.contains(WS_Enums.ProfileNotification.Message));
        chkNotificationEMailItemVoted.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationVoted.contains(WS_Enums.ProfileNotification.Email));
        chkNotificationMessageSocialEvent.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationSocial.contains(WS_Enums.ProfileNotification.Message));
        chkNotificationEMailSocialEvent.setChecked(ApplicationState.getCurrent().editProfileInfo.settings.notificationSocial.contains(WS_Enums.ProfileNotification.Email));
    }

    void getNotification(EnumSet<WS_Enums.ProfileNotification> enumSet,boolean message, boolean email)
    {
//        WS_Enums.ProfileNotification notification = WS_Enums.ProfileNotification.None;
        enumSet.clear();
        if (message)
        {
            enumSet.add(WS_Enums.ProfileNotification.Message);
        }
        if (email)
        {
            enumSet.add(WS_Enums.ProfileNotification.Email);
        }

    }

    @Override
    public void onBackPressed()
    {
        if(ApplicationState.getCurrent().editProfileInfo==null)
        {
            return;
        }
        if(ApplicationState.getCurrent().editProfileInfo.wymiary.isEmpty())
        {
            ApplicationState.getCurrent().getProfileInfo().wymiary=ApplicationState.getCurrent().editProfileInfo.wymiary;
        }

        if(isModified())
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle(R.string.html_app_name);
            dlgAlert.setMessage(R.string.confirm_exit);

            dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    ApplicationState.getCurrent().editProfileInfo=null;
                    finish();
                }
            });
            dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    if(ApplicationState.getCurrent().editProfileInfo.wymiary.isEmpty())
                    {
                        ApplicationState.getCurrent().getProfileInfo().wymiary=null;
                    }
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        else
        {
//            if(ApplicationState.getCurrent().editProfileInfo.wymiary.isEmpty())
//            {
//                ApplicationState.getCurrent().getProfileInfo().wymiary=null;
//            }
            ApplicationState.getCurrent().editProfileInfo=null;
            super.onBackPressed();
        }

    }

    protected boolean isModified()
    {
        if (ApplicationState.getCurrent() == null || ApplicationState.getCurrent().editProfileInfo == null )
        {
            return false;
        }

        return Helper.isModified(ApplicationState.getCurrent().editProfileInfo,ApplicationState.getCurrent().getProfileInfo());
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        mnuSave=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_save);
        mnuSave.setIcon(getResources().getDrawable(R.drawable.content_save)) ;
//        mnuSave.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setShowAsAction(mnuSave,MenuItem.SHOW_AS_ACTION_ALWAYS);

        mnuRemovePicture=menu.add(Menu.NONE,2,Menu.NONE,R.string.button_remove_picture);
//        mnuRemovePicture.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        MenuItemCompat.setShowAsAction(mnuRemovePicture,MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuSave)
        {
             if(!txtPassword.getText().toString().equals(txtConfirm.getText().toString()))
             {
                 BAMessageBox.ShowToast(R.string.create_profile_err_wrong_passwords);
                 return true;
             }

            progressDlg=BAMessageBox.ShowProgressDlg(this,R.string.progress_saving);
            //saveProfile();
            if(newBitmap!=null)
            {
                uploadPictureAndSaveProfile();
            }
            else
            {
                saveProfile();
            }
        }
        else if(item==mnuRemovePicture)
        {
            ApplicationState.getCurrent().editProfileInfo.user.picture=null;
            imgPicture.fill(null);
        }
        return true;
    }

    private void uploadPictureAndSaveProfile()
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                if(ApplicationState.getCurrent().editProfileInfo!=null)
                {
                    ApplicationState.getCurrent().editProfileInfo.user.picture= (PictureInfoDTO) Data;
                    saveProfile();
                }
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                stopProgress();
                Mint.logException(ex);
                BAMessageBox.ShowToast(R.string.err_cannot_send_picture);
            }

            @Override
            public void Wsdl2CodeEndedRequest() { }
        });
        try {

            service.UploadImageAsync(ApplicationState.getCurrent().editProfileInfo.user.picture,newBitmap);
        } catch (Exception e) {
            Mint.logException(e);
            BAMessageBox.ShowToast(R.string.err_cannot_send_picture);
        }
    }
    private void saveProfile() {
        ProfileInformationDTO edited=ApplicationState.getCurrent().editProfileInfo;
        ProfileUpdateData data = new ProfileUpdateData();
        data.profile=Helper.Copy(ApplicationState.getCurrent().getSessionData().profile);
        data.profile.version=edited.user.version;
        data.profile.aboutInformation=edited.aboutInformation;
        data.profile.birthday=edited.birthday;
        data.profile.gender=edited.user.gender;
        data.profile.countryId=edited.user.countryId;
        data.profile.settings=edited.settings;
        data.wymiary=edited.wymiary;
        if(!TextUtils.isEmpty(txtPassword.getText().toString()))
        {
            data.profile.password= SecurityHelper.SHA1(txtPassword.getText().toString());
        }

        data.profile.privacy=edited.user.privacy;
        data.profile.picture=edited.user.picture;

        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(this);
        try {
            service.UpdateProfileAsync(data);
        } catch (Exception e) {
            Mint.logException(e);
            BAMessageBox.ShowToast(R.string.err_cannot_update_profile);
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() { }


    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        ApplicationState.getCurrent().setProfileInfo( ApplicationState.getCurrent().editProfileInfo);

        ApplicationState.getCurrent().getSessionData().profile = (ProfileDTO) Data;
        ApplicationState.getCurrent().getProfileInfo().user.version= ((ProfileDTO) Data).version;

        if (!TextUtils.isEmpty(txtPassword.getText().toString()))
        {//store new password
            Settings.setPassword(txtPassword.getText().toString());

        }
        finish();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        Mint.logException(ex);
        BAMessageBox.ShowToast(R.string.err_cannot_update_profile);
    }

    void stopProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        stopProgress();
    }
}
