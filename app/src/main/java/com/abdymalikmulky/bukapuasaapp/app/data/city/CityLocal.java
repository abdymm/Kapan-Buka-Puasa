package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.raizlabs.android.dbflow.sql.language.From;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

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
        }
        String[] cityNameSplit = cityName.split(" ");
        Timber.d("DataCitySP %s", citySp.getCityName());
        From<City> fromQUery =  SQLite.select().from(City.class);
        Where<City> whereQuery = fromQUery.where(City_Table.name.like("%" + cityName + "%"));

        for (int i = 0; i < cityNameSplit.length; i++){
            whereQuery.or(City_Table.name.like("%" + cityNameSplit[i] + "%"));
        }
        City city = whereQuery.querySingle();
        if(city != null) {
            Timber.d("DataCitySP %s", city.toString());
            citySp.setCityName(cityName);
            citySp.setCityId(city.getId());
        }
    }

}
