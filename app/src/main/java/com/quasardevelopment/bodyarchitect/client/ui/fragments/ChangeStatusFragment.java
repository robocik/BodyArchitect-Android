package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 05.06.13
 * Time: 09:26
 * To change this template use File | Settings | File Templates.
 */
public class ChangeStatusFragment extends DialogFragment implements IWsdl2CodeEvents {
    private EditText txtStatus;

    public ChangeStatusFragment() {
        // Empty constructor required for DialogFragment
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_status, container);
        txtStatus = (EditText) view.findViewById(R.id.txtStatus);

        Bundle params=getArguments();
        String text=params.getString("Status");
        txtStatus.setText(text!=null?text:"");
        getDialog().setTitle(R.string.change_status_fragment_title);

        Button btnSend= (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStatus();
            }
        });
        Button btnCancel= (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    private void changeStatus() {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(ChangeStatusFragment.this);
        try {
            ProfileOperationParam param = new ProfileOperationParam();
            param.operation = WS_Enums.ProfileOperation.SetStatus;
            param.profileId = ApplicationState.getCurrent().getSessionData().profile.globalId;
            param.status = new ProfileStatusDTO();
            param.status.status=txtStatus.getText().toString();
            service.ProfileOperationAsync(param);
            //we don't wait for changing status. we assume that this operation will succeed
            Intent result = new Intent();
            result.putExtra("Status",txtStatus.getText().toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(),1,result);
            this.dismiss();
        } catch (Exception e) {
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }


    @Override
    public void Wsdl2CodeStartedRequest() {
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {

//        getTargetFragment().onActivityResult(getTargetRequestCode(),1,null);
//
//        this.dismiss();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.err_unhandled);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
    }
}
