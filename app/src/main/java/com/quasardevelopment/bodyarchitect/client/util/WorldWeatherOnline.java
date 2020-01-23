package com.quasardevelopment.bodyarchitect.client.util;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WeatherDTO;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 11.07.13
 * Time: 06:55
 * To change this template use File | Settings | File Templates.
 */
public class WorldWeatherOnline {

    static final String API_KEY = "c0dc594ca4185715132103";

    protected WeatherDTO makeRequest(String URL){
        WeatherDTO forecast = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpclient.execute(new HttpGet(URL));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                forecast = parseResponse(responseString);
            } else {
                //Utility.toast("Request of weather forecast failed ! HTTP status code: " + statusLine.getStatusCode());
                response.getEntity().getContent().close();
            }
        } catch (Exception e) {
            //Utility.toast(e.toString());
            e.printStackTrace();
        }
        return forecast;
    }

    public WeatherDTO requestWeatherForecast(float lat, float lon, int numOfDays) {

        String URL = "http://free.worldweatheronline.com/feed/weather.ashx?q=" + lat + "," + lon + "&format=json&num_of_days=" + numOfDays + "&key=" + API_KEY;

        return makeRequest(URL);
    }

    public WeatherDTO requestWeatherForecast(String city, int numOfDays) {
        String URL = "http://free.worldweatheronline.com/feed/weather.ashx?q=" + city + "&format=json&num_of_days=" + numOfDays + "&key=" + API_KEY;

        return makeRequest(URL);
    }

    protected WeatherDTO parseResponse(String response) throws JSONException {
        WeatherDTO parsedForecast = new WeatherDTO();
        JSONObject jResponse = new JSONObject(response).getJSONObject("data");

        final JSONObject jCurrentCondition = jResponse.getJSONArray("current_condition").getJSONObject(0);

        parsedForecast.temperature= (float)jCurrentCondition.getDouble("temp_C");

        parsedForecast.condition= Helper.SingleOrDefault(filter(new Predicate<WS_Enums.WeatherCondition>() {
            public boolean apply(WS_Enums.WeatherCondition item) {
                try {
                    return item.getCode()==jCurrentCondition.getInt("weatherCode");
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return false;
            }
        }, WS_Enums.WeatherCondition.values()));

        return parsedForecast;
    }

}