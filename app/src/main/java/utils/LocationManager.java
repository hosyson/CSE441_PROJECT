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
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;
    private Location currentLocation;

    public LocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        updateCurrentLocation(); // Cập nhật vị trí ngay khi khởi tạo
    }

    // Cập nhật vị trí hiện tại
    private void updateCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa có quyền, dừng lại
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
            }
        });
    }

    // Trả về vĩ độ (latitude)
    public Double getLatitude() {
        return (currentLocation != null) ? currentLocation.getLatitude() : null;
    }

    // Trả về kinh độ (longitude)
    public Double getLongitude() {
        return (currentLocation != null) ? currentLocation.getLongitude() : null;
    }

    // Cung cấp vị trí hiện tại qua listener (nếu cần dùng bất đồng bộ)
    public void getCurrentLocation(OnSuccessListener<Location> listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Xử lý yêu cầu quyền ở đây nếu cần
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(listener);
    }
}
