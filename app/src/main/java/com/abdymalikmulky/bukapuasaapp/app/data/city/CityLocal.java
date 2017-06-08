package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public class CityLocal implements CityDataSource{

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

}
