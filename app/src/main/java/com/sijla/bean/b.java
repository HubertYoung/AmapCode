package com.sijla.bean;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class b extends JSONObject {
    private List<String> a = new ArrayList();

    public JSONObject a(String str, String str2) {
        this.a.add(str2);
        return super.put(str, str2);
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        if (this.a != null) {
            for (int i = 0; i < this.a.size(); i++) {
                sb.append(this.a.get(i));
                if (i != this.a.size() - 1) {
                    sb.append("\t");
                }
            }
        }
        return sb.toString();
    }
}
