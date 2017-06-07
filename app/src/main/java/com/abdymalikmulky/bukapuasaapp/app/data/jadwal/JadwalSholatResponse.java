
package com.abdymalikmulky.bukapuasaapp.app.data.jadwal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JadwalSholatResponse {

    @SerializedName("JadwalSholat")
    @Expose
    private JadwalSholat jadwalSholat;

    public JadwalSholat getJadwalSholat() {
        return jadwalSholat;
    }

    public void setJadwalSholat(JadwalSholat jadwalSholat) {
        this.jadwalSholat = jadwalSholat;
    }

    @Override
    public String toString() {
        return "JadwalSholatResponse{" +
                "jadwalSholat=" + jadwalSholat +
                '}';
    }
}
