package thermo.aziaka.donavan.com.thermo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.Path;
import retrofit2.http.Query;
import thermo.aziaka.donavan.com.thermo.POJO.Weather;

import static thermo.aziaka.donavan.com.thermo.Constants.API_URL;
import static thermo.aziaka.donavan.com.thermo.Constants.APP_ID;

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

    Call<Weather> getWeatherLocation(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("units") String unit,
            @Query("appid") String id
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
