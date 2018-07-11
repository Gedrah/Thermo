package thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack;

import android.util.Log;

import java.util.Arrays;

import retrofit2.Callback;
import retrofit2.Response;
import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.WeatherList;

public class ListWeatherCallBack implements Callback<WeatherList> {

    private MainContract.Presenter context;
    private MainContract.View mView;

    public ListWeatherCallBack(MainContract.Presenter _context, MainContract.View view) {
        context = _context;
        mView = view;
    }

    @Override
    public void onResponse(retrofit2.Call<WeatherList> call, Response<WeatherList> response) {
        if (response.isSuccessful()) {
            context.addItemToList(response.body().getList());
        } else {
            mView.showMessage("Erreur", "La liste des villes n'a pas pu être chargés.");
        }
        mView.hideProgressDialog();
    }

    @Override
    public void onFailure(retrofit2.Call<WeatherList> call, Throwable t) {
        mView.showMessage("Erreur", "Un problème de connexion est survenu. Veuillez vérifier votre connexion internet et réessayer.");
        mView.hideProgressDialog();
    }
}
