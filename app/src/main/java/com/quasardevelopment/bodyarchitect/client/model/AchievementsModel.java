package com.quasardevelopment.bodyarchitect.client.model;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 13.06.13
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class AchievementsModel
{
    public enum AchievementRank
    {
        Rank1(0),
        Rank2(1),
        Rank3(3),
        Rank4(6),
        Rank5(12),
        Rank6(24)      ;

        private int code;

        AchievementRank(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }
    }

    public enum AchievementStar
    {
        None,
        Bronze,
        Silver,
        Gold
    }

    public enum AchievementCategory
    {
        Sport,
        Famous,
        Social
    }

    public static class Achievements
    {
        static public HashMap<AchievementRank,Integer> GetTrainingDaysInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,1000);
            map.put(AchievementRank.Rank5,600);
            map.put(AchievementRank.Rank4,300);
            map.put(AchievementRank.Rank3,150);
            map.put(AchievementRank.Rank2,50);
            return  map;
        }

        static public HashMap<AchievementRank,Integer> GetFollowersInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,100);
            map.put(AchievementRank.Rank5,40);
            map.put(AchievementRank.Rank4,20);
            map.put(AchievementRank.Rank3,6);
            map.put(AchievementRank.Rank2,3);
            return  map;
        }

        static public HashMap<AchievementRank,Integer> GetSupplementsDefinitionsInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,40);
            map.put(AchievementRank.Rank5,20);
            map.put(AchievementRank.Rank4,10);
            map.put(AchievementRank.Rank3,5);
            map.put(AchievementRank.Rank2,1);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetWorkoutPlansInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,40);
            map.put(AchievementRank.Rank5,20);
            map.put(AchievementRank.Rank4,10);
            map.put(AchievementRank.Rank3,5);
            map.put(AchievementRank.Rank2,1);
            return  map;
        }

        static public HashMap<AchievementRank,Integer> GetBlogCommentsInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,400);
            map.put(AchievementRank.Rank5,250);
            map.put(AchievementRank.Rank4,100);
            map.put(AchievementRank.Rank3,50);
            map.put(AchievementRank.Rank2,20);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetMyBlogCommentsInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,800);
            map.put(AchievementRank.Rank5,400);
            map.put(AchievementRank.Rank4,200);
            map.put(AchievementRank.Rank3,100);
            map.put(AchievementRank.Rank2,50);
            return  map;
        }

        static public HashMap<AchievementRank,Integer> GetVotingsInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,200);
            map.put(AchievementRank.Rank5,100);
            map.put(AchievementRank.Rank4,50);
            map.put(AchievementRank.Rank3,25);
            map.put(AchievementRank.Rank2,10);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetFriendsInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,100);
            map.put(AchievementRank.Rank5,40);
            map.put(AchievementRank.Rank4,20);
            map.put(AchievementRank.Rank3,8);
            map.put(AchievementRank.Rank2,4);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetStrengthTrainingEntriesInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,1000);
            map.put(AchievementRank.Rank5,400);
            map.put(AchievementRank.Rank4,200);
            map.put(AchievementRank.Rank3,100);
            map.put(AchievementRank.Rank2,30);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetBlogEntriesInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,1000);
            map.put(AchievementRank.Rank5,400);
            map.put(AchievementRank.Rank4,200);
            map.put(AchievementRank.Rank3,100);
            map.put(AchievementRank.Rank2,50);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetSupplementEntriesInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,300);
            map.put(AchievementRank.Rank5,150);
            map.put(AchievementRank.Rank4,100);
            map.put(AchievementRank.Rank3,60);
            map.put(AchievementRank.Rank2,30);
            return  map;
        }

        static public HashMap<AchievementRank,Integer> GetSizeEntriesInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,300);
            map.put(AchievementRank.Rank5,150);
            map.put(AchievementRank.Rank4,100);
            map.put(AchievementRank.Rank3,60);
            map.put(AchievementRank.Rank2,30);
            return  map;
        }

        static public HashMap<AchievementRank,Integer> GetA6WEntriesInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,165);
            map.put(AchievementRank.Rank5,125);
            map.put(AchievementRank.Rank4,85);
            map.put(AchievementRank.Rank3,40);
            map.put(AchievementRank.Rank2,10);
            return  map;

        }

        static public HashMap<AchievementRank,Integer> GetA6WFullCyclesInfo()
        {
            HashMap<AchievementRank,Integer> map = new HashMap<AchievementRank, Integer>();
            map.put(AchievementRank.Rank6,5);
            map.put(AchievementRank.Rank5,4);
            map.put(AchievementRank.Rank4,3);
            map.put(AchievementRank.Rank3,2);
            map.put(AchievementRank.Rank2,1);
            return  map;
        }

        static public AchievementRank GetA6WFullCyclesCount(UserSearchDTO user)
        {
            return GetRank(GetA6WFullCyclesInfo(), user.statistics.a6WFullCyclesCount);
        }

        static public AchievementRank GetA6WEntriesCount(UserSearchDTO user)
        {
            return GetRank(GetA6WEntriesInfo(), user.statistics.a6WEntriesCount);
        }

        static public AchievementRank GetSizeEntriesCount(UserSearchDTO user)
        {
            return GetRank(GetSizeEntriesInfo(), user.statistics.sizeEntriesCount);
        }

        static public AchievementRank GetSupplementsEntriesCount(UserSearchDTO user)
        {
            return GetRank(GetSupplementEntriesInfo(), user.statistics.supplementEntriesCount);
        }

        static public AchievementRank GetBlogEntriesCount(UserSearchDTO user)
        {
            return GetRank(GetBlogEntriesInfo(), user.statistics.blogEntriesCount);
        }

        static public AchievementRank GetStrengthTrainingEntriesCount(UserSearchDTO user)
        {
            return GetRank(GetStrengthTrainingEntriesInfo(), user.statistics.strengthTrainingEntriesCount);
        }

        static public AchievementRank GetFriendsCount(UserSearchDTO user)
        {
            return GetRank(GetFriendsInfo(), user.statistics.friendsCount);
        }

        static public AchievementRank GetTrainingDaysCount(UserSearchDTO user)
        {
            return GetRank(GetTrainingDaysInfo(), user.statistics.trainingDaysCount);
        }

        static public AchievementRank GetFollowersCount(UserSearchDTO user)
        {
            return GetRank(GetFollowersInfo(), user.statistics.followersCount);
        }

        static public AchievementRank GetSupplementsDefinitionsCount(UserSearchDTO user)
        {
            return GetRank(GetSupplementsDefinitionsInfo(), user.statistics.supplementsDefinitionsCount);
        }

        static public AchievementRank GetWorkoutPlansCount(UserSearchDTO user)
        {
            return GetRank(GetWorkoutPlansInfo(), user.statistics.workoutPlansCount);
        }

        static public AchievementRank GetTrainingDayCommentsCount(UserSearchDTO user)
        {
            return GetRank(GetBlogCommentsInfo(), user.statistics.trainingDayCommentsCount);
        }

        static public AchievementRank GetMyTrainingDayCommentsCount(UserSearchDTO user)
        {
            return GetRank(GetMyBlogCommentsInfo(), user.statistics.myTrainingDayCommentsCount);
        }

        static public AchievementRank GetVotingsCount(UserSearchDTO user)
        {
            return GetRank(GetVotingsInfo(), user.statistics.votingsCount);
        }

        static private AchievementRank GetRank(HashMap<AchievementRank,Integer> rankInfo, int value)
        {
            for (Map.Entry<AchievementRank,Integer> info: rankInfo.entrySet())
            {
                if (value>=info.getValue())
                {
                    return info.getKey();
                }
            }
            return AchievementRank.Rank1;
        }


        public static AchievementStar GetRedStar(UserSearchDTO user)
        {
            if(user==null)
            {
                return AchievementStar.None;
            }
            AchievementRank rankTrainingDays=GetTrainingDaysCount(user);
            AchievementRank rankStrenghtEntry = GetStrengthTrainingEntriesCount(user);
            AchievementRank rankSizeEntry = GetSizeEntriesCount(user);
            AchievementRank rankSupplementEntry = GetSupplementsEntriesCount(user);
            AchievementRank rankA6WCycles = GetA6WFullCyclesCount(user);

            int sum = rankA6WCycles.getCode() + rankSupplementEntry.getCode() + rankTrainingDays.getCode() + rankSizeEntry.getCode() + rankStrenghtEntry.getCode();
            return getStar(sum);
        }

        public static AchievementStar GetBlueStar(UserSearchDTO user)
        {
            if(user==null)
            {
                return AchievementStar.None;
            }
            AchievementRank rankFollowers = GetFollowersCount(user);
            AchievementRank rankFriends = GetFriendsCount(user);
            AchievementRank rankMyBlogComments = GetMyTrainingDayCommentsCount(user);
            AchievementRank rankBlogEntries = GetBlogEntriesCount(user);

            int sum = rankFollowers.getCode() + rankFriends.getCode() + rankMyBlogComments.getCode() + rankBlogEntries.getCode();
            return getStar(sum);
        }

        private static AchievementStar getStar(int sum)
        {
            if (sum >= 72)
            {
                return AchievementStar.Gold;
            }
            if (sum >= 36)
            {
                return AchievementStar.Silver;
            }
            if (sum >= 9)
            {
                return AchievementStar.Bronze;
            }
            return AchievementStar.None;
        }

        public static AchievementStar GetGreenStar(UserSearchDTO user)
        {
            if(user==null)
            {
                return AchievementStar.None;
            }
            AchievementRank rankVotings = GetVotingsCount(user);
            AchievementRank rankWorkoutPlans = GetWorkoutPlansCount(user);
            AchievementRank rankSupplementsDefinitions = GetSupplementsDefinitionsCount(user);
            AchievementRank rankBlogComments = GetTrainingDayCommentsCount(user);

            int sum = rankVotings.getCode() + 3*rankWorkoutPlans.getCode() + rankBlogComments.getCode() + 3*rankSupplementsDefinitions.getCode();
            return getStar(sum);
        }

    }
}
