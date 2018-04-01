package thermo.aziaka.donavan.com.thermo.CallBacks;

import android.telecom.Call;
import android.util.Log;

import java.util.Arrays;

import retrofit2.Callback;
import retrofit2.Response;
import thermo.aziaka.donavan.com.thermo.MainActivity;
import thermo.aziaka.donavan.com.thermo.POJO.Weather;

/**
 * Created by donavan on 3/20/18.
 */

public class WeatherCallBack implements Callback<Weather> {

    private MainActivity context;

    public WeatherCallBack(MainActivity _context) {
        context = _context;
    }

    @Override
    public void onResponse(retrofit2.Call<Weather> call, Response<Weather> response) {
        Log.e("header", response.headers().toString());
        if (response.isSuccessful()) {
            Log.e("Datas", response.message());
            context.updateCardInfo(response.body());
        } else {
            Log.e("ResponseValid", response.message());
            context.manageApiError(response.message());
        }
    }

    @Override
    public void onFailure(retrofit2.Call<Weather> call, Throwable t) {
        Log.e("ResponseFail", t.getMessage());
        Log.e("URL", Arrays.toString(t.getStackTrace()));
        context.manageApiError(t.getMessage());
    }
}
