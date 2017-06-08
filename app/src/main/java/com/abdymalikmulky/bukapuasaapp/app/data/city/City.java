
package com.abdymalikmulky.bukapuasaapp.app.data.city;

import com.abdymalikmulky.bukapuasaapp.app.data.DatabaseConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DatabaseConfig.class)
public class City extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = false)
    @SerializedName("id")
    @Expose
    private String id;

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
