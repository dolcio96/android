package pt.ua.cm.homework2_weather;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import pt.ua.cm.homework2_weather.datamodel.City;
import pt.ua.cm.homework2_weather.datamodel.Weather;
import pt.ua.cm.homework2_weather.network.ForecastForACityResultsObserver;
import pt.ua.cm.homework2_weather.network.IpmaWeatherClient;

public class WeatherForCityActivity extends AppCompatActivity {
    public final static String EXTRA_INTENT= "EXTRA FOR INTENT";
    City city;
    List<Weather> weatherList = null;
    IpmaWeatherClient client = new IpmaWeatherClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("FORECAST");
        Intent intent = getIntent();
        city = (City) intent.getSerializableExtra(EXTRA_INTENT);
        getWheatherForCity();
    }

    private void getWheatherForCity(){

        int localId = city.getGlobalIdLocal();
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {

                weatherList = forecast;
                setContentView(R.layout.activity_weather_for_city);
                displayFragmentWeatherList();
            }
            @Override
            public void onFailure(Throwable cause) {

            }
        });

    }

    public void displayFragmentWeatherList() {
        WeatherFragment waitingFragment = new WeatherFragment(city,weatherList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_weather,waitingFragment);
        fragmentTransaction.commit();
    }
}