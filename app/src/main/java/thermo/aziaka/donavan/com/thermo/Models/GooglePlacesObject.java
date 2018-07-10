package thermo.aziaka.donavan.com.thermo.Models;

import java.util.List;

public class GooglePlacesObject {
    private String status;
    private String next_page_token;
    private List<GooglePlaces> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public List<GooglePlaces> getPlaces() {
        return results;
    }

    public void setPlaces(List<GooglePlaces> places) {
        this.results = places;
    }
}
