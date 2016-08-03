package knack.weather;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity
{
    public final String jsonKey = "JSON_STRING";
    public final String imageKey = "IMAGE_STRING";
    public final String rawJsonKey = "RAW_JSON_STRING";

    final String TAG = "TEST";


    Button getMyWeatherButton;
    Button RefreshButton;
    Button GetWeatherOnTenDays;
    EditText CityEditText;

    TextView InfoAboutWeather;

    ImageView YahooImageView;

    String myJsonString;
    String readyString;
    String uriImage;

    ParseWeather.Forecast[] forecasts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        InfoAboutWeather = (TextView) findViewById(R.id.InfoAboutWeather);
        CityEditText = (EditText) findViewById(R.id.CityEditText);
        YahooImageView = (ImageView) findViewById(R.id.YahooImageView);
        getMyWeatherButton = (Button) findViewById(R.id.getMyWeatherButton);
        GetWeatherOnTenDays = (Button) findViewById(R.id.GetWeatherOnTenDays);

        myJsonString = "";

        CityEditText.setTypeface(Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf"));
        InfoAboutWeather.setTypeface(Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf"));
        getMyWeatherButton.setTypeface(Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf"));
        GetWeatherOnTenDays.setTypeface(Typeface.createFromAsset(getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        getMyWeatherButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String city = CityEditText.getText().toString();
                String yourUri = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + city + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                Log.d(TAG, "City: " + city);
                if (!(city.equalsIgnoreCase("")))
                {
                    try
                    {
                        DownloadTest downloadTest = new DownloadTest();
                        downloadTest.execute(new String[] { yourUri});
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Ошибка! \n Сообщение: "
                                + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(WeatherActivity.this,
                                    "Чтобы просмотреть погоду введи название города.",
                                    Toast.LENGTH_LONG)
                                    .show();
                }
            }
        });

        RefreshButton = (Button) findViewById(R.id.RefreshButton);
        RefreshButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DataWithCities dataWithCities = new DataWithCities();
                CityEditText.setText(dataWithCities.GetRandomCity());
            }
        });
        RefreshButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                CityEditText.setText("");
                return true;
            }
        });

        GetWeatherOnTenDays.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (myJsonString.equalsIgnoreCase(""))
                {
                    Toast.makeText(WeatherActivity.this, "Вижу, вы не запросили погоду.",
                                                    Toast.LENGTH_LONG)
                                                    .show();
                }
                else
                {
                    Intent intent = new Intent(WeatherActivity.this, WeatherOnDaysActivity.class);
                    intent.putExtra("jsonString", myJsonString);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        Log.d(TAG, "Вызов onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(jsonKey, readyString);
        savedInstanceState.putString(imageKey, uriImage);
        savedInstanceState.putString(rawJsonKey, myJsonString);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "Вызов onRestoreInstanceState");

        if (savedInstanceState != null)
        {
            readyString = savedInstanceState.getString(jsonKey, "");
            InfoAboutWeather.setText(readyString);
            uriImage = savedInstanceState.getString(imageKey, "");
            myJsonString = savedInstanceState.getString(rawJsonKey, "");
            if (!(uriImage.isEmpty()))
            {
                Picasso.with(getApplicationContext())
                        .load(uriImage)
                        .placeholder(R.mipmap.ic_sync_black_24dp)
                        .error(R.mipmap.ic_cloud_off_black_24dp)
                        .into(YahooImageView);
            }
        }
    }

    // Класс для GET-запроса и получения ответа
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
            getMyWeatherButton.setEnabled(false);
            Log.d(TAG, "Кнопка ПЕРЕД AsyncTask: " + Boolean.toString(getMyWeatherButton.isEnabled()));
        }

        @Override
        protected String doInBackground(String... urls)
        {
            // TODO Auto-generated method stub
            // Call your web service here
            try
            {
                GetExample example = new GetExample();
                myJsonString = example.run(urls[0]);
                Log.d(TAG, "Запрос УСПЕШНО прошёл");
            }
            catch (IOException e)
            {
                myJsonString = "Error";
                Log.d(TAG, "Запрос НЕ прошёл");
            }
            return myJsonString;
        }

        @Override
        protected void onPostExecute(String result)
        {
            // TODO Auto-generated method stub
            // Update your UI here
            //TextView.setText(result);
            // После выполнения - включаем
            getMyWeatherButton.setEnabled(true);
            Log.d("TAG", "Кнопка ПОСЛЕ AsyncTask: " + Boolean.toString(getMyWeatherButton.isEnabled()));
            Log.d("TAG", result);

            BindOfValues(result);
        }

        private void BindOfValues(String values)
        {
            Log.d(TAG, "values: " + values);
            ParseWeather parseWeather = new ParseWeather(values);
            parseWeather.Parse();

            readyString =
                    "Страна: " + parseWeather.location.GetCountry() + "\n" +
                    "Район: " + parseWeather.location.GetRegion() + "\n" +
                    "Город: " + parseWeather.location.GetCity() + "\n" +
                    "Температура ветра(\u2103): " + String.format(Locale.ENGLISH, "%.1f",
                                                                                     parseWeather
                                                                                     .wind
                                                                    .GetChillInCelsius()) + "\n" +
                    "Направление ветра: " +
                    parseWeather.wind.GetDirectionOfWind(parseWeather.wind.getDirection()) + "\n" +
                    "Скорость ветра(м/с): " +
                    String.format(Locale.ENGLISH, "%.1f", parseWeather
                                                            .wind
                                                            .GetSpeedInMetersInSecond()) + "\n" +
                    "Влажность(%): " +
                    Integer.toString(parseWeather.atmosphere.GetHumidity()) + "\n" +
                    "Давление(mmHg): " +
                    Double.toString((int)parseWeather.atmosphere.GetPressureInMm()) + "\n" +
                    "Видимость(км): " +
                    Double.toString((int)parseWeather.atmosphere.GetVisibilityInKilometers());

            uriImage = parseWeather.image.getUrl();

            InfoAboutWeather.setText(readyString);

            Picasso.with(getApplicationContext())
                    .load(uriImage)
                    .placeholder(R.mipmap.ic_sync_black_24dp)
                    .error(R.mipmap.ic_cloud_off_black_24dp)
                    .into(YahooImageView);

            forecasts = parseWeather.forecast.getArrayOfForcasts();

            Log.d(TAG, "forecasts: " + forecasts[1].getText());
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d("TAG", "onStart() called in WeatherActivity");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called in WeatherActivity");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called in WeatherActivity");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called in WeatherActivity");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called in WeatherActivity");
    }
}