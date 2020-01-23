package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.CacheKey;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ObjectNotFoundException;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDaysHolder;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.ForgotPasswordFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Constants;
import com.quasardevelopment.bodyarchitect.client.util.SecurityHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GetProfileInformationCriteria;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ProfileInformationDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SessionData;
import com.quasardevelopment.bodyarchitect.client.wcf.envelopes.BAService;
import com.quasardevelopment.bodyarchitect.client.wcf.envelopes.WcfResult;
import com.splunk.mint.Mint;

import java.util.HashMap;

public class LoginActivity extends BAActivityBase implements OnClickListener {

	Button btnLogin;
	EditText txtUserName,txtPassword;
	Spinner cmbServers;
    ProgressBar progressBar;
    LinearLayout inputPane,progressPane;
    TextView tbProgress;
    Button btnRegister;


    @Override
    protected void EnsureLogged()
    {
    }

    private void buildUI() {



        setContentView(R.layout.activity_login);

        inputPane = (LinearLayout)findViewById(R.id.Login_InputPane);
        progressPane = (LinearLayout)findViewById(R.id.progressPane);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tbProgress = (TextView)findViewById(R.id.tbProgress);
        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        cmbServers=  (Spinner)findViewById(R.id.cmbServers);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister= (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    login();
                }
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        fillServers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_login_create_account:
                Intent intent = new Intent(this,CreateProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_login_forgot_password:
                forgotPassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void forgotPassword()
    {
        FragmentManager fm = getSupportFragmentManager();
        ForgotPasswordFragment dlg = new ForgotPasswordFragment();
        dlg.setStyle(DialogFragment.STYLE_NORMAL,R.style.BADialog);
        dlg.show(fm, "fragment_edit_name");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);


        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.login_my_title, null);

        ActionBar.LayoutParams param = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(v,param);
        Drawable bgColor=new ColorDrawable(getResources().getColor(R.color.main_bg));
        actionBar.setBackgroundDrawable(bgColor);
        actionBar.setStackedBackgroundDrawable(bgColor);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
//        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflator.inflate(R.layout.mytitle, null);
//
//        ActionBar.LayoutParams param = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//        actionBar.setCustomView(v,param);
//        actionBar.setBackgroundDrawable(null);

        buildUI();



//        System.setProperty("http.keepAlive", "false");


        if (Settings.getUserName()!=null && Settings.getPassword()!=null)
        {   //credentials are already stored in settings so login automatically

            txtPassword.setText(Settings.getPassword());
            txtUserName.setText(Settings.getUserName());
            login();
        }
    }

	public void onClick(View v)
	{
        if(v==btnLogin)
        {
            login();
        }
        else
        {
            Intent intent = new Intent(LoginActivity.this,CreateProfileActivity.class);
            startActivity(intent);
        }

	}

    public static void loginImplementation(SessionData sessionData,String username,String pwdHash,String password)
    {
        ApplicationState state = ApplicationState.loadState();
        ObjectsReposidory.loadState();
        if (state == null)
        {
            state = new ApplicationState();
        }
        else if (state.getTempUserName()==null || !state.getTempUserName().equals(username))
        {
            //if we log as a different user, we should't use cache
            ApplicationState.ClearOffline();
            state = new ApplicationState();
        }
        ApplicationState.setCurrent(state);
        state.isOffline=false;
        state.setSessionData(sessionData);
        state.setTempUserName(username);
        state.setTempPassword(pwdHash);
        SessionData session=state.getSessionData();

        Settings.setUserName(username);
        Settings.setPassword(password);


    }

    void login()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);

        progressPane.setVisibility(View.VISIBLE);
        inputPane.setVisibility(View.GONE);
        tbProgress.setText(R.string.calendar_view_progress_logging);
        try{
            final String username=txtUserName.getText().toString();
            final String pwdHash=SecurityHelper.SHA1(txtPassword.getText().toString());
            final String password=txtPassword.getText().toString();

            ApplicationState.setCurrent(null);

            BAService service = new BAService(new IWsdl2CodeEvents() {
                @Override
                public void Wsdl2CodeStartedRequest() {}

                @Override
                public void Wsdl2CodeFinished(String methodName, Object Data)
                {
                    SessionData sessionData =( SessionData)Data;
                    if(sessionData==null)
                    {   //login failed
                        loginFailed(false, R.string.err_login_failed);
                        Settings.setUserName(null);
                        Settings.setPassword(null);
                        return;
                    }
                    else
                    {
                        loginImplementation(sessionData,username,pwdHash,password);


                        getProfileInformation();
                    }
                }

                @Override
                public void Wsdl2CodeFinishedWithException(Exception ex)
                {
                    Mint.logException(ex);
                    loginFailed(true,R.string.err_login_unhandled);
                }

                @Override
                public void Wsdl2CodeEndedRequest() {}
            });
            service.Login(username, pwdHash);

        }
        catch(NetworkErrorException ex)
        {
            loginFailed(true, R.string.err_login_unhandled);
        }
        catch(Exception ex)
        {
            Mint.logException(ex);
            loginFailed(true,R.string.err_login_unhandled);
        }
    }

    void loginFailed(boolean tryToOfflineMode,int errorMessage)
    {
        if(tryToOfflineMode)
        {
             goToOffline(true);
        }
        else
        {

            progressPane.setVisibility(View.GONE);
            inputPane.setVisibility(View.VISIBLE);
            BAMessageBox.ShowToast(errorMessage);
        }
    }

    private void goToOffline(final boolean automatic)
    {

        new AsyncTask<Void, Void, WcfResult<Void>>(){
            @Override
            protected void onPreExecute()
            {
                progressPane.setVisibility(View.VISIBLE);
                inputPane.setVisibility(View.GONE);
                tbProgress.setText( R.string.login_progress_offline_mode_start);
            };
            @Override
            protected WcfResult<Void> doInBackground(Void... params) {
                WcfResult<Void> result = new WcfResult<Void>();
                try
                {
                    ApplicationState.goToOfflineMode();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    result.Exception=ex;
                }
                return result;

            }
            @Override
            protected void onPostExecute(WcfResult<Void> result)
            {

                if(result.Exception==null)
                {

                    goToMainActivity();
                    return;
                }
                else  if(result.Exception instanceof ObjectNotFoundException)
                {
                    if (automatic)
                    {
                        BAMessageBox.ShowToast(R.string.err_login_unhandled);
                    }
                    else
                    {
                        BAMessageBox.ShowInfo(R.string.login_err_go_offline_must_login_first,LoginActivity.this);
                    }
                }
                else
                {
                    if (automatic)
                    {
                        BAMessageBox.ShowToast(R.string.err_login_unhandled);
                    }
                    else
                    {
                        BAMessageBox.ShowError(R.string.login_err_go_offline_mode,LoginActivity.this);
                    }
                }
                progressPane.setVisibility(View.GONE);
                inputPane.setVisibility(View.VISIBLE);

            }
        }.execute();
    }

    public static void getProfileInformationImplementation(ProfileInformationDTO profileInfo)
    {

        ApplicationState appState=ApplicationState.getCurrent();
        appState.setProfileInfo(profileInfo);
        //todo:reminders: ApplicationState.Current.Cache.Reminders.EnsureReminders();
        if(appState.getMyDays()==null)
        {
            appState.setMyDays(new HashMap<CacheKey, TrainingDaysHolder>());
        }
        appState.setCurrentBrowsingTrainingDays(null);
        appState.clearTrainingDays();
    }

    void getProfileInformation()
    {
          try
          {
              tbProgress.setText(R.string.progress_retrieving_profile_info);
              GetProfileInformationCriteria criteria = new GetProfileInformationCriteria();
              BAService service = new BAService(new IWsdl2CodeEvents() {
                  @Override
                  public void Wsdl2CodeStartedRequest() {}

                  @Override
                  public void Wsdl2CodeFinished(String methodName, Object Data)
                  {
                      ProfileInformationDTO profileInfo = (ProfileInformationDTO) Data;
                      getProfileInformationImplementation(profileInfo);
                      goToMainActivity();
                  }

                  @Override
                  public void Wsdl2CodeFinishedWithException(Exception ex) {
                      loginFailed(true,R.string.err_retrieving_profile_info);
                  }

                  @Override
                  public void Wsdl2CodeEndedRequest() {}
              });
              service.GetProfileInformation(criteria);
          }
          catch(NetworkErrorException ex)
          {
              loginFailed(true, R.string.err_network_problem);
          }
          catch (Exception ex)
          {
              loginFailed(true,R.string.err_retrieving_profile_info);
          }
    }

    void fillServers()
    {
        if(!Constants.IsDebugMode)
        {
            cmbServers.setVisibility(View.GONE);
            Settings.setEndPoint("Production");
            return;
        }
        final String array_spinner[]=new String[4];
        array_spinner[0]="Production";
        array_spinner[1]="Localhost";
        array_spinner[2]="Test";
        array_spinner[3]="Test2";

        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbServers.setAdapter(adapter);
        cmbServers.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id) {
                        String endPoint = array_spinner[position];
                        Settings.setEndPoint(endPoint);
                        //BasicHttpBinding_IBodyArchitectAccessService.Reset();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });

        int position=adapter.getPosition(Settings.getEndPoint());
        cmbServers.setSelection(position);
    }
	
	private void goToMainActivity()
	{
		Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
        finish();
	}

    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.pnlMain));
        System.gc();
    }
}
