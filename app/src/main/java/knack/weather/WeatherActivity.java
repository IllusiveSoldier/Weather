package knack.weather;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (findViewById(R.id.FragmentContainer) != null)
        {
            if (savedInstanceState != null)
            {
                return;
            }

            WeatherFragment weatherFragment = new WeatherFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.FragmentContainer, weatherFragment).commit();
        }
    }
}