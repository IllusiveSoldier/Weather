package knack.weather;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherFragment extends Fragment
{
    EditText CityEditText;
    Button RefreshButton;
    Button GetMyWeatherButton;
    Snackbar snackbar;
    FloatingActionButton MicFloatingButton;

    // Ответ от сервера с погодой
    String rawJsonString;
    // Буффер для SnackBar
    String bufferCityEditText;

    // Для записи голоса
    private final int REQ_CODE_SPEECH_INPUT = 100;

    // Флаг для отладки
    final String TAG = "TEST";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        CityEditText = (EditText) view.findViewById(R.id.CityEditText);
        CityEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        GetMyWeatherButton = (Button) view.findViewById(R.id.GetMyWeatherButton);
        GetMyWeatherButton.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        // --- СЛУШАТЕЛЬ onClick ДЛЯ КНОПКИ GetMyWeatherButton ---
        GetMyWeatherButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String city = CityEditText.getText().toString();
                String yourUri = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%" +
                        "20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo." +
                        "places(1)%20where%20text%3D%22" + city + "%22)&format=json&env=store%3A" +
                        "%2F%2Fdatatables.org%2Falltableswithkeys";
                if (!(city.equalsIgnoreCase("")))
                {
                    try
                    {
                        DownloadTest downloadTest = new DownloadTest();
                        downloadTest.execute(new String[] { yourUri});
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity().getApplicationContext(),
                                getResources().getString(R.string.message_error_error) + "\n" +
                                        getResources().getString(R.string.message_error_message) + ": " +
                                        e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.message_error_enter_city),
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        RefreshButton = (Button) view.findViewById(R.id.RefreshButton);
        // --- СЛУШАТЕЛЬ onClick ДЛЯ КНОПКИ RefreshButton ---
        RefreshButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DataWithCities dataWithCities = new DataWithCities();
                CityEditText.setText(dataWithCities.getRandomCity());
            }
        });

        // --- СЛУШАТЕЛЬ onLongClick ДЛЯ КНОПКИ RefreshButton ---
        RefreshButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                bufferCityEditText = CityEditText.getText().toString();
                CityEditText.setText("");
                snackbar = Snackbar
                        .make(view, getResources()
                                        .getString(R.string.message_notify_city_is_remove),
                                Snackbar.LENGTH_LONG)
                        .setAction(getResources()
                                .getString(R.string.message_action_undo), new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                CityEditText.setText(bufferCityEditText);
                            }
                        });
                if (Build.VERSION.SDK_INT >= 23)
                {
                    snackbar.setActionTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                            R.color.bright_green));
                }
                else
                {
                    snackbar.setActionTextColor(getResources().getColor(R.color.bright_green));
                }
                snackbar.show();
                return true;
            }
        });

        MicFloatingButton = (FloatingActionButton) view.findViewById(R.id.MicFloatingButton);
        MicFloatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                promptSpeechInput();
            }
        });

        return view;
    }

    private void promptSpeechInput()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.label_explain_say_something));
        try
        {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a)
        {
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.message_error_mic_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_CODE_SPEECH_INPUT:
            {
                if (resultCode == getActivity().RESULT_OK && null != data)
                {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    CityEditText.setText(result.get(0));
                }
                break;
            }
        }
    }


    // Класс для GET-запроса и получения ответа
    @TargetApi(19)
    public class GetExample
    {
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException
        {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute())
            {
                return response.body().string();
            }
        }
    }

    // AsyncTask
    public class DownloadTest extends AsyncTask<String, Integer, String>
    {
        @Override
        protected void onPreExecute()
        {
            // Перед началом выполенения вырубаем кнопку
            GetMyWeatherButton.setEnabled(false);
            GetMyWeatherButton.setText(getResources().getString(R.string.label_explain_loading));
        }

        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                GetExample example = new GetExample();
                rawJsonString = example.run(urls[0]);
            }
            catch (IOException e)
            {
                rawJsonString = "Error";
            }
            return rawJsonString;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // После выполнения - включаем
            GetMyWeatherButton.setEnabled(true);
            GetMyWeatherButton.setText(getResources().getString(R.string.label_widget_text_get_weather));
            bindOfValues(result);
        }

        private void bindOfValues(String values)
        {
            Intent intent = new Intent(getActivity(),
                    CurrentAndWeatherForecastActivity.class);
            intent.putExtra("jsonString", values);
            startActivity(intent);
        }
    }
}
