package thermo.aziaka.donavan.com.thermo.Main;

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
        void getTemperatureLocalisation();
        void setRecyclerView(TemperatureAdapter adapter);
        City sendCityFromEdit();
        void showProgressDialog(String message);
        void hideProgressDialog();
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
        City getUserCity();
        void addTemperature();
    }
}
