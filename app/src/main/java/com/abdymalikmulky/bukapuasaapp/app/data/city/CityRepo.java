package com.abdymalikmulky.bukapuasaapp.app.data.city;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/8/17.
 */

public class CityRepo implements CityDataSource {

    private CityLocal cityLocal;
    private CityRemote cityRemote;

    public CityRepo(CityLocal cityLocal, CityRemote cityRemote) {
        this.cityLocal = cityLocal;
        this.cityRemote = cityRemote;
    }

    @Override
    public void load(final LoadCityCallback callback) {
        if(cityLocal.isEmpty()){
            cityRemote.load(new LoadCityCallback() {
                @Override
                public void onLoaded(List<City> cities) {
                    if(storeCity(cities)){
                        callback.onLoaded(cities);
                    }else{
                        callback.onFailed("Something Wrong");
                    }
                }

                @Override
                public void onNoData() {
                    callback.onNoData();
                }

                @Override
                public void onFailed(String msg) {
                    callback.onFailed(msg);
                }
            });
        }
    }

    @Override
    public void get(GetCityCallback callback) {

    }

    private boolean storeCity(List<City> cities){
        int successCount = 0;
        cityLocal.delete();
        for (City city: cities) {
            if(cityLocal.save(city)){
                successCount++;
            }
        }
        return successCount==cities.size() ? true : false;
    }
}
