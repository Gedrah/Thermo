package thermo.aziaka.donavan.com.thermo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import thermo.aziaka.donavan.com.thermo.CallBacks.WeatherCallBack;
import thermo.aziaka.donavan.com.thermo.POJO.Weather;

import static thermo.aziaka.donavan.com.thermo.Constants.APP_ID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callWeatherAPI("Strasbourg");
        manageEvents();
    }

    private void manageEvents() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void callWeatherAPI(String city) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Call<Weather> call = api.getWeather(city, APP_ID);
        call.enqueue(new WeatherCallBack(this));
    }

    public void updateCardInfo(Weather obj) {
        Log.e("Success", "it works");
        CardView cardview = findViewById(R.id.card);
        ((TextView)cardview.getChildAt(0)).setText(obj.getName());
        ((TextView)cardview.getChildAt(1)).setText(String.valueOf(obj.getMain().getTemp()));
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
