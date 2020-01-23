package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.AccountTypeDescriptionActivity;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 08.07.13
 * Time: 08:54
 * To change this template use File | Settings | File Templates.
 */
public class AccountTypeControl   extends LinearLayout
{
    private final TextView tbName;
    private final TextView tbPoints;
    private final Button btnMore;
    private final LinearLayout pnlMain;
    TextView tbDescription;

    public AccountTypeControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.account_type_control, this);

        tbName=(TextView)findViewById(R.id.tbName);
        tbPoints=(TextView)findViewById(R.id.tbPoints);
        btnMore =(Button)findViewById(R.id.btnMore);
        tbDescription=(TextView)findViewById(R.id.tbDescription);
        pnlMain= (LinearLayout)findViewById(R.id.pnlMain);

        ViewTreeObserver observer = tbDescription.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int maxLines = (int) tbDescription.getHeight()
                        / tbDescription.getLineHeight();
                tbDescription.setMaxLines(maxLines);
                tbDescription.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
    }

    public void Fill(final WS_Enums.AccountType accountType,int points,int descriptionId,boolean isCurrent)
    {
        tbPoints.setText(Integer.toString(points));
        tbName.setText(EnumLocalizer.Translate(accountType).toUpperCase());
        tbDescription.setText(descriptionId);
        if(isCurrent)
        {
            setBackgroundResource(R.color.semi_transparent_accent_color);
        }
        else
        {
            setBackgroundResource(android.R.color.transparent);
        }
        btnMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), AccountTypeDescriptionActivity.class);
                intent.putExtra("AccountType",accountType);
                getContext().startActivity(intent);
            }
        });
    }
}
