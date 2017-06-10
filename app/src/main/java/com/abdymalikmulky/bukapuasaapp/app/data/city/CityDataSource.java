package com.abdymalikmulky.bukapuasaapp.app.data.city;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public interface CityDataSource {

    interface LoadCityCallback {
        void onLoaded(List<City> cities);
        void onNoData();
        void onFailed(String msg);
    }

    interface GetCityCallback {
        void onGet(City city);
        void onFailed(String msg);
    }



    void load(LoadCityCallback callback);

    //TODO: Get city by locationmanager
    void getCurrentCity(GetCityCallback callback);

    void setCurrentCityLocation(String cityName);
}
