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
        if (response.isSuccessful()) {
            context.editItemToList(response.body(), position);
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