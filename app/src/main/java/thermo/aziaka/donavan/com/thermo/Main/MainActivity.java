package thermo.aziaka.donavan.com.thermo.Main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.AddTemperatureClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.ClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.DismissClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.EditTemperatureClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.FabClickEvents;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.GeoButtonClickEvents;
import thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents.RefreshClickEvent;
import thermo.aziaka.donavan.com.thermo.Models.City;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.R;
import thermo.aziaka.donavan.com.thermo.RecyclerView.TemperatureAdapter;


public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {

    private MainContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private Map<Integer, ClickEventsCallBack> clickEvents;
    private EditText country, city;
    private ProgressDialog progressDialog;
    private TextView mainTemp, mainCity, toolBarTitle, mainDescription;
    private AppBarLayout appBarLayout;
    private final static int SCROLL_DOWN = -580;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        mPresenter = new MainPresenter(this);
    }

    private void initViews() {
        FloatingActionButton mFab = findViewById(R.id.fab);
        FloatingActionButton mGeoButton = findViewById(R.id.geo_button);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.temperature_list);

        progressDialog = new ProgressDialog(this);
        mainTemp = findViewById(R.id.main_temp);
        mainCity = findViewById(R.id.main_city);
        toolBarTitle = findViewById(R.id.toolbar_title);
        mainDescription = findViewById(R.id.main_description);
        appBarLayout = findViewById(R.id.appBar);
        toolbar.inflateMenu(R.menu.menu_main);

        mFab.setOnClickListener(this);
        mGeoButton.setOnClickListener(this);
        toolbar.findViewById(R.id.refresh_city).setOnClickListener(this);


        clickEvents = new HashMap<>();
        clickEvents.put(R.id.fab, new FabClickEvents());
        clickEvents.put(R.id.geo_button, new GeoButtonClickEvents());
        clickEvents.put(R.id.refresh_city, new RefreshClickEvent());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= SCROLL_DOWN) {
                    toolBarTitle.setVisibility(View.VISIBLE);
                    mainDescription.setVisibility(View.INVISIBLE);
                    mainCity.setVisibility(View.INVISIBLE);
                } else {
                    toolBarTitle.setVisibility(View.GONE);
                    mainDescription.setVisibility(View.VISIBLE);
                    mainCity.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setRecyclerView(TemperatureAdapter adapter) {
        RecyclerView.LayoutManager ll = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(ll);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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

    public void updateMainTemperature(int position) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(0);

        Weather item = mPresenter.getWeatherItem(position);
        mainTemp.setText(String.format("%s°C", formatter.format(item.getMain().getTemp())));
        mainCity.setText(String.format("%s", String.valueOf(item.getName() + ", " + item.getSys().getCountry())));
        toolBarTitle.setText(String.format("%s", String.valueOf(item.getName() + ", " + item.getSys().getCountry())));
        mainDescription.setText(String.format("%s", item.getWeather().get(item.getWeather().size() - 1).getDescription()));
        appBarLayout.setBackgroundResource(R.color.colorPrimary);
        mPresenter.callPlaceAPI(item.getCoord().getLon(), item.getCoord().getLan());
    }

    @Override
    public void deleteTemperatureItem(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Etes-vous sûr de vouloir supprimer cette ville ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteItemToList(position);
                        if (position == 0) {
                            updateMainTemperature(position);
                        }
                    }
                })
                .setNegativeButton("Non", new DismissClickEventsCallBack());
        dialog.show();
    }

    @Override
    public void refreshTemperature(int position) {
        Weather item = mPresenter.getWeatherItem(position);
        mPresenter.callWeatherAPI(item.getName(), position);
    }

    @Override
    public void setFavoriBackgroundImage(Bitmap URL) {
        appBarLayout.setBackground(new BitmapDrawable(getResources(), URL));
        hideProgressDialog();
    }

    @Override
    public void editTemperatureItem(final int position) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.select_city_item, null);
        city = alertLayout.findViewById(R.id.city);
        country = alertLayout.findViewById(R.id.country);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Changer la ville")
                .setPositiveButton("Ok", new EditTemperatureClickEventsCallBack(mPresenter, position))
                .setNegativeButton("Annuler", new DismissClickEventsCallBack())
                .setView(alertLayout);
        dialog.show();
    }

    @Override
    public void getTemperatureLocalisation(final Location pos) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Ajouter la météo de votre position actuelle ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.getGeolocalisation(pos);
                    }
                })
                .setNegativeButton("Annuler", new DismissClickEventsCallBack());
        dialog.show();
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

    @Override
    public void setFavoriItem(int position) {
        mPresenter.checkFavori(position);
    }


}
