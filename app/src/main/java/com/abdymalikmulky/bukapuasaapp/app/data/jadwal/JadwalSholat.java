
package com.abdymalikmulky.bukapuasaapp.app.data.jadwal;

import com.abdymalikmulky.bukapuasaapp.app.data.city.City;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JadwalSholat {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("jadwal")
    @Expose
    private List<Jadwal> jadwal = null;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Jadwal> getJadwal() {
        return jadwal;
    }

    public void setJadwal(List<Jadwal> jadwal) {
        this.jadwal = jadwal;
    }

    @Override
    public String toString() {
        return "JadwalSholat{" +
                "city=" + city +
                ", jadwal=" + jadwal +
                '}';
    }
}
