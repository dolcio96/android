package pt.ua.cm.homework2_weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import pt.ua.cm.homework2_weather.datamodel.City;
import pt.ua.cm.homework2_weather.network.CityResultsObserver;
import pt.ua.cm.homework2_weather.network.IpmaWeatherClient;

public class MainActivity extends AppCompatActivity {
    IpmaWeatherClient client = new IpmaWeatherClient();
    private Map<String, City> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("FORECAST");
        getCities();
    }
    private void getCities(){
        client.retrieveCitiesList(new CityResultsObserver() {
            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                cities=citiesCollection;
                setContentView(R.layout.activity_main);
                displayFragmentCitiesList();
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.d("FAIL CITY LIST","Failed to get weather conditions!");
            }
        });
    }

    public void displayFragmentCitiesList() {
        CityFragment cityFragment = new CityFragment(cities);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_cities,cityFragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //getSupportActionBar().setTitle(TITLE);
                //setContentView(R.layout.progress_bar);
                //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                //getCities();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}