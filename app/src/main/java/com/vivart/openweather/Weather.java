package com.vivart.openweather;

/**
 * Created by Vivart_Pandey on 10/17/2016.
 */

public class Weather {
    private long mForecastTime;
    private double mTemperature;
    private double mMinTemp;
    private double mMaxTemp;
    private double mHumidity;
    private double mPressure;
    private String mDescription;

    public long getForecastTime() {
        return mForecastTime;
    }

    public void setForecastTime(long forecastTime) {
        mForecastTime = forecastTime;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getMinTemp() {
        return mMinTemp;
    }

    public void setMinTemp(double minTemp) {
        mMinTemp = minTemp;
    }

    public double getMaxTemp() {
        return mMaxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        mMaxTemp = maxTemp;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPressure() {
        return mPressure;
    }

    public void setPressure(double pressure) {
        mPressure = pressure;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    private double mWindSpeed;
}
