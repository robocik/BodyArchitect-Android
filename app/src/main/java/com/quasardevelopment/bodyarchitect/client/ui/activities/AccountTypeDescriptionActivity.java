package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.AccountTypeDescriptionsAdapter;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 09.07.13
 * Time: 08:40
 * To change this template use File | Settings | File Templates.
 */
public class AccountTypeDescriptionActivity extends BANormalActivityBase
{
    ListView lstList;
    private TextView tbDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_account_type_description, null);
        setMainContent(inflate);

        lstList = (ListView) findViewById(R.id.lstList);
        //tbDescription = (TextView) findViewById(R.id.tbDescription);

        tbDescription=new TextView(this);
        tbDescription.setPadding(0,0,0, Helper.toDp(24));
        lstList.addHeaderView(tbDescription);
        lstList.setHeaderDividersEnabled(true);

        Intent intent=getIntent();
        WS_Enums.AccountType accountType= (WS_Enums.AccountType) intent.getSerializableExtra("AccountType");
        if(accountType.equals(WS_Enums.AccountType.User))
        {
            createBasicAccountDescription();
        }
        else if(accountType.equals(WS_Enums.AccountType.PremiumUser) )
        {
            createPremiumAccountDescription();
        }
        else
        {
            createInstructorAccountDescription();
        }



        getSupportActionBar().setSubtitle(R.string.account_type_description_title);
    }

    private void createBasicAccountDescription()
    {
        setTitle(EnumLocalizer.Translate(WS_Enums.AccountType.User));

        ArrayList<AccountTypeDescriptionsAdapter.AccountTypeItem> items = new ArrayList<AccountTypeDescriptionsAdapter.AccountTypeItem>();
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_unlimited_entries_in_calendar,R.string.feature_basic_unlimited_entries_in_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_exercises_database,R.string.feature_basic_exercises_database_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_workout_plans_database,R.string.feature_basic_workout_plans_database_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_supplements_cycle_definitions,R.string.feature_basic_supplements_cycle_definitions_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_measurements_in_calendar,R.string.feature_basic_measurements_in_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_blog_in_calendar,R.string.feature_basic_blog_in_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_supplements_in_calendar,R.string.feature_basic_supplements_in_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_a6w_in_calendar,R.string.feature_basic_a6w_in_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_access_to_other_users_calendar,R.string.feature_basic_access_to_other_users_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_search_users,R.string.feature_basic_search_users_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_basic_exercises_records,R.string.feature_basic_exercises_records_description));

        tbDescription.setText(R.string.change_account_type_fragment_basic_account_description);
        AccountTypeDescriptionsAdapter adapter = new AccountTypeDescriptionsAdapter(this,R.id.tbName,items);
        lstList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void createPremiumAccountDescription()
    {
        setTitle(EnumLocalizer.Translate(WS_Enums.AccountType.PremiumUser));

        ArrayList<AccountTypeDescriptionsAdapter.AccountTypeItem> items = new ArrayList<AccountTypeDescriptionsAdapter.AccountTypeItem>();
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_reports,R.string.feature_premium_reports_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_creating_exercises,R.string.feature_premium_creating_exercises_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_creating_workout_plans,R.string.feature_premium_creating_workout_plans_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_creating_supplements_definitions,R.string.feature_premium_creating_supplements_definitions_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_supports_my_trainings_for_supplements,R.string.feature_premium_supports_my_trainings_for_supplements_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_add_entries_in_future,R.string.feature_premium_add_entries_in_future_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_reminders,R.string.feature_premium_reminders_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_private_calendar,R.string.feature_premium_private_calendar_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_printing,R.string.feature_premium_printing_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_my_places,R.string.feature_premium_my_places_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_equipments,R.string.feature_premium_equipments_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_strength_training_timer,R.string.feature_premium_strength_training_timer_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_advanced_strength_training,R.string.feature_premium_advanced_strength_training_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_no_ads,R.string.feature_premium_no_ads_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_premium_more_features,R.string.feature_premium_more_features_description));

        tbDescription.setText(R.string.change_account_type_fragment_premium_account_description);
        AccountTypeDescriptionsAdapter adapter = new AccountTypeDescriptionsAdapter(this,R.id.tbName,items);
        lstList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void createInstructorAccountDescription()
    {
        setTitle(EnumLocalizer.Translate(WS_Enums.AccountType.Instructor));

        ArrayList<AccountTypeDescriptionsAdapter.AccountTypeItem> items = new ArrayList<AccountTypeDescriptionsAdapter.AccountTypeItem>();
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_instructor_customers,R.string.feature_instructor_customers_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_instructor_calendar_for_customers,R.string.feature_instructor_calendar_for_customers_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_instructor_schedule_entries,R.string.feature_instructor_schedule_entries_description));
        items.add(new AccountTypeDescriptionsAdapter.AccountTypeItem(R.string.feature_instructor_championships,R.string.feature_instructor_championships_description));

        tbDescription.setText(R.string.change_account_type_fragment_instructor_account_description);
        AccountTypeDescriptionsAdapter adapter = new AccountTypeDescriptionsAdapter(this,R.id.tbName,items);
        lstList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
