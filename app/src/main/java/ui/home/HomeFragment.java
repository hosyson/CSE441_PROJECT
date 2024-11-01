package ui.home;

import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.weather.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bumptech.glide.Glide;
import Model.currentWeather.Weather;
import ui.Fivedays.Forecast5DaysFragment;
import ui.history.HistoryFragment;
import utils.LocationManager;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private TextView txtCity, txtCurrentTimeStamp, txtDescription, txtCurrentTemp;
    private ImageView imgCurrentWeatherIcon, imageViewRain, imageViewWind, imageViewHumidity;
    private static final String TAG = "HomeFragment";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private LocationManager locationManager;
    private Button btnAround5Days, btnViewHistory;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize LocationManager
        locationManager = new LocationManager(requireContext());
        checkLocationPermissionAndInitialize();

        // Initialize UI elements
        FloatingActionButton fab = view.findViewById(R.id.fab);
        txtCity = view.findViewById(R.id.txtCity);
        imgCurrentWeatherIcon = view.findViewById(R.id.imgCurrentWeatherIcon);
        txtCurrentTimeStamp = view.findViewById(R.id.txtCurrentTimeStamp);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtCurrentTemp = view.findViewById(R.id.txtCurrentTemp);
        imageViewRain = view.findViewById(R.id.imageView2);
        imageViewWind = view.findViewById(R.id.imageView3);
        imageViewHumidity = view.findViewById(R.id.imageView4);
        btnAround5Days = view.findViewById(R.id.btnAround5Days);
        btnViewHistory = view.findViewById(R.id.btnViewHistory);

        // FloatingActionButton to navigate to City List
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_homeFragment_to_cityListFragment);
        });

        // Button to navigate to Around5DaysFragment
        btnAround5Days.setOnClickListener(v -> openAround5Days());
        // Button to navigate to HistoryFragment
        btnViewHistory.setOnClickListener(v -> openHistory());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            HomeViewModelFactory factory = new HomeViewModelFactory(
                    requireActivity().getApplication(),
                    locationManager
            );

            mViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
            Log.d(TAG, "ViewModel created successfully");

            mViewModel.getCurrentWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
                if (weatherData != null) {
                    updateUI(weatherData);
                } else {
                    Log.w(TAG, "Received null weather data");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error creating ViewModel", e);
        }
    }

    private void openAround5Days() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, new Forecast5DaysFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openHistory() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, new HistoryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void checkLocationPermissionAndInitialize() {
        if (locationManager.hasLocationPermission()) {
            initializeViewModel();
        } else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Yêu cầu quyền truy cập vị trí")
                    .setMessage("Ứng dụng cần quyền truy cập vị trí để hiển thị thời tiết tại vị trí của bạn.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        requestPermissions(
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE
                        );
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeViewModel();
            } else {
                Toast.makeText(requireContext(), "Ứng dụng cần quyền truy cập vị trí để hoạt động chính xác", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initializeViewModel() {
        locationManager.updateCurrentLocation(location -> {
            HomeViewModelFactory factory = new HomeViewModelFactory(requireActivity().getApplication(), locationManager);
            mViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        });
    }

    private void updateUI(Weather weatherData) {
        try {
            txtCity.setText(weatherData.getCityName());
            txtCurrentTimeStamp.setText(weatherData.getDatetime());
            txtDescription.setText(weatherData.getDescription());
            txtCurrentTemp.setText(String.valueOf(weatherData.getTemperature()));
            Glide.with(this).load(weatherData.getIcon()).into(imgCurrentWeatherIcon);
        } catch (Exception e) {
            Log.e(TAG, "Error updating UI", e);
        }
    }
}
