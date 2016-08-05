package knack.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherOnAnotherDaysFragment extends Fragment
{
    ParseWeather.Forecast[] forecasts;
    RecyclerView rv;

    public WeatherOnAnotherDaysFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_weather_on_another_days, container, false);

        Bundle bundle = getArguments();
        ParseWeather parseWeather = new ParseWeather(bundle.getString("jsonString"));
        parseWeather.Parse();
        forecasts = parseWeather.forecast.getArrayOfForcasts();

        if (parseWeather.isCheck())
        {
            rv = (RecyclerView) view.findViewById(R.id.rv);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
            rv.setLayoutManager(llm);
            rv.setHasFixedSize(true);

            initializeAdapter();
        }

        return view;
    }

    private void initializeAdapter()
    {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(forecasts);
        recyclerViewAdapter.setContext(getActivity());
        rv.setAdapter(recyclerViewAdapter);
    }
}
