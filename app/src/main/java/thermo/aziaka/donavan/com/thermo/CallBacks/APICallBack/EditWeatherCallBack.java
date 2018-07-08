package thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack;

import android.util.Log;

import java.util.Arrays;

import retrofit2.Callback;
import retrofit2.Response;
import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.Weather;

public class EditWeatherCallBack implements Callback<Weather> {

    private MainContract.Presenter context;
    private MainContract.View mView;
    private int position;

    public EditWeatherCallBack(MainContract.Presenter _context, MainContract.View view, int pos) {
        context = _context;
        mView = view;
        position = pos;
    }

    @Override
    public void onResponse(retrofit2.Call<Weather> call, Response<Weather> response) {
        Log.e("header", response.headers().toString());
        if (response.isSuccessful()) {
            Log.e("Datas", response.body().toString());
            context.editItemToList(response.body(), position);
        } else {
            Log.e("ResponseValid", response.message());
            mView.showMessage("Erreur", "Votre ville n'existe pas. Veuillez écrire le nom de votre ville en anglais.");
        }
        mView.hideProgressDialog();
    }

    @Override
    public void onFailure(retrofit2.Call<Weather> call, Throwable t) {
        Log.e("ResponseFail", t.getMessage());
        Log.e("URL", Arrays.toString(t.getStackTrace()));
        mView.showMessage("Erreur", "Un problème de connexion est survenu");
        mView.hideProgressDialog();
    }
}