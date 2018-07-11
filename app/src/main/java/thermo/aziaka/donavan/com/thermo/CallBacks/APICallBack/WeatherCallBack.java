package thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack;

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
    private MainContract.View mView;

    public WeatherCallBack(MainContract.Presenter _context, MainContract.View view) {
        context = _context;
        mView = view;
    }

    @Override
    public void onResponse(retrofit2.Call<Weather> call, Response<Weather> response) {
        if (response.isSuccessful()) {
            context.addItemToList(response.body());
        } else {
            mView.showMessage("Erreur", "Votre ville n'existe pas. Veuillez écrire le nom de votre ville en anglais.");
        }
        mView.hideProgressDialog();
    }

    @Override
    public void onFailure(retrofit2.Call<Weather> call, Throwable t) {
        mView.showMessage("Erreur", "Un problème de connexion est survenu. Veuillez vérifier votre connexion internet et réessayer.");
        mView.hideProgressDialog();
    }
}
