package thermo.aziaka.donavan.com.thermo.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.Models.WeatherList;

import static thermo.aziaka.donavan.com.thermo.Constant.API_URL;

/**
 * Created by donavan on 3/19/18.
 */

public interface OpenWeatherMapAPI {

    @GET("weather")
    Call<Weather> getWeather(
            @Query("q") String name,
            @Query("units") String unit,
            @Query("appid") String id
    );

    @GET("weather")
    Call<Weather> getWeatherLocation(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("units") String unit,
            @Query("appid") String id
    );

    @GET("group")
    Call<WeatherList> getWeatherList(
            @Query("id") String list,
            @Query("units") String unit,
            @Query("appid") String id
    );

    @GET("weather")
    Call<Weather> getWeatherWidget(
            @Query("id") String name,
            @Query("units") String unit,
            @Query("appid") String id
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
