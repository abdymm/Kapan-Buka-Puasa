package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public class CityLocal implements CityDataSource{

    private CitySp citySp;

    public CityLocal(CitySp citySp) {
        this.citySp = citySp;
    }

    @Override
    public void load(LoadCityCallback callback) {
        List<City> cities =  SQLite.select()
                .from(City.class)
                .queryList();
        if(cities.size() > 0) {
            callback.onLoaded(cities);
        }else{
            callback.onNoData();
        }
    }

    @Override
    public void getCurrentCity(GetCityCallback callback) {
        int cityId = citySp.getCityId();
        City city =  SQLite.select()
                .from(City.class)
                .where(City_Table.id.is(cityId))
                .limit(1)
                .querySingle();
        if(city != null){
            callback.onGet(city);
        }else {
            callback.onFailed("no data");
        }
    }


    public boolean isEmpty(){
        List<City> cities =  SQLite.select()
                .from(City.class)
                .queryList();
        if(cities.size() > 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean save(City city){
        return (city.insert() > 0) ? true : false;
    }

    public void delete(){
        SQLite.delete(City.class)
                .execute();
    }

    @Override
    public void setCurrentCityLocation(String cityName){
        Timber.d("DataCitySP %s", cityName);
        if(cityName.equals("")){
            cityName = citySp.getCityName();
        }else {
            citySp.setCityName(cityName);
        }
        Timber.d("DataCitySP %s", citySp.getCityName());
        City city =  SQLite.select()
                .from(City.class)
                .where(City_Table.name.like("%" + cityName + "%"))
                .querySingle();
        if(city != null) {
            citySp.setCityId(city.getId());
        }
    }

}
