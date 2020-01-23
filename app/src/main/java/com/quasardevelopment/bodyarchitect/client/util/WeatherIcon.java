package com.quasardevelopment.bodyarchitect.client.util;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 13.07.13
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class WeatherIcon
{
    static public String GetIcon(WS_Enums.WeatherCondition condition)
    {
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavySnowWithThunder))
        {
            return "weather_moderate_or_heavy_snow_with_thunder";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyLightSnowWithThunder))
        {
            return "weather_patchy_light_snow_with_thunder";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavyRainAreaWithThunder))
        {
            return "weather_moderate_or_heavy_snow_with_thunder";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyLightRainWithThunder))
        {
            return "weather_patchy_light_rain_with_thunder";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavyShowersOfIcePellets))
        {
            return "weather_moderate_or_heavy_showers_of_ice_pellets";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightShowersOfIcePellets))
        {
            return "weather_light_showers_of_ice_pellets";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavySnowShowers))
        {
            return "weather_moderate_or_heavy_snow_showers";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightSnowShowers))
        {
            return "weather_light_snow_showers";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavySleetShowers))
        {
            return "weather_moderate_or_heavy_showers_of_ice_pellets";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightSleetShowers))
        {
            return "weather_moderate_or_heavy_showers_of_ice_pellets";
        }
        if(condition.equals(WS_Enums.WeatherCondition.TorrentialRainShower))
        {
            return "weather_torrential_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavyRainShower))
        {
            return "weather_moderate_or_heavy_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightRainShower))
        {
            return "weather_light_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.IcePellets))
        {
            return "weather_moderate_or_heavy_showers_of_ice_pellets";
        }
        if(condition.equals(WS_Enums.WeatherCondition.HeavySnow))
        {
            return "weather_heavy_snow";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyHeavySnow))
        {
            return "weather_heavy_snow";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateSnow))
        {
            return "weather_moderate_snow";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyModerateSnow))
        {
            return "weather_moderate_snow";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightSnow))
        {
            return "weather_light_snow_showers";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyLightSnow))
        {
            return "weather_light_snow_showers";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavySleet))
        {
            return "weather_moderate_or_heavy_showers_of_ice_pellets";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightSleet))
        {
            return "weather_light_sleet";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateOrHeavyFreezingRain))
        {
            return "weather_moderate_or_heavy_freezing_rain";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightFreezingRain))
        {
            return "weather_moderate_or_heavy_freezing_rain";
        }
        if(condition.equals(WS_Enums.WeatherCondition.HeavyRain))
        {
            return "weather_torrential_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.HeavyRainAtTimes))
        {
            return "weather_heavy_rain_at_times";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateRain))
        {
            return "weather_moderate_or_heavy_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ModerateRainAtTimes))
        {
            return "weather_moderate_or_heavy_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightRain))
        {
            return "weather_light_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyLightRain))
        {
            return "weather_light_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.HeavyFreezingDrizzle))
        {
            return "weather_heavy_freezing_drizzle";
        }
        if(condition.equals(WS_Enums.WeatherCondition.FreezingDrizzle))
        {
            return "weather_heavy_freezing_drizzle";
        }
        if(condition.equals(WS_Enums.WeatherCondition.LightDrizzle))
        {
            return "weather_light_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyLightDrizzle))
        {
            return "weather_light_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.FreezingFog))
        {
            return "weather_fog";
        }
        if(condition.equals(WS_Enums.WeatherCondition.Fog))
        {
            return "weather_fog";
        }
        if(condition.equals(WS_Enums.WeatherCondition.Blizzard))
        {
            return "weather_blizzard";
        }
        if(condition.equals(WS_Enums.WeatherCondition.BlowingSnow))
        {
            return "weather_heavy_snow";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ThunderyOutbreaksNearby))
        {
            return "weather_patchy_light_rain_with_thunder";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyFreezingDrizzleNearby))
        {
            return "weather_light_sleet";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchySleet))
        {
            return "weather_light_sleet";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchySnow))
        {
            return "weather_light_snow_showers";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PatchyRain))
        {
            return "weather_light_rain_shower";
        }
        if(condition.equals(WS_Enums.WeatherCondition.Mist))
        {
            return "weather_fog";
        }
        if(condition.equals(WS_Enums.WeatherCondition.Overcast))
        {
            return "weather_cloudy";
        }
        if(condition.equals(WS_Enums.WeatherCondition.Cloudy))
        {
            return "weather_cloudy";
        }
        if(condition.equals(WS_Enums.WeatherCondition.PartlyCloudy))
        {
            return "weather_partly_cloudy";
        }
        if(condition.equals(WS_Enums.WeatherCondition.ClearSunny))
        {
            return "weather_clear_sunny";
        }
        return null;
    }
}
