package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.abdymalikmulky.bukapuasaapp.helper.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/8/17.
 */

public class CityRemote implements CityDataSource {

    CityApi api;

    public CityRemote() {
        this.api = ApiHelper.client().create(CityApi.class);
    }

    @Override
    public void load(final LoadCityCallback callback) {
        Call<CityResponse> call = api.getAll();
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.isSuccessful()) {
                    List<City> cities = response.body().getCity();
                    if(cities.size() > 0){
                        callback.onLoaded(cities);
                    }else{
                        callback.onNoData();
                    }
                }else{
                    callback.onFailed("Something Wrong");
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                callback.onFailed(t.toString());
            }
        });

    }

    @Override
    public void get(GetCityCallback callback) {

    }

}
