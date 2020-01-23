package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.AchievementsModel;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.StatisticsAdapter;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 13.06.13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsActivity extends BANormalActivityBase{

    public class StatisticItem
    {
        public String name;
        public String value;
        public int statusIcon;
        public String statusDescription;
    }

    UserSearchDTO user;
    ListView lstStatistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_statistics, null);
        setMainContent(inflate);

        lstStatistics= (ListView) findViewById(R.id.lstStatistics);

        Intent intent = getIntent();
        user= (UserSearchDTO) intent.getSerializableExtra("User");
        getSupportActionBar().setSubtitle(R.string.statistics_activity_title);
        //getSupportActionBar().setTitle(R.string.html_app_name);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<StatisticItem> items = new ArrayList<StatisticItem>();
        addStatistic(R.string.statistic_item_training_days_count, user.statistics.trainingDaysCount, AchievementsModel.Achievements.GetTrainingDaysCount(user), AchievementsModel.Achievements.GetTrainingDaysInfo(),items);
        addStatistic(R.string.statistic_item_workout_plans, user.statistics.workoutPlansCount, AchievementsModel.Achievements.GetWorkoutPlansCount(user), AchievementsModel.Achievements.GetWorkoutPlansInfo(),items);
        addStatistic(R.string.statistic_item_friends_count, user.statistics.friendsCount, AchievementsModel.Achievements.GetFriendsCount(user), AchievementsModel.Achievements.GetFriendsInfo(),items);
        addStatistic(R.string.statistic_item_followers_count, user.statistics.followersCount,AchievementsModel. Achievements.GetFollowersCount(user), AchievementsModel.Achievements.GetFollowersInfo(),items);
        addStatistic(R.string.statistic_item_votes_count, user.statistics.votingsCount,AchievementsModel. Achievements.GetVotingsCount(user), AchievementsModel.Achievements.GetVotingsInfo(),items);
        addStatistic(R.string.statistic_item_blog_comments_count, user.statistics.trainingDayCommentsCount,AchievementsModel. Achievements.GetTrainingDayCommentsCount(user), AchievementsModel.Achievements.GetBlogCommentsInfo(),items);
        addStatistic(R.string.statistic_item_my_blog_comments_count, user.statistics.myTrainingDayCommentsCount,AchievementsModel. Achievements.GetMyTrainingDayCommentsCount(user), AchievementsModel.Achievements.GetMyBlogCommentsInfo(),items);
        addStatistic(R.string.statistic_item_strength_training_entries_count, user.statistics.strengthTrainingEntriesCount,AchievementsModel. Achievements.GetStrengthTrainingEntriesCount(user), AchievementsModel.Achievements.GetStrengthTrainingEntriesInfo(),items);
        addStatistic(R.string.statistic_item_size_entries_count, user.statistics.sizeEntriesCount,AchievementsModel. Achievements.GetSizeEntriesCount(user), AchievementsModel.Achievements.GetSizeEntriesInfo(),items);
        addStatistic(R.string.statistic_item_supplements_entries_count, user.statistics.supplementEntriesCount,AchievementsModel. Achievements.GetSupplementsEntriesCount(user), AchievementsModel.Achievements.GetSupplementEntriesInfo(),items);
        addStatistic(R.string.statistic_item_blog_entries_count, user.statistics.blogEntriesCount, AchievementsModel.Achievements.GetBlogEntriesCount(user),AchievementsModel. Achievements.GetBlogEntriesInfo(),items);
        addStatistic(R.string.statistic_item_a6w_entries_count, user.statistics.a6WEntriesCount,AchievementsModel. Achievements.GetA6WEntriesCount(user), AchievementsModel.Achievements.GetA6WEntriesInfo(),items);
        addStatistic(R.string.statistic_item_a6w_full_cycles_count, user.statistics.a6WFullCyclesCount, AchievementsModel.Achievements.GetA6WFullCyclesCount(user),AchievementsModel. Achievements.GetA6WFullCyclesInfo(),items);
        addStatistic(R.string.statistic_item_supplements_definitions_count, user.statistics.supplementsDefinitionsCount, AchievementsModel.Achievements.GetSupplementsDefinitionsCount(user), AchievementsModel.Achievements.GetSupplementsDefinitionsInfo(),items);


        StatisticsAdapter adapter = new StatisticsAdapter(this,R.layout.statistic_row,items);
        lstStatistics.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void addStatistic(int textId, int value, AchievementsModel.AchievementRank rank, HashMap<AchievementsModel.AchievementRank, Integer> info,List<StatisticItem> items)
    {
        StatisticItem item = new StatisticItem();
        item.name = getString(textId).toUpperCase();
        item.value = Integer.toString(value);
        String uri = "drawable/"+rank.toString().toLowerCase();

        // int imageResource = R.drawable.icon;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        item.statusIcon = imageResource;
        item.statusDescription = getRankDescription(rank, info);
        items.add(item);
    }

    private String getRankDescription(AchievementsModel.AchievementRank rank, HashMap<AchievementsModel.AchievementRank, Integer> info)
    {
        if (rank.equals(AchievementsModel.AchievementRank.Rank1))
        {

            return String.format(getString(R.string.statistic_item_no_status_description_tooltip), EnumLocalizer.Translate(AchievementsModel.AchievementRank.Rank2), info.get(AchievementsModel.AchievementRank.Rank2));
        }
        String nextStatus = "";
        if (!rank.equals(AchievementsModel.AchievementRank.Rank6))
        {
            AchievementsModel.AchievementRank nextRank = GetNextEnumValue(rank);
            nextStatus = String.format(getString(R.string.statistic_item_next_available_status_tooltip), EnumLocalizer.Translate(nextRank), info.get(nextRank));
        }


        return String.format(getString(R.string.statistic_item_status_description_tooltip), EnumLocalizer.Translate(rank), info.get(rank), nextStatus);
    }

    static public AchievementsModel.AchievementRank GetNextEnumValue(AchievementsModel.AchievementRank value)
    {
        AchievementsModel.AchievementRank[] values = AchievementsModel.AchievementRank.values();
        for (int i = 0; i < values.length; i++) {
            AchievementsModel.AchievementRank item = values[i];
            if (item.equals(value)) {
                return values[i+1];
            }
        }
        return value;
    }

}
