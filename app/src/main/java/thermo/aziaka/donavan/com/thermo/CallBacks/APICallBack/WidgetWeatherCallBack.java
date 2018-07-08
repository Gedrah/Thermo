package thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;

import retrofit2.Callback;
import retrofit2.Response;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.Widget.TempWidgetProvider;

public class WidgetWeatherCallBack implements Callback<Weather> {

    private Context context;
    private TempWidgetProvider view;

    public WidgetWeatherCallBack(Context _context, TempWidgetProvider provider) {
        context = _context;
        view = provider;
    }

    @Override
    public void onResponse(retrofit2.Call<Weather> call, Response<Weather> response) {
        Log.e("header", response.headers().toString());
        if (response.isSuccessful()) {
            Log.e("Datas", response.body().toString());
            view.updateWidget(response.body());
        } else {
            Log.e("ResponseValid", response.message());
        }
    }

    @Override
    public void onFailure(retrofit2.Call<Weather> call, Throwable t) {
        Log.e("ResponseFail", t.getMessage());
        Log.e("URL", Arrays.toString(t.getStackTrace()));
    }
}
