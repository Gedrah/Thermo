package thermo.aziaka.donavan.com.thermo.CallBacks;

import android.util.Log;

import java.util.Arrays;

import retrofit2.Callback;
import retrofit2.Response;
import thermo.aziaka.donavan.com.thermo.Main.MainActivity;
import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.Weather;

/**
 * Created by donavan on 3/20/18.
 */

public class WeatherCallBack implements Callback<Weather> {

    private MainContract.Presenter context;

    public WeatherCallBack(MainContract.Presenter _context) {
        context = _context;
    }

    @Override
    public void onResponse(retrofit2.Call<Weather> call, Response<Weather> response) {
        Log.e("header", response.headers().toString());
        if (response.isSuccessful()) {
            Log.e("Datas", response.body().toString());
            context.addItemToList(response.body());
        } else {
            Log.e("ResponseValid", response.message());
            context.getCurrentView().showMessage("Erreur", "Votre ville n'existe pas.");
        }
        context.getCurrentView().hideProgressDialog();
    }

    @Override
    public void onFailure(retrofit2.Call<Weather> call, Throwable t) {
        Log.e("ResponseFail", t.getMessage());
        Log.e("URL", Arrays.toString(t.getStackTrace()));
        context.getCurrentView().showMessage("Erreur", "Un problème de connexion est survenu");
        context.getCurrentView().hideProgressDialog();
    }
}
