package api;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherFetcher {
    private static final String API_KEY = "04b3351e3f114bd7abd233330243110"; // Thay thế bằng API key của bạn
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    private final OkHttpClient client;

    public WeatherFetcher() {
        client = new OkHttpClient();
    }

    public void fetchCurrentWeather(String city, final WeatherCallback callback) {
        String url = BASE_URL + "?key=" + API_KEY + "&q=" + city;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    // Gọi phương thức xử lý dữ liệu trong callback
                    callback.onSuccess(responseData);
                } else {
                    callback.onError("Response was not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Network call failed: " + e.getMessage());
            }
        });
    }

    // Giao diện callback để xử lý dữ liệu trả về
    public interface WeatherCallback {
        void onSuccess(String data);
        void onError(String errorMessage);
    }
}
