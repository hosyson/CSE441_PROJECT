package utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationManager {
    private FusedLocationProviderClient fusedLocationClient;
    private Context context;

    public LocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getCurrentLocation(OnSuccessListener<Location> listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle permission request
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(listener);
    }
}
