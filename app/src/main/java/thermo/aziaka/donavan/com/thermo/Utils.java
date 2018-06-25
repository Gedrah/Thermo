package thermo.aziaka.donavan.com.thermo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thermo.aziaka.donavan.com.thermo.Models.Weather;

public class Utils {

    public static String createCityString(List<String> cities) {
        StringBuilder allCities = new StringBuilder();
        for (int i = 0; i < cities.size(); i++) {
            allCities.append(cities.get(i));
            if (i < cities.size() - 1) {
                allCities.append(",");
            }
        }
        return allCities.toString();
    }

    public static void saveWeatherList(List<Weather> weatherList, Context context) {
        List<String> list = new ArrayList<String>();
        for (Weather w : weatherList) {
            list.add(String.valueOf(w.getId()));
        }

        StringBuilder csvList = new StringBuilder();
        for (String s : list){
            csvList.append(s);
            csvList.append(",");
        }

        SharedPreferences sharedPref = context.getSharedPreferences("WeatherList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("WeatherList", csvList.toString());
        editor.apply();
    }

    public static void saveWidgetIds(int []ids, Context context) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < ids.length; i++) {
            list.add(String.valueOf(ids[i]));
        }

        StringBuilder csvList = new StringBuilder();
        for (String s : list){
            csvList.append(s);
            csvList.append(",");
        }

        SharedPreferences sharedPref = context.getSharedPreferences("WidgetList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("WidgetList", csvList.toString());
        editor.apply();
    }

    public static int[] getWidgetListFromDatabase(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("WidgetList", Context.MODE_PRIVATE);
        String widgetList = sharedPref.getString("WidgetList", "");
        if (widgetList.equals(""))
            return null;
        String[] items = widgetList.split(",");
        int[] widgetIds = new int[widgetList.length()];
        for (int i = 0; i < items.length; i++) {
            widgetIds[i] = Integer.parseInt(items[i]);
        }
        return widgetIds;
    }

    @Nullable
    public static List<String> getWeatherListFromDatabase(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("WeatherList", Context.MODE_PRIVATE);
        String weatherList = sharedPref.getString("WeatherList", "");
        Log.e("Datas", weatherList);
        if (weatherList.equals(""))
            return null;
        String[] items = weatherList.split(",");
        return Arrays.asList(items);
    }
}
