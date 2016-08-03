package knack.weather;

import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WeatherViewHolder>
{
    final String TAG = "TEST";

    public static class WeatherViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView WeatherInDay;

        WeatherViewHolder(View itemView)
        {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            WeatherInDay = (TextView) itemView.findViewById(R.id.WeatherInDay);
            this.WeatherInDay.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf"));
        }
    }

    ParseWeather.Forecast[] forecasts;

    RecyclerViewAdapter(ParseWeather.Forecast[] f)
    {
        this.forecasts = f;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        WeatherViewHolder wvh = new WeatherViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder weatherViewHolder, int i)
    {

        weatherViewHolder.WeatherInDay.setText("Заголовок: " + forecasts[i].getTitle() + "\n" +
                                                "Дата: " + forecasts[i].getDate() + "\n" +
                                               "День: " + forecasts[i].getDay() + "\n" +
                                               "Макс.температура(\u2103): " + String.format(
                                                "%.1f", forecasts[i].getHighInCelsius()) + "\n" +
                                               "Мин. температура(\u2103): " + String.format(
                                               "%.1f", forecasts[i].getLowInCelsius()) + "\n" +
                                               "Состояние: " + forecasts[i]
                                                                .GetCondidion(forecasts[i]
                                                                .getCode()));
    }

    @Override
    public int getItemCount()
    {
        return forecasts.length;
    }
}
