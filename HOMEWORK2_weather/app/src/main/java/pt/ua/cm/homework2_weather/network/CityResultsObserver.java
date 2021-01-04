package pt.ua.cm.homework2_weather.network;

import java.util.HashMap;

import pt.ua.cm.homework2_weather.datamodel.City;

public interface  CityResultsObserver {
    public void receiveCitiesList(HashMap<String, City> citiesCollection);
    public void onFailure(Throwable cause);
}
