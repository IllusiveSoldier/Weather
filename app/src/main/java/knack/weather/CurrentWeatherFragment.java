package knack.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class CurrentWeatherFragment extends Fragment
{
    TextView InfoAboutWeather;

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

        InfoAboutWeather = (TextView) view.findViewById(R.id.InfoAboutWeather);
        Bundle bundle = getArguments();
        FormatOutput formatOutput = new FormatOutput(getActivity());
        ParseWeather parseWeather = new ParseWeather(bundle.getString("jsonString"));
        parseWeather.Parse();
        if (parseWeather.isCheck())
        {
            String s =
                    formatOutput.GetCountyLabel() + " " +
                            parseWeather.location.GetCountry() + "\n" +
                    formatOutput.GetRegionLabel() + " " +
                            parseWeather.location.GetRegion() + "\n" +
                    formatOutput.GetCityLabel() + " " +
                            parseWeather.location.GetCity() + "\n" +
                    formatOutput.GetChillLabel() + " " +
                            String.format(Locale.ENGLISH,
                                    "%.1f", parseWeather.wind.GetChillInCelsius()) + "\n" +
                    formatOutput.GetDirectLabel() + " " +
                            formatOutput.GetDirectionOfWind(
                                    parseWeather.wind.GetDirection()) + "\n" +
                    formatOutput.GetSppedOfWindLabel() + " " +
                            String.format(Locale.ENGLISH,
                                    "%.1f", parseWeather.wind.GetSpeedInMetersInSecond()) + " " +
                            formatOutput.GetmetersInsecondLabel() + "\n" +
                    formatOutput.GetHumidityLabel() + " " +
                            parseWeather.atmosphere.GetHumidity() + "%" + "\n" +
                    formatOutput.GetPressureLabel() + " " +
                            (int)parseWeather.atmosphere.GetPressureInMm() + " " +
                            formatOutput.GetmmHgLabel() + "\n" +
                    formatOutput.GetVisibilitylabel() + " " +
                            (int)parseWeather.atmosphere.GetVisibilityInKilometers() + " " +
                            formatOutput.GetKmlabelLabel();

            InfoAboutWeather.setText(s);
        }
        else
        {
            InfoAboutWeather.setText(formatOutput.GetMessageWithError());
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }
}

