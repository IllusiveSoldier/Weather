package knack.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
        Log.d(TAG, "myJsonString в WeatherOnDaysActivity: " + myJsonString);

        ParseWeather parseWeather = new ParseWeather(myJsonString);
        parseWeather.Parse();
        forecasts = parseWeather.forecast.getArrayOfForcasts();
        Log.d(TAG, forecasts[1].getText());

        initializeAdapter();
    }

    private void initializeAdapter()
    {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(forecasts);
        rv.setAdapter(recyclerViewAdapter);
        Log.d(TAG, "вызов initializeAdapter");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called in WeatherOnDaysActivity");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called in WeatherOnDaysActivity");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called in WeatherOnDaysActivity");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called in WeatherOnDaysActivity");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called in WeatherOnDaysActivity");
    }
}
