package com.autonavi.minimap.route.train.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class TrainTicketBaseItem implements Serializable {
    public String arrivalTime;
    public String departureTime;
    public String distance;
    public boolean isAnimShow = false;
    public boolean isShowPrice = false;
    public Map<String, String> map = new HashMap();
    public String runningTime;

    public static void setTicketPrices(TrainTicketBaseItem trainTicketBaseItem, JSONObject jSONObject) throws JSONException {
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_rw_s", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_rw_s", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_rw_x", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_yw_s", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_yw_z", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_yw_x", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_sw", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_1", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_2", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_yz", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_rz", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_t", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_gg", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_rb", jSONObject);
        putInMapIfValueValid(trainTicketBaseItem.map, "seat_dw", jSONObject);
    }

    private static void putInMapIfValueValid(Map<String, String> map2, String str, JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString(str);
        if (!"".equals(string) && Double.parseDouble(string) != 0.0d) {
            map2.put(str, string);
        }
    }
}
