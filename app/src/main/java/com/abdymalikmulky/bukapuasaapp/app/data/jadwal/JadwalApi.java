package com.abdymalikmulky.bukapuasaapp.app.data.jadwal;

import com.abdymalikmulky.bukapuasaapp.util.EndpointUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public interface JadwalApi {

    @GET(EndpointUtil.URL_JADWAL+"{cityId}")
    public Call<JadwalSholatResponse> getAll(@Path("cityId") int cityId);


}
