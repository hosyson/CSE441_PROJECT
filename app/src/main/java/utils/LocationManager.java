package utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationManager {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;
    private Location currentLocation;

    public LocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    // Kiểm tra xem đã có quyền truy cập vị trí chưa
    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // Cập nhật vị trí hiện tại
    public void updateCurrentLocation(OnSuccessListener<Location> listener) {
        if (!hasLocationPermission()) {
            Log.d("LocationManager", "No location permission");
            return;
        }

        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            Log.d("LocationManager", "Location updated: " + location.getLatitude() + ", " + location.getLongitude());
                            currentLocation = location;
                            if (listener != null) {
                                listener.onSuccess(location);
                            }
                        } else {
                            Log.d("LocationManager", "Location is null");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("LocationManager", "Error getting location", e);
                    });
        } catch (SecurityException e) {
            Log.e("LocationManager", "Security exception when requesting location", e);
        }
    }

    public Double getLatitude() {
        return (currentLocation != null) ? currentLocation.getLatitude() : null;
    }

    public Double getLongitude() {
        return (currentLocation != null) ? currentLocation.getLongitude() : null;
    }
}
