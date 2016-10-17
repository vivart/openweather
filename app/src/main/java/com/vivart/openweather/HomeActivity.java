package com.vivart.openweather;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private WeatherListAdapter mAdapter;
    private FetchWeatherTask mFetchWeatherTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.weather_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new WeatherListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFetchWeatherTask == null) {
            mFetchWeatherTask = new FetchWeatherTask();
        }
        mFetchWeatherTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //user is going away from this activity
        //so cancel weather task
        if (mFetchWeatherTask != null) {
            mFetchWeatherTask.cancel(true);
            mFetchWeatherTask = null;
        }
    }

    private class FetchWeatherTask extends AsyncTask<Void, Void, ArrayList<Weather>> {

        @Override
        protected ArrayList<Weather> doInBackground(Void... params) {
            ArrayList<Weather> weatherArrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(getWeatherData());
                JSONArray list = jsonObject.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    if (isCancelled()){
                        //task is cancelled so no need to do anything further
                        break;
                    }
                    Weather weather = new Weather();
                    JSONObject listJSONObject = list.getJSONObject(i);
                    weather.setForecastTime(listJSONObject.getLong("dt"));
                    JSONObject mainObject = listJSONObject.getJSONObject("main");
                    weather.setTemperature(mainObject.getDouble("temp"));
                    weather.setMinTemp(mainObject.getDouble("temp_min"));
                    weather.setMaxTemp(mainObject.getDouble("temp_max"));
                    weather.setPressure(mainObject.getDouble("pressure"));
                    weather.setHumidity(mainObject.getDouble("humidity"));

                    JSONObject jsonWeather = listJSONObject.getJSONArray("weather").getJSONObject(0);
                    weather.setDescription(jsonWeather.getString("description"));
                    weatherArrayList.add(weather);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weatherArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Weather> s) {
            super.onPostExecute(s);
            //update list with updated data
            mAdapter.setDataSet(s);
        }
    }
    @Nullable
    private String getWeatherData() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJson = null;

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Noida,IN&mode=json&units=metric&appid=8f36f8b778630b07fb1e268f9dcf86a2");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            forecastJson = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return forecastJson;
    }
}
