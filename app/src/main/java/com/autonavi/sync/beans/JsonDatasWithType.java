package com.autonavi.sync.beans;

import java.util.ArrayList;
import java.util.List;

public class JsonDatasWithType {
    public List<JsonDataWithId> jsonDataWithId;
    public String type;

    public JsonDatasWithType() {
        this.jsonDataWithId = new ArrayList();
    }

    public JsonDatasWithType(String str, List<JsonDataWithId> list) {
        this.type = str;
        this.jsonDataWithId = list;
        if (this.jsonDataWithId == null) {
            this.jsonDataWithId = new ArrayList();
        }
    }
}
