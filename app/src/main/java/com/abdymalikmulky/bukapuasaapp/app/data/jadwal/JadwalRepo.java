package com.abdymalikmulky.bukapuasaapp.app.data.jadwal;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public class JadwalRepo implements JadwalDataSource {

    private JadwalLocal jadwalLocal;
    private JadwalRemote jadwalRemote;

    public JadwalRepo(JadwalLocal jadwalLocal, JadwalRemote jadwalRemote) {
        this.jadwalLocal = jadwalLocal;
        this.jadwalRemote = jadwalRemote;
    }



    @Override
    public void loadByCity(final int cityId, final LoadJadwalByCityCallback callback) {
        jadwalRemote.loadByCity(cityId, new LoadJadwalByCityCallback() {
            @Override
            public void onLoaded(List<Jadwal> jadwalList) {
                if(storeJadwal(cityId, jadwalList)){
                    jadwalLocal.loadByCity(cityId, new LoadJadwalByCityCallback() {
                        @Override
                        public void onLoaded(List<Jadwal> jadwalList) {
                            callback.onLoaded(jadwalList);
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
                }else{
                    callback.onFailed("Something Wrong yeah");
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

    @Override
    public void getJadwalTodayByCity(int cityId, GetJadwalTodayByCityCallback callback) {
        jadwalLocal.getJadwalTodayByCity(cityId, callback);
    }

    private boolean storeJadwal(int cityId, List<Jadwal> jadwalList){
        int successCount = 0;
        jadwalLocal.deleteByCity(cityId);
        for (Jadwal jadwal : jadwalList) {
            if(jadwalLocal.save(jadwal)){
                successCount++;
            }
        }
        return successCount==jadwalList.size() ? true : false;
    }



}
