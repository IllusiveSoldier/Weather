package knack.weather;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    EditText CityEditText;
    Button RefreshButton;
    Button GetMyWeatherButton;
    Snackbar snackbar;
    FloatingActionButton LocationFloatingButton;
    FloatingActionButton MicFloatingButton;

    // Ответ от сервера с погодой
    String rawJsonString;
    // Буффер для SnackBar
    String bufferCityEditText;

    // Для работы с местоположением
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location location;
    private GoogleApiClient googleApiClient;
    public static final int NUMBER_OF_REQUEST = 1;

    // Для записи голоса
    private final int REQ_CODE_SPEECH_INPUT = 100;

    // Флаг для отладки
    final String TAG = "TEST";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (checkPlayServices())
        {
            buildGoogleApiClient();
        }


        CityEditText = (EditText) findViewById(R.id.CityEditText);
        CityEditText.setTypeface(Typeface.createFromAsset(getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        GetMyWeatherButton = (Button) findViewById(R.id.GetMyWeatherButton);
        GetMyWeatherButton.setTypeface(Typeface.createFromAsset(getAssets(),
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
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.message_error_error) + "\n" +
                                getResources().getString(R.string.message_error_message) + ": " +
                                        e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(WeatherActivity.this,
                            getResources().getString(R.string.message_error_enter_city),
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        RefreshButton = (Button) findViewById(R.id.RefreshButton);
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
                    snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
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


        LocationFloatingButton = (FloatingActionButton) findViewById(R.id.LocationFloatingButton);
        LocationFloatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int permissionCheck = ContextCompat.checkSelfPermission(WeatherActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                Log.d(TAG, "permissionCheck: " + permissionCheck);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED)
                    {
                        if (ActivityCompat
                                .shouldShowRequestPermissionRationale(WeatherActivity.this,
                                        Manifest.permission.ACCESS_FINE_LOCATION))
                        {
                        }
                        else
                        {
                            requestPermissions(new String[]
                                    { Manifest.permission.ACCESS_FINE_LOCATION },
                                    NUMBER_OF_REQUEST);
                        }
                    }
                    else
                    {
                         displayLocation();
                    }
                }
            }
        });

        MicFloatingButton = (FloatingActionButton) findViewById(R.id.MicFloatingButton);
        MicFloatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                promptSpeechInput();
            }
        });
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
            Toast.makeText(getApplicationContext(),
                    getString(R.string.message_error_mic_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_CODE_SPEECH_INPUT:
            {
                if (resultCode == RESULT_OK && null != data)
                {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    CityEditText.setText(result.get(0));
                }
                break;
            }
        }
    }

    private void displayLocation()
    {
        location = LocationServices.FusedLocationApi
                .getLastLocation(googleApiClient);

        if (location != null)
        {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            try
            {
                Geocoder geocoder = new Geocoder(WeatherActivity.this, Locale.getDefault());
                CityEditText.setText(geocoder.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(1));
            }
            catch (IOException e)
            {
                Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(WeatherActivity.this,
                    getResources().getString(R.string.message_action_activate_gps),
                    Toast.LENGTH_LONG).show();
        }
    }


    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {

                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (googleApiClient != null)
        {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        checkPlayServices();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0)
    {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0)
    {
        googleApiClient.connect();
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
            Intent intent = new Intent(WeatherActivity.this,
                                       CurrentAndWeatherForecastActivity.class);
            intent.putExtra("jsonString", values);
            startActivity(intent);
        }
    }
}