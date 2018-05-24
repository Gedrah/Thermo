package thermo.aziaka.donavan.com.thermo;

import java.util.List;

public class Utils {
    public static String createCityString(List<String> cities) {
        StringBuilder allCities = new StringBuilder();
        for (int i = 0; i < cities.size(); i++) {
            allCities.append(cities.get(i));
            if (i < cities.size() - 1) {
                allCities.append(",");
            }
        }
        return allCities.toString();
    }
}
