package com.abdymalikmulky.bukapuasaapp.app.data.jadwal.remote;

import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalApi;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalDataSource;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalSholatResponse;
import com.abdymalikmulky.bukapuasaapp.helper.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public class JadwalRemote implements JadwalDataSource {

    JadwalApi api;

    public JadwalRemote() {
        this.api = ApiHelper.client().create(JadwalApi.class);
    }

    @Override
    public void loadByCity(int cityId, final LoadJadwalByCityCallback callback) {

        Call<JadwalSholatResponse> call = api.getAll(cityId);
        call.enqueue(new Callback<JadwalSholatResponse>() {
            @Override
            public void onResponse(Call<JadwalSholatResponse> call, Response<JadwalSholatResponse> response) {
                if(response.isSuccessful()) {
                    List<Jadwal> jadwalList = response.body().getJadwalSholat().getJadwal();
                    if(jadwalList.size() > 0){
                        callback.onLoaded(jadwalList);
                    }else{
                        callback.onNoData();
                    }
                }else{
                    callback.onFailed("Something Wrong");
                }
            }

            @Override
            public void onFailure(Call<JadwalSholatResponse> call, Throwable t) {
                Timber.d("LoadDataJadwal %s", t.toString());

                callback.onFailed(t.toString());
            }
        });
    }

    @Override
    public void getJadwalTodayByCity(int cityId, GetJadwalTodayByCityCallback callback) {

    }
}
