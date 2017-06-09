package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

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
    public void get(GetCityCallback callback) {

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
        citySp.setCityName(cityName);
        List<City> cities =  SQLite.select()
                .from(City.class)
                .where(City_Table.name.like("%" + cityName + "%"))
                .limit(1)
                .queryList();
        if(cities.size() > 0) {
            citySp.setCityId(cities.get(0).getId());
        }
    }

}
