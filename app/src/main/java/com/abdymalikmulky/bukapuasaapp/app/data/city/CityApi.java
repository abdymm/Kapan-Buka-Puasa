package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.abdymalikmulky.bukapuasaapp.util.EndpointUtil;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public interface CityApi {

    @GET(EndpointUtil.URL_CITY)
    public Call<CityResponse> getAll();


}
