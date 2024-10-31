package ui.today;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.WeatherData24h;
import adapter.CustomAdapter24h;

public class Weather24hFragment extends Fragment {

    private static final int LOCATION_REQUEST_CODE = 1000;

    ImageView imgback;
    TextView txtname;
    ListView lv;
    CustomAdapter24h customAdapter;
    ArrayList<WeatherData24h> mangthoitiet;
    FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather24h, container, false);
        imgback = view.findViewById(R.id.imageviewBack);
        txtname = view.findViewById(R.id.textviewTenthanhpho);
        lv = view.findViewById(R.id.listviewWeather24h);

        Anhxa();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });

        // Kiểm tra quyền truy cập vị trí
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

        return view;
    }

    private void Anhxa() {
        mangthoitiet = new ArrayList<>();
        customAdapter = new CustomAdapter24h(getContext(), mangthoitiet);
        lv.setAdapter(customAdapter);
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);  // Chỉ cập nhật một lần

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    getCityName(location.getLatitude(), location.getLongitude());
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }




    private void getCityName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            // Lấy danh sách địa điểm từ toạ độ
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                String tenthanhpho = addresses.get(0).getLocality(); // Lấy tên thành phố từ địa chỉ
                if (tenthanhpho == null) {
                    tenthanhpho = addresses.get(0).getAdminArea(); // Lấy tên bang/quận nếu không tìm thấy thành phố
                }
                if (tenthanhpho != null) {
                    txtname.setText(tenthanhpho); // Cập nhật tên thành phố trong giao diện
                    Get24HoursData(tenthanhpho);  // Gọi hàm lấy dữ liệu thời tiết với tên thành phố hiện tại
                    Log.e("Ten thanh pho","ten :"+tenthanhpho);
                } else {
                    Log.e("Geocoder Error", "City name not found");
                }
            } else {
                Log.e("Geocoder Error", "No address found for the location");
            }
        } catch (Exception e) {
            Log.e("Geocoder Error", "Unable to get city name: " + e.getMessage());
        }
    }


    private void Get24HoursData(String tenthanhpho) {
        // Yêu cầu để lấy key của thành phố
        String urlcitykey = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=QbGB0fmYi9oCmAPLZSzOb1gwJIBU3CDQ&q=" + tenthanhpho;
        RequestQueue requestQueueCityKey = Volley.newRequestQueue(getContext());
        StringRequest stringRequestCityKey = new StringRequest(Request.Method.GET, urlcitykey,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Lấy key thành phố từ phản hồi JSON
                            JSONArray jsonArrayList = new JSONArray(response);
                            JSONObject jsonObjectList = jsonArrayList.getJSONObject(0);
                            String keyCity = jsonObjectList.getString("Key");
                            Log.d("JSON Key city", "Json: " + response);

                            // Sau khi nhận được keyCity, thực hiện yêu cầu thứ hai để lấy dữ liệu thời tiết
                            String url = "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + keyCity + "?apikey=QbGB0fmYi9oCmAPLZSzOb1gwJIBU3CDQ";
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                // Xử lý phản hồi JSON và thêm dữ liệu vào danh sách
                                                JSONArray jsonArrayList = new JSONArray(response);
                                                for (int i = 0; i < jsonArrayList.length(); i++) {
                                                    JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                                    String ngay = jsonObjectList.getString("EpochDateTime");

                                                    long l = Long.valueOf(ngay);
                                                    Date date = new Date(l * 1000L);
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                                                    String Day = simpleDateFormat.format(date);

                                                    SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm a");
                                                    String hour = hourFormat.format(date);

                                                    JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("Temperature");
                                                    String temp = jsonObjectTemp.getString("Value");
                                                    int intdoC = (int) ((Double.parseDouble(temp) - 32) / 1.8);
                                                    String stringdoC = String.valueOf(intdoC);

                                                    String status = jsonObjectList.getString("IconPhrase");
                                                    String icon = jsonObjectList.getString("WeatherIcon");

                                                    // Thêm dữ liệu thời tiết vào danh sách
                                                    mangthoitiet.add(new WeatherData24h(Day, status, icon, hour, stringdoC));
                                                }

                                                // Cập nhật giao diện
                                                customAdapter.notifyDataSetChanged();
                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Loi tra ve json 1", "Error occurred: " + error.getMessage());
                                }
                            });
                            // Thực hiện yêu cầu thứ hai để lấy dữ liệu thời tiết
                            requestQueue.add(stringRequest);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Loi tra ve json 2", "Error occurred: " + error.getMessage());
            }
        });
        // Thực hiện yêu cầu đầu tiên để lấy key của thành phố
        requestQueueCityKey.add(stringRequestCityKey);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, lấy vị trí
                getCurrentLocation();
            } else {
                // Quyền bị từ chối
                Log.e("Permission Denied", "Location permission was denied.");
                // Bạn có thể thông báo cho người dùng hoặc xử lý theo cách khác
            }
        }
    }
}
