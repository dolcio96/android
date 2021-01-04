package pt.ua.cm.homework2_weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.ua.cm.homework2_weather.datamodel.Weather;

public class HelperAdapterWeather extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<Weather> weather;

    public HelperAdapterWeather(Context context,List< Weather> weather) {
        this.context=context;
        this.weather=weather;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_weather_item,parent,false);
        ViewHolderWeatherClass viewHolderClass= new ViewHolderWeatherClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderWeatherClass viewHolderClass=(ViewHolderWeatherClass)holder;
        viewHolderClass.forecastTextView.setText((CharSequence) weather.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public class ViewHolderWeatherClass extends RecyclerView.ViewHolder {
        TextView forecastTextView;

        public ViewHolderWeatherClass(@NonNull View itemView) {
            super(itemView);
            forecastTextView=(TextView)itemView.findViewById(R.id.forecastTextView);

        }
    }
}
