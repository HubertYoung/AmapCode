package defpackage;

import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: eju reason: default package */
/* compiled from: TrainPlanItemsFilter */
public final class eju {
    public static ArrayList<TrainPlanBaseInfoItem> a(ArrayList<TrainPlanBaseInfoItem> arrayList) {
        ArrayList<TrainPlanBaseInfoItem> arrayList2 = new ArrayList<>();
        if (arrayList == null || arrayList.size() == 0) {
            return arrayList2;
        }
        Iterator<TrainPlanBaseInfoItem> it = arrayList.iterator();
        while (it.hasNext()) {
            TrainPlanBaseInfoItem next = it.next();
            if (next.isContainsTicketTypeForStudent()) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }

    public static ArrayList<TrainPlanBaseInfoItem> a(ArrayList<TrainPlanBaseInfoItem> arrayList, eiv eiv) {
        ArrayList<TrainPlanBaseInfoItem> arrayList2 = new ArrayList<>();
        if (arrayList == null || arrayList.size() == 0 || eiv == null) {
            return arrayList2;
        }
        Iterator<TrainPlanBaseInfoItem> it = arrayList.iterator();
        while (it.hasNext()) {
            TrainPlanBaseInfoItem next = it.next();
            if (eiv.a(next.trip.substring(0, 1)) && eiv.b(next.trainDepartureTime) && eiv.c(next.getFormatedArrivalTime())) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }
}
