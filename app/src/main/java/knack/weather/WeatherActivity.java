package knack.weather;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
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

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null)
            {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            WeatherFragment weatherFragment = new WeatherFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.FragmentContainer, weatherFragment).commit();
        }
    }
}