package repository;

import Model.history.HistoryResponse;
import api.WeatherApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherHistoryRepository {
    private WeatherApiService api;

    public WeatherHistoryRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WeatherApiService.class);
    }

    public void getHistoricalWeather(String apiKey, String location, String date, retrofit2.Callback<HistoryResponse> callback) {
        Call<HistoryResponse> call = api.getHistoricalWeather(apiKey, location, date);
        call.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful()) {
                    // Gọi callback với đối tượng response body
                    callback.onResponse(call, response); // Truyền vào response chứ không phải response.body()
                } else {
                    // Nếu phản hồi không thành công, gọi callback với lỗi
                    callback.onFailure(call, new Throwable("Response failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                // Gọi callback với lỗi
                callback.onFailure(call, t);
            }
        });
    }
}

