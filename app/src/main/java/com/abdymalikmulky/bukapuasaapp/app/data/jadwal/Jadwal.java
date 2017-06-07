
package com.abdymalikmulky.bukapuasaapp.app.data.jadwal;

import com.abdymalikmulky.bukapuasaapp.app.data.DatabaseConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = DatabaseConfig.class)
public class Jadwal extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = true)
    @SerializedName("id")
    @Expose
    private long id;

    @Column
    @SerializedName("city_id")
    @Expose
    private int cityId;

    @Column
    @SerializedName("jadwal_date")
    @Expose
    private String jadwalDate;

    @Column
    @SerializedName("imsyak")
    @Expose
    private String imsyak;

    @Column
    @SerializedName("shubuh")
    @Expose
    private String shubuh;

    @Column
    @SerializedName("fajar")
    @Expose
    private String fajar;

    @Column
    @SerializedName("dhuha")
    @Expose
    private String dhuha;

    @Column
    @SerializedName("dzuhur")
    @Expose
    private String dzuhur;

    @Column
    @SerializedName("ashr")
    @Expose
    private String ashr;

    @Column
    @SerializedName("maghrib")
    @Expose
    private String maghrib;

    @Column
    @SerializedName("isya")
    @Expose
    private String isya;

    @Column
    @SerializedName("created_at")
    @Expose
    private Date createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getJadwalDate() {
        return jadwalDate;
    }

    public void setJadwalDate(String jadwalDate) {
        this.jadwalDate = jadwalDate;
    }

    public String getImsyak() {
        return imsyak;
    }

    public void setImsyak(String imsyak) {
        this.imsyak = imsyak;
    }

    public String getShubuh() {
        return shubuh;
    }

    public void setShubuh(String shubuh) {
        this.shubuh = shubuh;
    }

    public String getFajar() {
        return fajar;
    }

    public void setFajar(String fajar) {
        this.fajar = fajar;
    }

    public String getDhuha() {
        return dhuha;
    }

    public void setDhuha(String dhuha) {
        this.dhuha = dhuha;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public void setDzuhur(String dzuhur) {
        this.dzuhur = dzuhur;
    }

    public String getAshr() {
        return ashr;
    }

    public void setAshr(String ashr) {
        this.ashr = ashr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Jadwal{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", jadwalDate='" + jadwalDate + '\'' +
                ", imsyak='" + imsyak + '\'' +
                ", shubuh='" + shubuh + '\'' +
                ", fajar='" + fajar + '\'' +
                ", dhuha='" + dhuha + '\'' +
                ", dzuhur='" + dzuhur + '\'' +
                ", ashr='" + ashr + '\'' +
                ", maghrib='" + maghrib + '\'' +
                ", isya='" + isya + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
