package pt.ua.cm.homework2_weather;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import pt.ua.cm.homework2_weather.datamodel.City;

public class HelperAdapterCity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    View view;
    ArrayList<String> arrayListName = new ArrayList<String>();
    ArrayList<City> arrayLisCity = new ArrayList<City>();
    Map<String, City> cities;

    CityViewHolderClass viewHolderClass;
    Boolean isPhone = false;
    public HelperAdapterCity(Context context,Map<String,City> cities, Boolean isPhone){
        this.context=context;
        this.cities=cities;
        this.isPhone = isPhone;
        for (Map.Entry<String, City> entry : cities.entrySet()) {
            arrayListName.add(entry.getKey());
            arrayLisCity.add(entry.getValue());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_city_item,parent,false);
         viewHolderClass= new CityViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolderClass=(CityViewHolderClass)holder;
        viewHolderClass.textView.setText(arrayListName.get(position));
        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPhone){
                    devicePhone(position);
                    Log.d("PHONE","PHONE");
                }
                else{
                    deviceTablet(v,position);
                    Log.d("TABLET","TABLET");
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayListName.size();
    }

    public class CityViewHolderClass extends RecyclerView.ViewHolder {
        TextView textView;
        public CityViewHolderClass(@NonNull View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.city_text_view);
        }

    }

    public void devicePhone(int position){
        Intent intent = new Intent(context, WeatherForCityActivity.class);
        //Log.d("FUNZIA0",arrayLisCity.get(position).toString());
        intent.putExtra(WeatherForCityActivity.EXTRA_INTENT,(Serializable) arrayLisCity.get(position));
        context.startActivity(intent);
    }
    public void deviceTablet(View v,int position){
        WaitingFragment fragment = new WaitingFragment(arrayLisCity.get(position));
        FragmentManager fm = ((MainActivity) v.getContext()).getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.container_weather_prov, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

