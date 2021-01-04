package pt.ua.cm.homework2_weather.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.cm.homework2_weather.datamodel.City;
import pt.ua.cm.homework2_weather.datamodel.CityGroup;
import pt.ua.cm.homework2_weather.datamodel.Weather;
import pt.ua.cm.homework2_weather.datamodel.WeatherGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * issues the requests to the remote IPMA API
 * consummers should provide a lister to get the results
 */

public class IpmaWeatherClient {

    private IpmaApiEndpoints apiService;

    public IpmaWeatherClient() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        this.apiService = retrofitInstance.create(IpmaApiEndpoints.class);
    }

    public void retrieveCitiesList(final CityResultsObserver listener) {
        final HashMap<String, City> cities = new HashMap<>();

        Call<CityGroup> call = apiService.getCityParent();
        call.enqueue(new Callback<CityGroup>() {
            @Override
            public void onResponse(Call<CityGroup> call, Response<CityGroup> response) {
                int statusCode = response.code();
                CityGroup citiesGroup = response.body();
                for (City city : citiesGroup.getCities()) {
                    cities.put(city.getLocal(), city);
                }
                listener.receiveCitiesList( cities);
            }

            @Override
            public void onFailure(Call<CityGroup> call, Throwable t) {
                Log.e("main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }
        });
    }

    public void retrieveForecastForCity(int localId, ForecastForACityResultsObserver listener) {
        List<Weather> forecast = new ArrayList<>();

        Call<WeatherGroup> call = apiService.getWeatherParent(localId);
        call.enqueue(new Callback<WeatherGroup>() {
            @Override
            public void onResponse(Call<WeatherGroup> call, Response<WeatherGroup> response) {
                int statusCode = response.code();
                WeatherGroup weatherTypesGroup = response.body();
                forecast.addAll(weatherTypesGroup.getForecasts());
                listener.receiveForecastList(forecast);
            }

            @Override
            public void onFailure(Call<WeatherGroup> call, Throwable t) {
                Log.e("main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure(t);
            }
        });
    }
}

