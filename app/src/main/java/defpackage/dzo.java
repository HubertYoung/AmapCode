package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.route.coach.model.CoachPlanData;
import com.autonavi.minimap.route.coach.model.CoachPlanItem;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: dzo reason: default package */
/* compiled from: AosCoachPlanResponsor */
public final class dzo extends AbstractAOSParser {
    public int a = -1;
    public CoachPlanData b = new CoachPlanData();

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
        if (this.errorCode == 1) {
            try {
                JSONObject optJSONObject = new JSONObject(new String(bArr, "UTF-8")).optJSONObject("data");
                this.a = optJSONObject.optInt("why", -1);
                if (this.a == 62) {
                    this.errorMessage = optJSONObject.optString("errMsg");
                }
                this.b.bsid = optJSONObject.optString("bsid");
                this.b.depNameList = optJSONObject.optString("depNameList");
                this.b.arrNameList = optJSONObject.optString("arrNameList");
                this.b.isEnlargeCity = optJSONObject.optString("isEnlargeCity");
                this.b.hasShiftType = optJSONObject.optString("hasShiftType");
                this.b.service_switch = optJSONObject.optInt("service_switch", 0);
                this.b.ticketshow = optJSONObject.optInt("ticketshow", 0);
                JSONArray optJSONArray = optJSONObject.optJSONArray("coachlist");
                this.b.planItems = a(optJSONArray);
            } catch (Exception unused) {
            }
        }
    }

    private static ArrayList<CoachPlanItem> a(JSONArray jSONArray) {
        ArrayList<CoachPlanItem> arrayList = new ArrayList<>();
        if (jSONArray == null || jSONArray.length() == 0) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            CoachPlanItem coachPlanItem = null;
            if (optJSONObject != null) {
                CoachPlanItem coachPlanItem2 = new CoachPlanItem();
                coachPlanItem2.cost = optJSONObject.optDouble("cost");
                JSONArray optJSONArray = optJSONObject.optJSONArray("segments");
                if (!(optJSONArray == null || optJSONArray.length() == 0)) {
                    JSONArray optJSONArray2 = optJSONArray.optJSONArray(0);
                    if (!(optJSONArray2 == null || optJSONArray2.length() == 0)) {
                        JSONObject optJSONObject2 = optJSONArray2.optJSONObject(1);
                        coachPlanItem2.busNumber = optJSONObject2.optString("busNumber");
                        coachPlanItem2.scheduleId = optJSONObject2.optString("scheduleId");
                        coachPlanItem2.fullPrice = optJSONObject2.optDouble("fullPrice", 0.0d);
                        coachPlanItem2.depName = optJSONObject2.optString("depName");
                        coachPlanItem2.depCity = optJSONObject2.optString("depCity");
                        coachPlanItem2.arrName = optJSONObject2.optString("arrName");
                        coachPlanItem2.arrCity = optJSONObject2.optString("arrCity");
                        coachPlanItem2.departTime = optJSONObject2.optString("departTime");
                        coachPlanItem2.lastDepartTime = optJSONObject2.optString("lastDepartTime");
                        coachPlanItem2.shiftType = optJSONObject2.optInt("shiftType");
                        coachPlanItem2.dateSource = optJSONObject2.optInt("dateSource");
                        coachPlanItem2.stock = optJSONObject2.optInt("stock", -1);
                        coachPlanItem = coachPlanItem2;
                    }
                }
            }
            if (coachPlanItem != null) {
                arrayList.add(coachPlanItem);
            }
        }
        return arrayList;
    }
}
