package thermo.aziaka.donavan.com.thermo.Models;

public class GeometryPlace {
    private Coord location;
    private GoogleViewPort viewport;

    public Coord getLocation() {
        return location;
    }

    public void setLocation(Coord location) {
        this.location = location;
    }

    public GoogleViewPort getViewport() {
        return viewport;
    }

    public void setViewport(GoogleViewPort viewport) {
        this.viewport = viewport;
    }
}
