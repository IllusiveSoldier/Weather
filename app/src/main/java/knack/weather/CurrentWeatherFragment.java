package knack.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        InfoAboutWeather = (TextView) view.findViewById(R.id.InfoAboutWeather);
        Bundle bundle = getArguments();
        ParseWeather parseWeather = new ParseWeather(bundle.getString("jsonString"));
        parseWeather.Parse();
        if (parseWeather.isCheck())
        {
            InfoAboutWeather.setText("Страна: " + parseWeather.location.GetCountry() + "\n" +
                    "Район: " + parseWeather.location.GetRegion() + "\n" +
                    "Город: " + parseWeather.location.GetCity() + "\n" +
                    "Температура ветра(\u2103): " + String.format(
                    "%.1f", parseWeather.wind.GetChillInCelsius()) + "\n" +
                    "Направление ветра: " + parseWeather
                    .wind
                    .GetDirectionOfWind(parseWeather
                            .wind
                            .getDirection()) + "\n" +
                    "Скорость ветра(м/с): " + String.format("%.1f", parseWeather.wind
                    .GetSpeedInMetersInSecond()) + "\n" +
                    "Влажность(%): " +
                    Integer.toString(parseWeather.atmosphere.GetHumidity()) + "\n" +
                    "Давление(mmHg): " +
                    Double.toString((int)parseWeather.atmosphere.GetPressureInMm())
                    + "\n" +
                    "Видимость(км): " +
                    Double.toString((int)parseWeather.atmosphere
                            .GetVisibilityInKilometers()));
        }
        else
        {
            InfoAboutWeather.setText("Ошибка загрузки погоды из этого места!");
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }
}

