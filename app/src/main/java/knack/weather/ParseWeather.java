package knack.weather;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class ParseWeather
{
    Gson gson = new Gson();

    String jsonString;

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

        public String GetCity()
        {
            return getCity();
        }

        public String GetRegion()
        {
            return getRegion();
        }

        public String GetCountry()
        {
            return getCountry();
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

        public int GetChillInFahrenheit()
        {
            return getChill();
        }

        public double GetChillInCelsius()
        {
            return (getChill() - 32) / 1.8000;
        }

        public int GetDirection()
        {
            return getDirection();
        }

        public int GetSpeedInMph()
        {
            return getSpeed();
        }

        public double GetSpeedInKmh()
        {
            return getSpeed() * 1.609344;
        }

        public double GetSpeedInMetersInSecond()
        {
            return GetSpeedInKmh() * 0.2777777777778;
        }

        public String GetDirectionOfWind(int direction)
        {
            if (direction >= 0 && direction < 15)
            {
                return "северное";
            }
            else if (direction >= 15 && direction <= 75)
            {
                return "северо-восточное";
            }
            else if (direction > 75 && direction < 105)
            {
                return "восточное";
            }
            else if (direction >= 105 && direction <= 165)
            {
                return "юго-восточное";
            }
            else if (direction > 165 && direction < 195)
            {
                return "южное";
            }
            else if (direction >= 195 && direction <= 255)
            {
                return "юго-западное";
            }
            else if (direction > 255 && direction < 285)
            {
                return "западное";
            }
            else if (direction >= 285 && direction <= 345)
            {
                return "северо-западное";
            }
            else if (direction >= 345 && direction <= 360)
            {
                return "северное";
            }
            else return "Ошибка";
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
            return "Влажность: " + getHumidity() + ", Давление: " + getPressure() + ", Видимость: " + getVisibility();
        }

        public int GetHumidity()
        {
            return getHumidity();
        }
        
        public double GetPressureInInch()
        {
            return getPressure();
        }

        public double GetPressureInMm()
        {
            return getPressure() * 0.75006375541921;
        }

        public double GetVisibilityInMiles()
        {
            return getVisibility();
        }

        public double GetVisibilityInKilometers()
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

        public String GetUriImage()
        {
            return getUrl();
        }

        public int GetSizeImageOfX()
        {
            return getWidth();
        }

        public int GetSizeImageOfY()
        {
            return getHeight();
        }
    }

    public class Forecast
    {
        String date;
        String day;
        double high;
        double low;
        String text;
        int code;
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


        public String getDay()
        {
            switch (day)
            {
                case "Mon":
                    day = "Понедельник";
                    break;
                case "Tue":
                    day = "Вторник";
                    break;
                case "Wed":
                    day = "Среда";
                    break;
                case "Thu":
                    day = "Четверг";
                    break;
                case "Fri":
                    day = "Пятница";
                    break;
                case "Sat":
                    day = "Суббота";
                    break;
                case "Sun":
                    day = "Воскресенье";
                    break;
            }

            return day;
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

        public String GetCondidion(int code)
        {
            String condition = "";

            switch (code)
            {
                case 0:
                    condition = "Торнадо";
                    break;
                case 1:
                    condition = "Тропический шторм";
                    break;
                case 2:
                    condition = "Ураган";
                    break;
                case 3:
                    condition = "Сильные грозы";
                    break;
                case 4:
                    condition = "Грозы";
                    break;
                case 5:
                    condition = "Дождь со снегом";
                    break;
                case 6:
                    condition = "Дождь и мокрый снег";
                    break;
                case 7:
                    condition = "Снег и гололёд";
                    break;
                case 8:
                    condition = "Изморось";
                    break;
                case 9:
                    condition = "Мелкий дождь";
                    break;
                case 10:
                    condition = "Ледяной дождь";
                    break;
                case 11:
                    condition = "Кратковременные дожди";
                    break;
                case 12:
                    condition = "Кратковременные дожди";
                    break;
                case 13:
                    condition = "Снегопад";
                    break;
                case 14:
                    condition = "Небольшие снегопады";
                    break;
                case 15:
                    condition = "Метель";
                    break;
                case 16:
                    condition = "Снег";
                    break;
                case 17:
                    condition = "Град";
                    break;
                case 18:
                    condition = "Мокрый снег";
                    break;
                case 19:
                    condition = "Пыль";
                    break;
                case 20:
                    condition = "Туман";
                    break;
                case 21:
                    condition = "Лёгкий туман";
                    break;
                case 22:
                    condition = "Задымлённо";
                    break;
                case 23:
                    condition = "Ветреное";
                    break;
                case 24:
                    condition = "Небольшая ветреность";
                    break;
                case 25:
                    condition = "Прохладно";
                    break;
                case 26:
                    condition = "Облачно";
                    break;
                case 27:
                    condition = "Преимущественно облачно(ночь)";
                    break;
                case 28:
                    condition = "Преимущественно облачно(день)";
                    break;
                case 29:
                    condition = "Небольшая облачность(ночь)";
                    break;
                case 30:
                    condition = "Небольшая облачность(день)";
                    break;
                case 31:
                    condition = "Ясно(ночь)";
                    break;
                case 32:
                    condition = "Солнечно";
                    break;
                case 33:
                    condition = "Светло(ночь)";
                    break;
                case 34:
                    condition = "Светло(день)";
                    break;
                case 35:
                    condition = "Дождь с градом";
                    break;
                case 36:
                    condition = "Жарко";
                    break;
                case 37:
                    condition = "Отдельно грозы";
                    break;
                case 38:
                    condition = "Временами грзы";
                    break;
                case 39:
                    condition = "Временами грозы";
                    break;
                case 40:
                    condition = "Местами проливные дожди";
                    break;
                case 41:
                    condition = "Сильный снегопад";
                    break;
                case 42:
                    condition = "Редкий снег";
                    break;
                case 43:
                    condition = "Сильный снегопад";
                    break;
                case 44:
                    condition = "Переменная облачность";
                    break;
                case 45:
                    condition = "Ливни с грозами";
                    break;
                case 46:
                    condition = "Ливневый дождь";
                    break;
                case 47:
                    condition = "Местами ливни";
                    break;
            }

            return condition;
        }
    }

    public void Parse()
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
