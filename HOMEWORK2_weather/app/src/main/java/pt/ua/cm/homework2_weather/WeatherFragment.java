package pt.ua.cm.homework2_weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pt.ua.cm.homework2_weather.datamodel.City;
import pt.ua.cm.homework2_weather.datamodel.Weather;
import pt.ua.cm.homework2_weather.network.ForecastForACityResultsObserver;
import pt.ua.cm.homework2_weather.network.IpmaWeatherClient;


public class WeatherFragment extends Fragment {
    RecyclerView weatherRecyclerView;
    List<Weather> weatherList;
    City city;
    IpmaWeatherClient client = new IpmaWeatherClient();
    WeatherFragment(City city,List<Weather> weatherList){
        this.city=city;
        this.weatherList=weatherList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        TextView cityName = (TextView)view.findViewById(R.id.city_name_weather);
        cityName.setText("Weather for: "+city.getLocal());
        weatherRecyclerView=view.findViewById(R.id.recyclerview_weather);

        HelperAdapterWeather helperAdapter=new HelperAdapterWeather(getContext(),weatherList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        weatherRecyclerView.setLayoutManager(linearLayoutManager);
        weatherRecyclerView.setAdapter(helperAdapter);
        return view;
    }


    private void getWheatherForCity(){

        int localId = city.getGlobalIdLocal();
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {

                weatherList = forecast;

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                if (weatherList!=null){
                    HelperAdapterWeather helperAdapter=new HelperAdapterWeather(getContext(),weatherList);
                    weatherRecyclerView.setAdapter(helperAdapter);
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