package thermo.aziaka.donavan.com.thermo.Models;

public class City {
    private String city;
    private String country;

    public City (String _city, String _country) {
        city = _city;
        country = _country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
