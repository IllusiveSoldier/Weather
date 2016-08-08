package knack.weather;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.WeatherViewHolder>
{
    Context context;

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
            this.WeatherInDay.setTypeface(Typeface.createFromAsset(itemView
                    .getContext()
                    .getAssets(),
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
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        WeatherViewHolder wvh = new WeatherViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder weatherViewHolder, int i)
    {
        FormatOutput formatOutput = new FormatOutput(context);
        String s =
                formatOutput.getTitleLabel() + " " + forecasts[i].getTitle() + "\n" +
                formatOutput.getDateLabel() + " " + forecasts[i].getDate() + "\n" +
                formatOutput.getDayLabel() + " " +
                        formatOutput.getDay(forecasts[i].getDay()) + "\n" +
                formatOutput.getMaxTempLabel() + " " +
                        String.format(Locale.ENGLISH,
                                "%.1f",
                                forecasts[i].getHighInCelsius()) + "\u2103" + "\n" +
                formatOutput.getMinTempLabel() + " " +
                        String.format(Locale.ENGLISH,
                                "%.1f", forecasts[i].getLowInCelsius()) + "\u2103" + "\n" +
                formatOutput.getConditionLabel() + " " +
                        formatOutput.getCondition(forecasts[i].getCode());

        weatherViewHolder.WeatherInDay.setText(s);
    }

    @Override
    public int getItemCount()
    {
        return forecasts.length;
    }

    public void setContext(Context c)
    {
        context = c;
    }
}
