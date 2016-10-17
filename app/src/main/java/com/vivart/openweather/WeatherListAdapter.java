package com.vivart.openweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Vivart_Pandey on 10/17/2016.
 */

class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {
    private final ArrayList<Weather> mWeatherArrayList = new ArrayList<>();
    private Context mContext;

    void setDataSet(@NonNull final ArrayList<Weather> weatherArrayList) {
        mWeatherArrayList.clear();
        mWeatherArrayList.addAll(weatherArrayList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = mWeatherArrayList.get(position);
        //convert it to millisecond from second
        holder.mTime.setText(getDateAndTime(weather.getForecastTime() * 1000));
        holder.mDescription.setText(weather.getDescription());
        holder.mPressure.setText(mContext.getString(R.string.pressure, weather.getPressure()));
        holder.mHumidity.setText(mContext.getString(R.string.humidity, weather.getHumidity()));
        holder.mMaxTemp.setText(mContext.getString(R.string.temperature, weather.getMaxTemp()));
        holder.mMinTemp.setText(mContext.getString(R.string.temperature, weather.getMinTemp()));
    }

    private String getDateAndTime(long milliSeconds) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM hh:mm  aaa", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return format.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return mWeatherArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTime;
        TextView mDescription;
        TextView mPressure;
        TextView mHumidity;
        TextView mMaxTemp;
        TextView mMinTemp;

        ViewHolder(View v) {
            super(v);
            mTime = (TextView) v.findViewById(R.id.time);
            mDescription = (TextView) v.findViewById(R.id.description);
            mPressure = (TextView) v.findViewById(R.id.pressure);
            mHumidity = (TextView) v.findViewById(R.id.humidity);
            mMaxTemp = (TextView) v.findViewById(R.id.max_temp);
            mMinTemp = (TextView) v.findViewById(R.id.min_temp);
        }
    }
}
