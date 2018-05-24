package thermo.aziaka.donavan.com.thermo.Main;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import thermo.aziaka.donavan.com.thermo.CallBacks.AddTemperatureClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.DismissClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.FabClickEvents;
import thermo.aziaka.donavan.com.thermo.CallBacks.GeoButtonClickEvents;
import thermo.aziaka.donavan.com.thermo.Models.City;
import thermo.aziaka.donavan.com.thermo.R;
import thermo.aziaka.donavan.com.thermo.RecyclerView.TemperatureAdapter;


public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {

    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private Map<Integer, ClickEventsCallBack> clickEvents;
    private EditText country;
    private EditText city;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViews() {
        FloatingActionButton mFab = findViewById(R.id.fab);
        Button mGeoButton = findViewById(R.id.geo_button);
        mRecyclerView = findViewById(R.id.temperature_list);
        progressDialog = new ProgressDialog(this);

        mFab.setOnClickListener(this);
        mGeoButton.setOnClickListener(this);

        clickEvents = new HashMap<>();
        clickEvents.put(R.id.fab, new FabClickEvents());
        clickEvents.put(R.id.geo_button, new GeoButtonClickEvents());
    }

    public void setRecyclerView(TemperatureAdapter adapter) {
        RecyclerView.LayoutManager ll = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(ll);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DismissClickEventsCallBack());
        dialog.show();
    }

    @Override
    public void addTemperatureItem() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.select_city_item, null);
        city = alertLayout.findViewById(R.id.city);
        country = alertLayout.findViewById(R.id.country);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Ajouter une ville")
                .setPositiveButton("Ok", new AddTemperatureClickEventsCallBack(mPresenter))
                .setNegativeButton("Annuler", new DismissClickEventsCallBack())
                .setView(alertLayout);
        dialog.show();
    }

    @Override
    public void getTemperatureLocalisation() {
        // get localisation rights
    }

    @Override
    public void onClick(View v) {
        ClickEventsCallBack clickEvent = clickEvents.get(v.getId());
        clickEvent.handleResponse(mPresenter);
    }

    public City sendCityFromEdit() {
        return new City(city.getText().toString(), country.getText().toString());
    }

    @Override
    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.hide();
        progressDialog.dismiss();
    }

}