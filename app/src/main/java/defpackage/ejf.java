package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.route.train.model.TrainTicketBaseItem;
import com.autonavi.minimap.route.train.model.TrainTicketGeneralInfoItem;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: ejf reason: default package */
/* compiled from: TrainTicketListResponser */
public final class ejf extends AbstractAOSParser {
    public List<TrainTicketGeneralInfoItem> a;

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = parseHeader(bArr);
        if (this.errorCode == 1 && parseHeader != null) {
            try {
                JSONArray jSONArray = new JSONObject(new String(bArr, "UTF-8")).getJSONArray("train");
                if (jSONArray != null) {
                    if (this.a == null) {
                        this.a = new ArrayList();
                    }
                    this.a.clear();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        TrainTicketGeneralInfoItem trainTicketGeneralInfoItem = new TrainTicketGeneralInfoItem();
                        trainTicketGeneralInfoItem.trainName = jSONObject.getString("name");
                        if (trainTicketGeneralInfoItem.trainName.contains("/") && jSONObject.has("real_train_num")) {
                            String string = jSONObject.getString("real_train_num");
                            if (!TextUtils.isEmpty(string)) {
                                trainTicketGeneralInfoItem.trainName = string;
                            }
                        }
                        trainTicketGeneralInfoItem.departureTime = jSONObject.getString("departure_time");
                        trainTicketGeneralInfoItem.departureTime = a(trainTicketGeneralInfoItem.departureTime);
                        trainTicketGeneralInfoItem.arrivalTime = jSONObject.getString("arrival_time");
                        trainTicketGeneralInfoItem.arrivalTime = a(trainTicketGeneralInfoItem.arrivalTime);
                        trainTicketGeneralInfoItem.distance = jSONObject.getString("distance");
                        trainTicketGeneralInfoItem.runningTime = jSONObject.getString("running_time");
                        trainTicketGeneralInfoItem.departure = jSONObject.getString("from_station");
                        trainTicketGeneralInfoItem.destination = jSONObject.getString("to_station");
                        TrainTicketBaseItem.setTicketPrices(trainTicketGeneralInfoItem, jSONObject);
                        this.a.add(trainTicketGeneralInfoItem);
                    }
                }
            } catch (Exception unused) {
                this.errorMessage = UNKNOWN_ERROR;
            }
        }
    }

    private static String a(String str) {
        return (!TextUtils.isEmpty(str) && str.length() > 5 && str.endsWith(":00")) ? str.substring(0, str.length() - 3) : str;
    }

    public final String getErrorDesc(int i) {
        if (this.errorMessage == null || this.errorMessage.equals("")) {
            return UNKNOWN_ERROR;
        }
        return this.errorMessage;
    }
}
