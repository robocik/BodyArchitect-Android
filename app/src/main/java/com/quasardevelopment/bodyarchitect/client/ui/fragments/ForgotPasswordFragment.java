package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.splunk.mint.Mint;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 05.06.13
 * Time: 07:55
 * To change this template use File | Settings | File Templates.
 */
public class ForgotPasswordFragment extends DialogFragment implements IWsdl2CodeEvents,TextView.OnEditorActionListener {
    private EditText txtEmail;
    private LinearLayout inputPane;
    private LinearLayout progressPane;

    public ForgotPasswordFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_dialog, container);

        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        getDialog().setTitle(getString(R.string.forgot_password_title));

        inputPane = (LinearLayout)view.findViewById(R.id.pnlMain);
        progressPane = (LinearLayout)view.findViewById(R.id.progressPane);
        Button btnSend= (Button) view.findViewById(R.id.btnSend);
        Button btnCancel= (Button) view.findViewById(R.id.btnCancel);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    void showProgress(boolean show)
    {
        inputPane.setVisibility(show?View.GONE:View.VISIBLE);
        progressPane.setVisibility(show?View.VISIBLE:View.GONE);
    }

    void sendEmail()
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(ForgotPasswordFragment.this);
        try {
            service.AccountOperationAsync(txtEmail.getText().toString(), WS_Enums.AccountOperationType.RestorePassword);
        } catch (Exception e) {
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            sendEmail();
            return true;
        }
        return false;
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        showProgress(true);
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        BAMessageBox.ShowToast(R.string.forgot_password_success);
        this.dismiss();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        Mint.logException(ex);
        BAMessageBox.ShowToast(R.string.err_unhandled);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        showProgress(false);
    }
}
