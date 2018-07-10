package thermo.aziaka.donavan.com.thermo.Models;

import java.util.List;

public class GooglePlaces {
    private GeometryPlace geometry;
    private String icon;
    private String id;
    private String name;
    private List<GooglePhotos> photos;
    private String place_id;
    private String reference;
    private String scope;
    private List<String> types;
    private String vicinity;
    private float rating;

    public GeometryPlace getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPlace geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GooglePhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<GooglePhotos> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

