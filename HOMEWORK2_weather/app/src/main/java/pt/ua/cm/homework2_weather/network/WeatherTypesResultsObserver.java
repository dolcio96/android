package pt.ua.cm.homework2_weather.network;

import java.util.HashMap;

import pt.ua.cm.homework2_weather.datamodel.WeatherType;

public interface WeatherTypesResultsObserver {
    public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection);
    public void onFailure(Throwable cause);
}
