package knack.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class WeatherOnDaysActivity extends AppCompatActivity
{
    String myJsonString;
    ParseWeather.Forecast[] forecasts;
    RecyclerView rv;

    final String TAG = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_on_days);


        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        Intent intent = getIntent();
        myJsonString = intent.getStringExtra("jsonString");

        ParseWeather parseWeather = new ParseWeather(myJsonString);
        parseWeather.Parse();
        forecasts = parseWeather.forecast.getArrayOfForcasts();

        initializeAdapter();
    }

    private void initializeAdapter()
    {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(forecasts);
        rv.setAdapter(recyclerViewAdapter);
    }
}
