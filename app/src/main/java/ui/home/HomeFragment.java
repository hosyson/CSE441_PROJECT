package ui.home;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weather.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bumptech.glide.Glide;

import ui.home.HomeViewModel;  // Đảm bảo đã import đúng

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private TextView txtCity, txtCurrentTimeStamp, txtDescription, txtCurrentTemp,txtTodayLabel;
    private ImageView imgCurrentWeatherIcon, imageViewRain, imageViewWind, imageViewHumidity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Khởi tạo các view từ layout
        FloatingActionButton fab = view.findViewById(R.id.fab);
        txtCity = view.findViewById(R.id.txtCity);
        imgCurrentWeatherIcon = view.findViewById(R.id.imgCurrentWeatherIcon);
        txtCurrentTimeStamp = view.findViewById(R.id.txtCurrentTimeStamp);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtCurrentTemp = view.findViewById(R.id.txtCurrentTemp);
        imageViewRain = view.findViewById(R.id.imageView2);
        imageViewWind = view.findViewById(R.id.imageView3);
        imageViewHumidity = view.findViewById(R.id.imageView4);
        txtTodayLabel = view.findViewById(R.id.txtTodayLabel);
        // Cài đặt sự kiện cho FloatingActionButton
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_homeFragment_to_cityListFragment);
        });

        txtTodayLabel.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_homeFragment_to_Weather24hFragment);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Khởi tạo ViewModel mà không cần ViewModelFactory
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Quan sát dữ liệu từ ViewModel và cập nhật UI
        mViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            if (weatherData != null) {
                // Cập nhật giao diện với dữ liệu thời tiết
                txtCity.setText(weatherData.getCityName());  // Kiểm tra phương thức getCityName()
                txtCurrentTimeStamp.setText(weatherData.getTimezone());  // Kiểm tra phương thức getTimezone()
                txtDescription.setText(weatherData.getCurrent().getWeather_description());  // Kiểm tra phương thức getWeather_description()
                txtCurrentTemp.setText(String.format("%s°C", weatherData.getCurrent().getTemp()));  // Kiểm tra phương thức getTemp()

                // Tải các biểu tượng cho Rain, Wind, Humidity
                Glide.with(this)
                        .load(R.drawable.rain)
                        .into(imageViewRain);
                Glide.with(this)
                        .load(R.drawable.wind)
                        .into(imageViewWind);
                Glide.with(this)
                        .load(R.drawable.humidity)
                        .into(imageViewHumidity);
            }
        });
    }
}
