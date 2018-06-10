package thermo.aziaka.donavan.com.thermo.Main;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import thermo.aziaka.donavan.com.thermo.API.OpenWeatherMapAPI;
import thermo.aziaka.donavan.com.thermo.CallBacks.ListWeatherCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.WeatherCallBack;
import thermo.aziaka.donavan.com.thermo.Geolocal;
import thermo.aziaka.donavan.com.thermo.Models.City;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.Models.WeatherList;
import thermo.aziaka.donavan.com.thermo.RecyclerView.TemperatureAdapter;
import thermo.aziaka.donavan.com.thermo.Utils;

import static thermo.aziaka.donavan.com.thermo.Constant.APP_ID;

public class MainPresenter implements MainContract.Presenter {

    private Geolocal position;
    private List<Weather> list;
    private TemperatureAdapter adapter;
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
        list = new ArrayList<>();
        adapter = new TemperatureAdapter(mView, list);
        mView.setRecyclerView(adapter);
        List <String> newList = getWeatherListFromDatabase();
        position = new Geolocal((Context)mView);
        if (newList != null)
            callWeatherAPI(newList);
    }

    @Override
    public void callWeatherAPI(String city) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeather(city,"metric", APP_ID);
        call.enqueue(new WeatherCallBack(this, mView));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callWeatherAPI(double lat, double lon) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeatherLocation(Double.toString(lat), Double.toString(lon),"metric", APP_ID);
        call.enqueue(new WeatherCallBack(this, mView));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callWeatherAPI(List<String> cities) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<WeatherList> call = api.getWeatherList(Utils.createCityString(cities),"metric", APP_ID);
        call.enqueue(new ListWeatherCallBack(this, mView));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callGeolocalisation() {
        mView.getTemperatureLocalisation(position.getLastBestLocation());
    }

    @Override
    public void getGeolocalisation(Location pos) {
        if (pos != null) {
            callWeatherAPI(pos.getLatitude(), pos.getLongitude());
        } else {
            mView.showMessage("Localisation", "Veuillez activer la géolocalisation pour continuer.");
        }
    }

    @Override
    public void addItemToList(Weather item) {
        list.add(item);
        adapter.updateTemperatureList(list);
        saveWeatherList(list);
    }

    @Override
    public void checkFavori(int position) {
        for (int i = 0; i < list.size(); i++) {
            if (position == i) {
                list.get(i).setFavori(true);
            } else {
                list.get(i).setFavori(false);
            }
        }
    }

    public Weather getWeatherItem(int position) {
        return list.get(position);
    }

    public int getFavoriFromShared() {
        return 0;
    }

    @Override
    public void deleteItemToList(int position) {
        list.remove(position);
        adapter.updateTemperatureList(list);
        saveWeatherList(list);
    }

    @Override
    public void addItemToList(List<Weather> items) {
        list = items;
        adapter.updateTemperatureList(list);
        saveWeatherList(list);
    }

    @Override
    public City getUserCity() {
        return mView.sendCityFromEdit();
    }

    @Override
    public void addTemperature() {
        mView.addTemperatureItem();
    }

    private void saveWeatherList(List<Weather> weatherList) {
        List<String> list = new ArrayList<String>();
        for (Weather w : weatherList) {
            list.add(String.valueOf(w.getId()));
        }

        StringBuilder csvList = new StringBuilder();
        for (String s : list){
            csvList.append(s);
            csvList.append(",");
        }

        SharedPreferences sharedPref = ((Context)mView).getSharedPreferences("WeatherList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("WeatherList", csvList.toString());
        editor.apply();
    }

    @Nullable
    private List<String> getWeatherListFromDatabase() {
        SharedPreferences sharedPref = ((Context)mView).getSharedPreferences("WeatherList", Context.MODE_PRIVATE);
        String weatherList = sharedPref.getString("WeatherList", "");
        Log.e("Datas", weatherList);
        if (weatherList.equals(""))
            return null;
        String[] items = weatherList.split(",");
        return Arrays.asList(items);
    }

}
