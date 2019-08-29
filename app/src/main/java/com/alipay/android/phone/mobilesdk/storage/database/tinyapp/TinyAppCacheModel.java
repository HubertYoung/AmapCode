package com.alipay.android.phone.mobilesdk.storage.database.tinyapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tinyapp")
public class TinyAppCacheModel {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "key")
    public String key;
    @DatabaseField(columnName = "value")
    public String value;

    public TinyAppCacheModel() {
    }

    public TinyAppCacheModel(String key2, String value2) {
        this.key = key2;
        this.value = value2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }
}
