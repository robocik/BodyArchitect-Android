package com.quasardevelopment.bodyarchitect.client.ui;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ValidationException;


public class BAMessageBox
{
    public static void ShowError(int message,Context context)
    {
        if(context==null)
        {
            context=MyApplication.getAppContext();
        }
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setTitle(R.string.html_app_name);
        dlgAlert.setMessage(message);
        dlgAlert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //dlgAlert.setPositiveButton("OK",);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static void ShowInfo(int message,Context context)
    {
        if(context==null)
        {
            context=MyApplication.getAppContext();
        }
        ShowInfo(context.getString(message),context);
    }

    public static void ShowInfo(String message,Context context)
    {
        if(context==null)
        {
            context=MyApplication.getAppContext();
        }
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setTitle(R.string.html_app_name);
        dlgAlert.setMessage(message);
        dlgAlert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static void ShowToast(int message)
    {
        Context ctx=MyApplication.getAppContext();
        Toast.makeText(ctx, ctx.getString(message), Toast.LENGTH_LONG).show();
    }

    public static void ShowToast(String message)
    {
        Context ctx=MyApplication.getAppContext();
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }



    public static ProgressDialog ShowProgressDlg(Context ctx,int text)
    {

        ProgressDialog dlg= ProgressDialog.show(ctx, ctx.getString(R.string.html_app_name), ctx.getString(text));
        dlg.setTitle(R.string.html_app_name);
        return dlg;
    }

    public static void ShowValidationError(ValidationException ex) {
        Context ctx=MyApplication.getAppContext();
        Toast.makeText(ctx, "Validation error", Toast.LENGTH_LONG).show();//todo:translate or show validation results
    }
}
