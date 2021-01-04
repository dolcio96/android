package pt.ua.cm.homework2_weather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import pt.ua.cm.homework2_weather.datamodel.City;


public class CityFragment extends Fragment {
    RecyclerView cityRecyclerView;
    Map<String, City> cities;
    ArrayList<String> names;
    Boolean isPhone;

    CityFragment( Map<String, City> cities){
        this.cities=cities;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new ArrayList<String>();
        Iterator it = cities.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            names.add(pair.getKey().toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        cityRecyclerView=view.findViewById(R.id.recyclerview_cities);
        HelperAdapterCity helperAdapter=new HelperAdapterCity(getContext(),cities,checkScreenDimension());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        cityRecyclerView.setLayoutManager(linearLayoutManager);
        cityRecyclerView.setAdapter(helperAdapter);
        return view;
    }

    public Boolean checkScreenDimension(){
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        Log.d("PIXEL",String.valueOf(dpWidth));
        if(dpWidth<600){
            return isPhone=true;
        }
        else {
            return isPhone=false;
        }
    }

}