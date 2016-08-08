package knack.weather;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class CurrentWeatherFragment extends Fragment
{
    TextView InfoAboutWeather;
    ImageView ImageYahooView;

    public CurrentWeatherFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        ImageYahooView = (ImageView) view.findViewById(R.id.ImageYahooView);
        InfoAboutWeather = (TextView) view.findViewById(R.id.InfoAboutWeather);
        InfoAboutWeather.setMovementMethod(new ScrollingMovementMethod());
        InfoAboutWeather.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));
        Bundle bundle = getArguments();
        FormatOutput formatOutput = new FormatOutput(getActivity());
        ParseWeather parseWeather = new ParseWeather(bundle.getString("jsonString"));
        parseWeather.parse();
        if (parseWeather.isCheck())
        {
            setValuesInCurrentWeather(parseWeather, formatOutput);
        }
        else
        {
            InfoAboutWeather.setText(formatOutput.getMessageWithError());
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setValuesInCurrentWeather(ParseWeather parseWeather, FormatOutput formatOutput)
    {
        String s = formatOutput.getLocationLabel() + "\n" +
                formatOutput.getCountyLabel() + " " +
                parseWeather.location.getCountry() + "\n" +
                formatOutput.getRegionLabel() + " " +
                parseWeather.location.getRegion() + "\n" +
                formatOutput.getCityLabel() + " " +
                parseWeather.location.getCity() + "\n\n" +
                formatOutput.getWindLabel() + "\n" +
                formatOutput.getChillLabel() + " " +
                String.format(Locale.ENGLISH,
                        "%.1f", parseWeather.wind.getChillInCelsius()) +
                " \u2103" + "\n" +
                formatOutput.getDirectLabel() + " " +
                formatOutput.getDirectionOfWind(
                        parseWeather.wind.getDirection()) + "\n" +
                formatOutput.getSpeedOfWindLabel() + " " +
                String.format(Locale.ENGLISH,
                        "%.1f", parseWeather.wind.getSpeedInMetersInSecond()) + " " +
                formatOutput.getMetersInSecondLabel() + "\n\n" +
                formatOutput.getAtmosphereLabel() + "\n" +
                formatOutput.getHumidityLabel() + " " +
                parseWeather.atmosphere.getHumidity() + "%" + "\n" +
                formatOutput.getPressureLabel() + " " +
                (int)parseWeather.atmosphere.getPressureInMm() + " " +
                formatOutput.getMmHgLabel() + "\n" +
                formatOutput.getVisibilityLabel() + " " +
                (int)parseWeather.atmosphere.getVisibilityInKilometers() + " " +
                formatOutput.getKmLabel();


        InfoAboutWeather.setText(s);

        Picasso.with(getActivity())
                .load(parseWeather.image.getUrl())
                .placeholder(R.mipmap.ic_sync_black_24dp)
                .error(R.mipmap.ic_cloud_off_black_24dp)
                .into(ImageYahooView);
    }
}

