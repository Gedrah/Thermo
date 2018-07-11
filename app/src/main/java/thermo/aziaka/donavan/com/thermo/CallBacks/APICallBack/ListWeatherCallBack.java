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
        Log.e("header", response.headers().toString());
        if (response.isSuccessful()) {
            Log.e("Datas", response.body().toString());
            Log.e("Datas Place", String.valueOf(response.raw()));
            context.addItemToList(response.body().getList());
        } else {
            Log.e("ResponseValid", response.message());
            mView.showMessage("Erreur", "Votre ville n'existe pas.");
        }
        mView.hideProgressDialog();
    }

    @Override
    public void onFailure(retrofit2.Call<WeatherList> call, Throwable t) {
        Log.e("ResponseFail", t.getMessage());
        Log.e("URL", Arrays.toString(t.getStackTrace()));
        mView.showMessage("Erreur", "Un problème de connexion est survenu");
        mView.hideProgressDialog();
    }
}
