package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.route.train.model.TrainTicketBaseItem;
import com.autonavi.minimap.route.train.model.TrainTicketStationInfoItem;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: ejd reason: default package */
/* compiled from: TrainInfoResponser */
public final class ejd extends AbstractAOSParser {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public List<TrainTicketStationInfoItem> h;

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = parseHeader(bArr);
        if (this.errorCode == 1 && parseHeader != null) {
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
                JSONObject jSONObject2 = jSONObject.getJSONObject("train");
                this.a = jSONObject2.getString("name");
                this.f = jSONObject2.getString("from_station");
                this.g = jSONObject2.getString("to_station");
                this.d = jSONObject2.getString("departure_time");
                this.d = a(this.d);
                this.e = jSONObject2.getString("arrival_time");
                this.e = a(this.e);
                this.b = jSONObject2.getString("distance");
                this.c = jSONObject2.getString("running_time");
                JSONArray jSONArray = jSONObject.getJSONArray("station");
                if (jSONArray != null) {
                    if (this.h == null) {
                        this.h = new ArrayList();
                    }
                    this.h.clear();
                    int length = jSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                        TrainTicketStationInfoItem trainTicketStationInfoItem = new TrainTicketStationInfoItem();
                        trainTicketStationInfoItem.name = jSONObject3.getString("name");
                        trainTicketStationInfoItem.index = jSONObject3.getString("index");
                        trainTicketStationInfoItem.arrivalTime = jSONObject3.getString("arrival_time");
                        trainTicketStationInfoItem.arrivalTime = a(trainTicketStationInfoItem.arrivalTime);
                        trainTicketStationInfoItem.departureTime = jSONObject3.getString("departure_time");
                        trainTicketStationInfoItem.departureTime = a(trainTicketStationInfoItem.departureTime);
                        trainTicketStationInfoItem.daysOnJourney = jSONObject3.getString("days");
                        trainTicketStationInfoItem.runningTime = jSONObject3.getString("running_time");
                        trainTicketStationInfoItem.distance = jSONObject3.getString("distance");
                        TrainTicketBaseItem.setTicketPrices(trainTicketStationInfoItem, jSONObject3);
                        this.h.add(trainTicketStationInfoItem);
                    }
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
                this.errorMessage = UNKNOWN_ERROR;
            }
        }
    }

    public final String getErrorDesc(int i) {
        return this.errorMessage;
    }

    private static String a(String str) {
        return !"".equals(str) ? str.substring(0, str.lastIndexOf(":")) : "";
    }
}
