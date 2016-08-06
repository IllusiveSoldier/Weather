package knack.weather;

import android.content.Context;

public class FormatOutput
{
    Context context;

    public FormatOutput(Context c)
    {
        context = c;
    }

    public String GetDirectionOfWind(int direction)
    {
        if (direction >= 0 && direction < 15)
        {
            return context.getResources().getString(R.string.label_direction_north);
        }
        else if (direction >= 15 && direction <= 75)
        {
            return context.getResources().getString(R.string.label_direction_north_east);
        }
        else if (direction > 75 && direction < 105)
        {
            return context.getResources().getString(R.string.label_direction_east);
        }
        else if (direction >= 105 && direction <= 165)
        {
            return context.getResources().getString(R.string.label_direction_south_east);
        }
        else if (direction > 165 && direction < 195)
        {
            return context.getResources().getString(R.string.label_direction_south);
        }
        else if (direction >= 195 && direction <= 255)
        {
            return context.getResources().getString(R.string.label_direction_south_west);
        }
        else if (direction > 255 && direction < 285)
        {
            return context.getResources().getString(R.string.label_direction_west);
        }
        else if (direction >= 285 && direction <= 345)
        {
            return context.getResources().getString(R.string.label_direction_north_west);
        }
        else if (direction >= 345 && direction <= 360)
        {
            return context.getResources().getString(R.string.label_direction_north);
        }
        else return context.getResources().getString(R.string.label_direction_error);
    }

    public String GetDay(String d)
    {
        String day = "";

        switch (d)
        {
            case "Mon":
                day = context.getResources().getString(R.string.label_day_of_the_week_monday);
                break;
            case "Tue":
                day = context.getResources().getString(R.string.label_day_of_the_week_tuesday);
                break;
            case "Wed":
                day = context.getResources().getString(R.string.label_day_of_the_week_wednesday);
                break;
            case "Thu":
                day = context.getResources().getString(R.string.label_day_of_the_week_thursday);
                break;
            case "Fri":
                day = context.getResources().getString(R.string.label_day_of_the_week_friday);
                break;
            case "Sat":
                day = context.getResources().getString(R.string.label_day_of_the_week_saturday);
                break;
            case "Sun":
                day = context.getResources().getString(R.string.label_day_of_the_week_Sunday);
                break;
        }

        return day;
    }

    public String GetCondition(int code)
    {
        String condition = "";

        switch (code)
        {
            case 0:
                condition = context.getResources().getString(R.string.label_weather_code_0);
                break;
            case 1:
                condition = context.getResources().getString(R.string.label_weather_code_1);
                break;
            case 2:
                condition = context.getResources().getString(R.string.label_weather_code_2);
                break;
            case 3:
                condition = context.getResources().getString(R.string.label_weather_code_3);
                break;
            case 4:
                condition = context.getResources().getString(R.string.label_weather_code_4);
                break;
            case 5:
                condition = context.getResources().getString(R.string.label_weather_code_5);
                break;
            case 6:
                condition = context.getResources().getString(R.string.label_weather_code_6);
                break;
            case 7:
                condition = context.getResources().getString(R.string.label_weather_code_7);
                break;
            case 8:
                condition = context.getResources().getString(R.string.label_weather_code_8);
                break;
            case 9:
                condition = context.getResources().getString(R.string.label_weather_code_9);
                break;
            case 10:
                condition = context.getResources().getString(R.string.label_weather_code_10);
                break;
            case 11:
                condition = context.getResources().getString(R.string.label_weather_code_11);
                break;
            case 12:
                condition = context.getResources().getString(R.string.label_weather_code_12);
                break;
            case 13:
                condition = context.getResources().getString(R.string.label_weather_code_13);
                break;
            case 14:
                condition = context.getResources().getString(R.string.label_weather_code_14);
                break;
            case 15:
                condition = context.getResources().getString(R.string.label_weather_code_15);
                break;
            case 16:
                condition = context.getResources().getString(R.string.label_weather_code_16);
                break;
            case 17:
                condition = context.getResources().getString(R.string.label_weather_code_17);
                break;
            case 18:
                condition = context.getResources().getString(R.string.label_weather_code_18);
                break;
            case 19:
                condition = context.getResources().getString(R.string.label_weather_code_19);
                break;
            case 20:
                condition = context.getResources().getString(R.string.label_weather_code_20);
                break;
            case 21:
                condition = context.getResources().getString(R.string.label_weather_code_21);
                break;
            case 22:
                condition = context.getResources().getString(R.string.label_weather_code_22);
                break;
            case 23:
                condition = context.getResources().getString(R.string.label_weather_code_23);
                break;
            case 24:
                condition = context.getResources().getString(R.string.label_weather_code_24);
                break;
            case 25:
                condition = context.getResources().getString(R.string.label_weather_code_25);
                break;
            case 26:
                condition = context.getResources().getString(R.string.label_weather_code_26);
                break;
            case 27:
                condition = context.getResources().getString(R.string.label_weather_code_27);
                break;
            case 28:
                condition = context.getResources().getString(R.string.label_weather_code_28);
                break;
            case 29:
                condition = context.getResources().getString(R.string.label_weather_code_29);
                break;
            case 30:
                condition = context.getResources().getString(R.string.label_weather_code_30);
                break;
            case 31:
                condition = context.getResources().getString(R.string.label_weather_code_31);
                break;
            case 32:
                condition = context.getResources().getString(R.string.label_weather_code_32);
                break;
            case 33:
                condition = context.getResources().getString(R.string.label_weather_code_33);
                break;
            case 34:
                condition = context.getResources().getString(R.string.label_weather_code_34);
                break;
            case 35:
                condition = context.getResources().getString(R.string.label_weather_code_35);
                break;
            case 36:
                condition = context.getResources().getString(R.string.label_weather_code_36);
                break;
            case 37:
                condition = context.getResources().getString(R.string.label_weather_code_37);
                break;
            case 38:
                condition = context.getResources().getString(R.string.label_weather_code_38);
                break;
            case 39:
                condition = context.getResources().getString(R.string.label_weather_code_39);
                break;
            case 40:
                condition = context.getResources().getString(R.string.label_weather_code_40);
                break;
            case 41:
                condition = context.getResources().getString(R.string.label_weather_code_41);
                break;
            case 42:
                condition = context.getResources().getString(R.string.label_weather_code_42);
                break;
            case 43:
                condition = context.getResources().getString(R.string.label_weather_code_43);
                break;
            case 44:
                condition = context.getResources().getString(R.string.label_weather_code_44);
                break;
            case 45:
                condition = context.getResources().getString(R.string.label_weather_code_45);
                break;
            case 46:
                condition = context.getResources().getString(R.string.label_weather_code_46);
                break;
            case 47:
                condition = context.getResources().getString(R.string.label_weather_code_47);
                break;
        }

        return condition;
    }

    public String GetCountyLabel()
    {
        return context.getResources().getString(R.string.label_explain_country);
    }

    public String GetRegionLabel()
    {
        return context.getResources().getString(R.string.label_explain_region);
    }

    public String GetCityLabel()
    {
        return context.getResources().getString(R.string.label_explain_city);
    }

    public String GetChillLabel()
    {
        return context.getResources().getString(R.string.label_explain_temp_of_wind);
    }

    public String GetDirectLabel()
    {
        return context.getResources().getString(R.string.label_explain_direction_of_wind);
    }

    public String GetSpeedOfWindLabel()
    {
        return context.getResources().getString(R.string.label_explain_speed_of_wind);
    }

    public String GetHumidityLabel()
    {
        return context.getResources().getString(R.string.label_explain_humidity);
    }

    public String GetPressureLabel()
    {
        return context.getResources().getString(R.string.label_explain_pressure);
    }

    public String GetVisibilityLabel()
    {
        return context.getResources().getString(R.string.label_explain_visibility);
    }

    public String GetKmLabel()
    {
        return context.getResources().getString(R.string.label_units_km);
    }

    public String GetMmHgLabel()
    {
        return context.getResources().getString(R.string.label_units_mm_Hg);
    }

    public String GetMetersInSecondLabel()
    {
        return context.getResources().getString(R.string.label_units_meters_in_second);
    }

    public String GetTitleLabel()
    {
        return context.getResources().getString(R.string.label_explain_title);
    }

    public String GetDateLabel()
    {
        return context.getResources().getString(R.string.label_explain_date);
    }

    public String GetDayLabel()
    {
        return context.getResources().getString(R.string.label_explain_day);
    }

    public String GetMaxTempLabel()
    {
        return context.getResources().getString(R.string.label_explain_max);
    }

    public String GetMinTempLabel()
    {
        return context.getResources().getString(R.string.label_explain_min);
    }

    public String GetConditionLabel()
    {
        return context.getResources().getString(R.string.label_explain_condition);
    }

    public String GetMessageWithError()
    {
        return context.getResources()
                .getString(R.string.message_error_error_download_weather_from_current_city);
    }
}
