package pt.ua.cm.homework2_weather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pt.ua.cm.homework2_weather.datamodel.City;
import pt.ua.cm.homework2_weather.datamodel.Weather;
import pt.ua.cm.homework2_weather.network.ForecastForACityResultsObserver;
import pt.ua.cm.homework2_weather.network.IpmaWeatherClient;

public class WaitingFragment extends Fragment {
    RecyclerView weatherRecyclerView;
    List<Weather> weatherList;
    City city;
    IpmaWeatherClient client = new IpmaWeatherClient();
    public WaitingFragment(City city){
        this.city=city;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWheatherForCity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waiting, container, false);
    }

    private void getWheatherForCity(){

        int localId = city.getGlobalIdLocal();
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                weatherList = forecast;

                if (weatherList!=null){
                    WeatherFragment waitingFragment = new WeatherFragment(city,weatherList);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_weather_prov,waitingFragment);
                    fragmentTransaction.commit();
                }
                else{
                    Log.d("Non aggiunto","Non aggiunto");
                }
            }
            @Override
            public void onFailure(Throwable cause) {

            }
        });

    }

}