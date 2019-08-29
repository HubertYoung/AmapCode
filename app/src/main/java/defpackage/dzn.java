package defpackage;

import com.autonavi.minimap.route.coach.model.CoachOrderResultData;
import com.autonavi.minimap.route.train.model.IOrderSearchResult;

/* renamed from: dzn reason: default package */
/* compiled from: AosCoachOrderListResponser */
public final class dzn extends eiy {
    public final String b() {
        return "DATA_CENTER_COACH_ORDER";
    }

    public dzn(int i) {
        super(i);
    }

    public final IOrderSearchResult a() {
        return new CoachOrderResultData("DATA_CENTER_COACH_ORDER");
    }
}
