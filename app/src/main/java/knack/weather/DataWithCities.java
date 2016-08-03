package knack.weather;

import java.util.Random;

public class DataWithCities
{
    String[] source = {"Кейптаун", "Сидней", "Париж", "Рио-де-Жанейро",
            "Нью-Йорк", "Рим", "Лондон", "Барселона", "Гонконг", "Киото",
            "Куинстаун", "Иерусалим", "Сиемреап", "Прага", "Венеция",
            "Буэнос-Айрес", "Гонолулу", "Санкт Петербург",
            "Флоренция", "Джорджтаун", "Сан Франциско", "Петра", "Лас Вегас"};

    Random random = new Random();

    public String GetRandomCity()
    {
        return source[random.nextInt(source.length)];
    }

    public String[] GetAllCities()
    {
        return source;
    }

    public int GetSizeOfArrayOfCities()
    {
        return source.length;
    }
}
