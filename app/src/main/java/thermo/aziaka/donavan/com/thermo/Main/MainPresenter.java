package thermo.aziaka.donavan.com.thermo.Main;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import thermo.aziaka.donavan.com.thermo.API.OpenWeatherMapAPI;
import thermo.aziaka.donavan.com.thermo.CallBacks.WeatherCallBack;
import thermo.aziaka.donavan.com.thermo.Geolocal;
import thermo.aziaka.donavan.com.thermo.Models.City;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.RecyclerView.TemperatureAdapter;

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
    }

    @Override
    public void callWeatherAPI(String city) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeather(city,"metric", APP_ID);
        call.enqueue(new WeatherCallBack(this));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callWeatherAPI(double lat, double lon) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeatherLocation(Double.toString(lat), Double.toString(lon),"metric", APP_ID);
        call.enqueue(new WeatherCallBack(this));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callGeolocalisation() {

    }

    public void initAdapter(List<Weather> list) {
    }

    @Override
    public void addItemToList(Weather item) {
        list.add(item);
        adapter.updateTemperatureList(list);
    }

    @Override
    public City getUserCity() {
        return mView.sendCityFromEdit();
    }

    public MainContract.View getCurrentView() {
        return mView;
    }
}
