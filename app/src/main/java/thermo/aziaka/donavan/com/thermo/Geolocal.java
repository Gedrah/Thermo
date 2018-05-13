package thermo.aziaka.donavan.com.thermo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import thermo.aziaka.donavan.com.thermo.Main.MainActivity;

/**
 * Created by donavan on 4/2/18.
 */

public class Geolocal {

    private LocationManager mLocationManager;
    private MainActivity currentContext;

    public Geolocal(MainActivity context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        currentContext = context;
    }

    public Location getLastBestLocation() {
        if (ActivityCompat.checkSelfPermission(currentContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(currentContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(currentContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return null;
            } else {
            Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

            long NetLocationTime = 0;

            if (null != locationNet) {
                NetLocationTime = locationNet.getTime();
            }

            if ( 0 < GPSLocationTime - NetLocationTime ) {
                return locationGPS;
            }
            else {
                return locationNet;
            }
        }

    }
}
