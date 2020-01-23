package com.quasardevelopment.bodyarchitect.client.ui.activities;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.CardioTrainingSessionsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.StrengthTrainingSetsAdapterBase;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 17.05.13
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public class CardioTrainingItemActivity extends StrengthTrainingItemActivityBase
{

    @Override
    protected StrengthTrainingSetsAdapterBase createAdapter() {
        return new CardioTrainingSessionsAdapter(this, R.layout.cardio_training_session_row, selectedItem.series);
    }

    @Override
    protected Class GetSetActivityType() {
        return CardioSetActivity.class;
    }

    protected String getEmptyMessage()
    {
        return getString(R.string.cardio_set_empty_list);
    }
}
