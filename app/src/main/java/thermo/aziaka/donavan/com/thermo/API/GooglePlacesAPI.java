package thermo.aziaka.donavan.com.thermo.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import thermo.aziaka.donavan.com.thermo.Models.GooglePlacesObject;

import static thermo.aziaka.donavan.com.thermo.Constant.GOOGLE_PLACES_URL;

public interface GooglePlacesAPI {

    @GET("photo")
    Call<ResponseBody> getGooglePlacePhoto(
            @Query("maxwidth") String width,
            @Query("photoreference") String photo,
            @Query("key") String apiKey
    );

    @GET("nearbysearch/json")
    Call<GooglePlacesObject> getGooglePlace(
            @Query("location") String localisation,
            @Query("radius") String radius,
            @Query("key") String apiKey
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GOOGLE_PLACES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
