package com.abdymalikmulky.bukapuasaapp.app.data.jadwal.local;

import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalDataSource;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal_Table;
import com.abdymalikmulky.bukapuasaapp.util.DateTimeUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public class JadwalLocal implements JadwalDataSource{

    @Override
    public void loadByCity(int cityId, LoadJadwalByCityCallback callback) {
        List<Jadwal> jadwalList =  SQLite.select()
                .from(Jadwal.class)
                .where(Jadwal_Table.cityId.eq(cityId))
                .orderBy(Jadwal_Table.id, true)
                .queryList();
        if(jadwalList.size() > 0) {
            callback.onLoaded(jadwalList);
        }else{
            callback.onNoData();
        }
    }

    @Override
    public void getJadwalTodayByCity(int cityId, GetJadwalTodayByCityCallback callback) {
        String dateString = DateTimeUtil.getStringNowOnlyDate();

        Where<Jadwal> queryWhere = SQLite.select()
                .from(Jadwal.class)
                .where(Jadwal_Table.cityId.eq(cityId))
                .and(Jadwal_Table.jadwalDate.eq(dateString));
        Jadwal jadwal = queryWhere.querySingle();

        if(queryWhere.count() > 0){
            callback.onGet(jadwal);
        }else {
            callback.onNoData();
        }
    }

    public boolean save(Jadwal newJadwal){
        return (newJadwal.insert() > 0) ? true : false;
    }

    public void deleteByCity(int cityId){
        SQLite.delete(Jadwal.class)
                .where(Jadwal_Table.cityId.is(cityId))
                .execute();
    }
}
