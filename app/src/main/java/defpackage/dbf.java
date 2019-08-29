package defpackage;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* renamed from: dbf reason: default package */
/* compiled from: MsgboxUtil */
public final class dbf {
    public static String a() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
        date.setTime(currentTimeMillis);
        simpleDateFormat.applyPattern("HH");
        StringBuilder sb = new StringBuilder();
        sb.append(simpleDateFormat.format(date));
        sb.append(":00");
        String sb2 = sb.toString();
        date.setTime(currentTimeMillis + 3600000);
        simpleDateFormat.applyPattern("HH");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(simpleDateFormat.format(date));
        sb3.append(":00");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("-");
        sb5.append(sb4);
        return sb5.toString();
    }

    public static List<AmapMessage> a(ArrayList<AmapMessage> arrayList, int i) {
        ArrayList arrayList2 = new ArrayList();
        if (arrayList.size() == 0) {
            return null;
        }
        int i2 = 0;
        if (i == 100 || i == 500) {
            Collections.sort(arrayList, new dbe());
            int size = arrayList.size();
            if (size > 1) {
                ArrayList arrayList3 = new ArrayList();
                for (int i3 = 1; i3 < size; i3++) {
                    arrayList3.add(arrayList.get(i3).id);
                }
                MessageBoxManager.getInstance().setMsgsShowOnMapSync((String[]) arrayList3.toArray(new String[arrayList3.size()]));
            }
            arrayList2.add(arrayList.get(0));
            return arrayList2;
        }
        Collections.sort(arrayList, new dbb());
        Iterator<AmapMessage> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AmapMessage next = it.next();
            if (next.isToastTips()) {
                if (i2 <= 0) {
                    arrayList2.add(next);
                    break;
                }
            } else if (!next.isADDisplay()) {
                i2++;
                arrayList2.add(next);
                if (i2 >= 3) {
                    break;
                }
            } else {
                continue;
            }
        }
        return arrayList2;
    }

    public static List<AmapMessage> a(ArrayList<AmapMessage> arrayList) {
        if (arrayList.size() == 0) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            AmapMessage amapMessage = arrayList.get(i);
            if (amapMessage.isEmergencyNews()) {
                arrayList2.add(amapMessage);
            }
        }
        if (arrayList2.size() == 0) {
            return null;
        }
        Collections.sort(arrayList2, new dbe());
        return arrayList2;
    }
}
