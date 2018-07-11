package thermo.aziaka.donavan.com.thermo.Main;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thermo.aziaka.donavan.com.thermo.API.GooglePlacesAPI;
import thermo.aziaka.donavan.com.thermo.API.OpenWeatherMapAPI;
import thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack.EditWeatherCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack.ListWeatherCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack.WeatherCallBack;
import thermo.aziaka.donavan.com.thermo.Geolocal;
import thermo.aziaka.donavan.com.thermo.Models.City;
import thermo.aziaka.donavan.com.thermo.Models.GooglePlaces;
import thermo.aziaka.donavan.com.thermo.Models.GooglePlacesObject;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.Models.WeatherList;
import thermo.aziaka.donavan.com.thermo.RecyclerView.TemperatureAdapter;
import thermo.aziaka.donavan.com.thermo.Utils;

import static thermo.aziaka.donavan.com.thermo.Constant.APP_ID;
import static thermo.aziaka.donavan.com.thermo.Constant.GOOGLE_PLACES_API_KEY;

public class MainPresenter implements MainContract.Presenter {

    private Geolocal position;
    private List<Weather> list;
    private TemperatureAdapter adapter;
    private MainContract.View mView;

    // actualiser, placer en haut et editer la ville
    // edit API call

    public MainPresenter(MainContract.View view) {
        mView = view;
        list = new ArrayList<>();
        adapter = new TemperatureAdapter(mView, list);
        mView.setRecyclerView(adapter);
        List <String> newList = Utils.getWeatherListFromDatabase((Context)mView);
        position = new Geolocal((Context)mView);
        if (newList != null)
            callWeatherAPI(newList);
    }

    @Override
    public void callWeatherAPI(String city, int position) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeather(city,"metric", "fr",  APP_ID);
        call.enqueue(new EditWeatherCallBack(this, mView, position));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callPlaceAPI(float lon, float lat) {
        Log.e("Datas Place", "it passes here atleast" + lon + " : " + lat);
        GooglePlacesAPI api = GooglePlacesAPI.retrofit.create(GooglePlacesAPI.class);
        Call<GooglePlacesObject> call = api.getGooglePlace(Float.toString(lat) + "," + Float.toString(lon), "5000", GOOGLE_PLACES_API_KEY);
        call.enqueue(new Callback<GooglePlacesObject>() {
            @Override
            public void onResponse(Call<GooglePlacesObject> call, Response<GooglePlacesObject> response) {
                Log.e("Datas Place", "it works ?");
                Log.e("Datas Place", String.valueOf(response.raw()));
                Log.e("Datas Place", String.valueOf(response.body().getPlaces().get(0).getPhotos().get(0).getPhoto_reference()));
                callPhotoAPI(response.body().getPlaces().get(0).getPhotos().get(0).getPhoto_reference());
            }

            @Override
            public void onFailure(Call<GooglePlacesObject> call, Throwable t) {
                Log.e("Datas Place", "it doesn't works ?");
                Log.e("Datas Place", t.getMessage());
                mView.hideProgressDialog();
            }
        });
        mView.showProgressDialog("Chargement de l'image...");
    }

    @Override
    public void callPhotoAPI(String PhotoReference) {
        mView.showProgressDialog("Chargement de l'image...");
        Log.e("Datas Place", "it passes here atleast photo");
        WindowManager wm = (WindowManager) ((Context)mView).getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        GooglePlacesAPI api = GooglePlacesAPI.retrofit.create(GooglePlacesAPI.class);
        Call<ResponseBody> call = api.getGooglePlacePhoto(String.valueOf(width), PhotoReference, GOOGLE_PLACES_API_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("Datas Place", "it works photo?");
                Log.e("Datas Place", response.message());
                Log.e("Datas Place", String.valueOf(response.body()));
                mView.setFavoriBackgroundImage(BitmapFactory.decodeStream(response.body().byteStream()));
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Datas Place", "it doesn't works photo ?");
                Log.e("Datas Place", t.getMessage());
                mView.hideProgressDialog();
            }
        });
    }

    @Override
    public void callWeatherAPI(String city) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeather(city,"metric", "fr",  APP_ID);
        call.enqueue(new WeatherCallBack(this, mView));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callWeatherAPI(double lat, double lon) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeatherLocation(Double.toString(lat), Double.toString(lon),"metric", "fr", APP_ID);
        call.enqueue(new WeatherCallBack(this, mView));
        mView.showProgressDialog("Chargement de la ville...");
    }

    @Override
    public void callWeatherAPI(List<String> cities) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<WeatherList> call = api.getWeatherList(Utils.createCityString(cities),"metric", "fr", APP_ID);
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

    public void editItemToList(Weather item, int position) {
        list.set(position, item);
        adapter.updateTemperatureList(list);
        Utils.saveWeatherList(list, (Context)mView);
    }

    @Override
    public void addItemToList(Weather item) {
        list.set(0, item);
        adapter.updateTemperatureList(list);
        Utils.saveWeatherList(list, (Context)mView);
        mView.updateMainTemperature(0);
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
        Weather item = list.get(position);
        list.remove(position);
        list.add(0, item);
        adapter.updateTemperatureList(list);
        Utils.saveWeatherList(list, (Context)mView);
    }

    public Weather getWeatherItem(int position) {
        return list.get(position);
    }

    @Override
    public void deleteItemToList(int position) {
        list.remove(position);
        adapter.updateTemperatureList(list);
        Utils.saveWeatherList(list, (Context)mView);
    }

    @Override
    public void addItemToList(List<Weather> items) {
        list = items;
        adapter.updateTemperatureList(list);
        Utils.saveWeatherList(list, (Context)mView);
        if (!list.isEmpty())
            mView.updateMainTemperature(0);
    }

    @Override
    public City getUserCity() {
        return mView.sendCityFromEdit();
    }

    @Override
    public void addTemperature() {
        mView.addTemperatureItem();
    }

}
