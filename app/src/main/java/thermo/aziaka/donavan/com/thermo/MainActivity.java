package thermo.aziaka.donavan.com.thermo;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import retrofit2.Call;
import thermo.aziaka.donavan.com.thermo.CallBacks.WeatherCallBack;
import thermo.aziaka.donavan.com.thermo.POJO.Weather;

import static thermo.aziaka.donavan.com.thermo.Constants.APP_ID;

public class MainActivity extends AppCompatActivity {

    Geolocal position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        position = new Geolocal(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        Location local = position.getLastBestLocation();
    }

    public void manageApiError(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Erreur").setMessage(error)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog.show();
    }

    public void selectCity(View view) {
        final EditText input = new EditText(this);
        input.setInputType(1);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Selectionner votre ville")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!TextUtils.isEmpty(input.getText().toString()))
                            callWeatherAPI(input.getText().toString());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Location local = position.getLastBestLocation();
                        callWeatherAPI(local.getLatitude(), local.getLongitude());
                    }
                });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialog.setView(input);
        dialog.show();
    }

    public void callWeatherAPI(String city) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeather(city,"metric", APP_ID);
        CardView cardview = findViewById(R.id.card);
        LinearLayout layout = (LinearLayout) cardview.getChildAt(0);
        layout.getChildAt(3).setVisibility(View.VISIBLE);
        call.enqueue(new WeatherCallBack(this));
    }

    public void callWeatherAPI(double lat, double lon) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Log.e("Datas", Double.toString(lat) + " : " + Double.toString(lon));
        Call<Weather> call = api.getWeatherLocation(Double.toString(lat), Double.toString(lon),"metric", APP_ID);
        CardView cardview = findViewById(R.id.card);
        LinearLayout layout = (LinearLayout) cardview.getChildAt(0);
        layout.getChildAt(3).setVisibility(View.VISIBLE);
        call.enqueue(new WeatherCallBack(this));
    }

    public void updateCardInfo(Weather obj) {
        Log.e("Success", "it works");
        CardView cardview = findViewById(R.id.card);
        LinearLayout layout = (LinearLayout) cardview.getChildAt(0);
        layout.setBackground(chooseBackgroundColor(obj.getMain().getTemp()));
        ((TextView)layout.getChildAt(0)).setText(obj.getName() + ", " + obj.getSys().getCountry());
        ((TextView)layout.getChildAt(1)).setText(String.valueOf((long)obj.getMain().getTemp()) + "°C");
        ((TextView)layout.getChildAt(2)).setText(String.valueOf((long)obj.getMain().getTemp() * 9/5 + 32) + "°F");
        layout.getChildAt(3).setVisibility(View.INVISIBLE);
    }

    private Drawable chooseBackgroundColor(float temp) {
        if (temp >= 5 && temp < 15)
            return getResources().getDrawable(R.drawable.background_gradient_green);
        else if (temp >= 15 && temp < 25)
            return getResources().getDrawable(R.drawable.background_gradient_yellow);
        else if (temp >= 25)
            return getResources().getDrawable(R.drawable.background_gradient_red);
        else
            return getResources().getDrawable(R.drawable.background_gradient_blue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
