package com.quasardevelopment.bodyarchitect.client.ui.activities;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.StrengthTrainingSetsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.StrengthTrainingSetsAdapterBase;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 04.05.13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class StrengthWorkoutItemActivity extends StrengthTrainingItemActivityBase
{

    @Override
    protected StrengthTrainingSetsAdapterBase createAdapter() {
        return new StrengthTrainingSetsAdapter(this, R.layout.strength_training_set_row, selectedItem.series);
    }

    @Override
    protected Class GetSetActivityType() {
        return SetActivity.class;
    }


}
