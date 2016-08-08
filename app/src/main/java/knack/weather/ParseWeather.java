package knack.weather;

import android.content.Context;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class ParseWeather
{
    Gson gson = new Gson();

    String jsonString;

    Context context;


    Location location = new Location();
    Wind wind = new Wind();
    Atmosphere atmosphere = new Atmosphere();
    Forecast forecast = new Forecast();

    Image image = new Image();

    public ParseWeather(String _jsonString)
    {
        jsonString = _jsonString;
    }

    public boolean isCheck()
    {
        return check;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }

    boolean check;

    public class Location
    {
        String city;
        String country;
        String region;

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public String getCountry()
        {
            return country;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }

        public String getRegion()
        {
            return region;
        }

        public void setRegion(String region)
        {
            this.region = region;
        }

        public String getLocation()
        {
            return getCountry() + ", " + getRegion() + ", " + getCity();
        }
    }

    public class Wind
    {
        int chill;
        int direction;
        int speed;

        public int getChill()
        {
            return chill;
        }

        public void setChill(int chill)
        {
            this.chill = chill;
        }

        public int getDirection()
        {
            return direction;
        }

        public void setDirection(int direction)
        {
            this.direction = direction;
        }

        public int getSpeed()
        {
            return speed;
        }

        public void setSpeed(int speed)
        {
            this.speed = speed;
        }

        public String getWind()
        {
            return "Температура ветра: " + getChill() + ", Направление ветра: " + getDirection() +
                    "Скорость ветра: " + getSpeed();
        }

        public int getChillInFahrenheit()
        {
            return getChill();
        }

        public double getChillInCelsius()
        {
            return (getChill() - 32) / 1.8000;
        }

        public int getSpeedInMph()
        {
            return getSpeed();
        }

        public double getSpeedInKmh()
        {
            return getSpeed() * 1.609344;
        }

        public double getSpeedInMetersInSecond()
        {
            return getSpeedInKmh() * 0.2777777777778;
        }

    }

    public class Atmosphere
    {
        int humidity;
        double pressure;
        double visibility;

        public int getHumidity()
        {
            return humidity;
        }

        public double getPressure()
        {
            return pressure;
        }

        public double getVisibility()
        {
            return visibility;
        }

        public void setVisibility(double visibility)
        {
            this.visibility = visibility;
        }

        public void setPressure(double pressure)
        {
            this.pressure = pressure;
        }

        public void setHumidity(int humidity)
        {
            this.humidity = humidity;
        }

        public String getAtmosphere()
        {
            return "Влажность: " + getHumidity() +
                 ", Давление: " + getPressure() +
                 ", Видимость: " + getVisibility();
        }

        
        public double getPressureInInch()
        {
            return getPressure();
        }

        public double getPressureInMm()
        {
            return getPressure() * 0.75006375541921;
        }

        public double getVisibilityInMiles()
        {
            return getVisibility();
        }

        public double getVisibilityInKilometers()
        {
            return getVisibility() * 1.609344;
        }
    }

    public class Image
    {
        int width;
        int height;
        String url;

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public void setHeight(int height)
        {
            this.height = height;
        }

        public void setWidth(int width)
        {
            this.width = width;
        }
    }

    public class Forecast
    {
        String date;

        public String getDay()
        {
            return day;
        }

        public void setDay(String day)
        {
            this.day = day;
        }

        String day;
        double high;
        double low;
        String text;
        int code;
        String title;
        String readyString;
        Forecast[] ArrayOfForcasts;

        public String getReadyString()
        {
            return readyString;
        }

        public void setReadyString(String readyString)
        {
            this.readyString += readyString;
        }

        public String getText()
        {
            return text;
        }

        public double getLow()
        {
            return low;
        }

        public double getLowInCelsius()
        {
            return (low - 32) / 1.8000;
        }

        public double getHigh()
        {
            return high;
        }

        public double getHighInCelsius()
        {
            return (high - 32) / 1.8000;
        }

        public String getDate()
        {
            return date;
        }

        public Forecast[] getArrayOfForcasts()
        {
            return ArrayOfForcasts;
        }

        public int getCode()
        {
            return code;
        }

        public void setCode(int code)
        {
            this.code = code;
        }

        public void setArrayOfForcasts(Forecast[] arrayOfForcasts)
        {
            ArrayOfForcasts = arrayOfForcasts;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }

    public void parse()
    {
        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObjectQuery = jsonObject.getJSONObject("query");
            JSONObject jsonObjectResults = jsonObjectQuery.getJSONObject("results");
            JSONObject jsonObjectChannel = jsonObjectResults.getJSONObject("channel");
            JSONObject jsonObjectItem = jsonObjectChannel.getJSONObject("item");

            JSONObject jsonObjectLocation = jsonObjectChannel.getJSONObject("location");
            JSONObject jsonObjectWind = jsonObjectChannel.getJSONObject("wind");
            JSONObject jsonObjectAtmosphere = jsonObjectChannel.getJSONObject("atmosphere");

            JSONObject jsonObjectImage = jsonObjectChannel.getJSONObject("image");

            Forecast[] forecasts = gson.fromJson(jsonObjectItem.getJSONArray("forecast").toString(),
                                                                                Forecast[].class);
            for (Forecast value : forecasts)
            {
                value.setTitle(jsonObjectItem.getString("title"));
            }
            forecast.setArrayOfForcasts(forecasts);

            location = gson.fromJson(jsonObjectLocation.toString(), Location.class);
            wind = gson.fromJson(jsonObjectWind.toString(), Wind.class);
            atmosphere = gson.fromJson(jsonObjectAtmosphere.toString(), Atmosphere.class);
            image = gson.fromJson(jsonObjectImage.toString(), Image.class);

            setCheck(true);
        }
        catch (JSONException e)
        {
            setCheck(false);
        }
    }
}
