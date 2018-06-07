package thermo.aziaka.donavan.com.thermo.Main;

import android.location.Location;

import java.util.List;

import thermo.aziaka.donavan.com.thermo.Models.City;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.RecyclerView.TemperatureAdapter;

public interface MainContract {

    /**
    * To have access to Android UI (buttons, etc)
    */
    interface View {
        void showMessage(String title, String message);
        void addTemperatureItem();
        void getTemperatureLocalisation(Location pos);
        void setRecyclerView(TemperatureAdapter adapter);
        City sendCityFromEdit();
        void showProgressDialog(String message);
        void hideProgressDialog();
        void editTemperatureItem(final int position);
        void setFavoriItem(int position);
        void deleteTemperatureItem(final int position);
    }

    /**
     * To access model and data management
     */
    interface Presenter {
        void callWeatherAPI(String city);
        void callWeatherAPI(double lat, double lon);
        void callWeatherAPI(List<String> cities);
        void callGeolocalisation();
        void addItemToList(Weather item);
        void addItemToList(List<Weather> items);
        void deleteItemToList(int position);
        void getGeolocalisation(Location pos);
        City getUserCity();
        void checkFavori(int position);
        void addTemperature();
    }
}
