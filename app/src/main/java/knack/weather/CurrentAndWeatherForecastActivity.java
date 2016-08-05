package knack.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CurrentAndWeatherForecastActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String myJsonString;

    private int[] tabIcons = {
            R.mipmap.ic_cloud_black_24dp,
            R.mipmap.ic_brightness_3_black_24dp,
    };

    CurrentWeatherFragment currentWeatherFragment;
    WeatherOnAnotherDaysFragment weatherOnAnotherDaysFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_and_weather_forecast);

        Intent intent = getIntent();
        myJsonString = intent.getStringExtra("jsonString");

        // Передаём данные в 1 фрагмент
        currentWeatherFragment = new CurrentWeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("jsonString", myJsonString);
        currentWeatherFragment.setArguments(bundle);

        // Во 2
        weatherOnAnotherDaysFragment = new WeatherOnAnotherDaysFragment();
        weatherOnAnotherDaysFragment.setArguments(bundle);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(currentWeatherFragment, "Current weather");
        adapter.addFragment(weatherOnAnotherDaysFragment, "Weather forecast");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }
}
