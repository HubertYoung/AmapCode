package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.minimap.bundle.agroup.api.IDataService.a;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: cjh reason: default package */
/* compiled from: AgroupDataWrapper */
public final class cjh implements a {
    final ArrayList<MemberInfo> a = new ArrayList<>();
    final ArrayList<MemberInfo> b = new ArrayList<>();
    cje c;
    boolean d;
    public boolean e = false;
    private Runnable f = new Runnable() {
        public final void run() {
            if (cjh.this.c.d()) {
                cjh cjh = cjh.this;
                cjt a2 = cjt.a();
                if (a2 != null) {
                    List<MemberInfo> f = a2.f();
                    ArrayList arrayList = new ArrayList();
                    int size = f.size();
                    for (int i = 0; i < size; i++) {
                        MemberInfo memberInfo = f.get(i);
                        if (memberInfo != null) {
                            if (memberInfo.isMyself()) {
                                if (cjh.e) {
                                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                                    memberInfo.lon = latestPosition.getLongitude();
                                    memberInfo.lat = latestPosition.getLatitude();
                                }
                            }
                            if (!(memberInfo.lon == 0.0d || memberInfo.lat == 0.0d)) {
                                if (TextUtils.isEmpty(memberInfo.nickname)) {
                                    memberInfo.nickname = "匿名";
                                }
                                memberInfo.setIndex(arrayList.size());
                                arrayList.add(memberInfo);
                            }
                        }
                    }
                    synchronized (cjh.b) {
                        cjh.b.clear();
                        if (arrayList.size() > 0) {
                            cjh.b.addAll(arrayList);
                        }
                    }
                    synchronized (cjh.a) {
                        cjh.a.clear();
                        if (arrayList.size() > 0) {
                            cjh.a.addAll(arrayList);
                        }
                        cjh.c.a(cjh.a);
                    }
                }
            }
        }
    };

    public final void onSuperGroupInfoChanged() {
    }

    public final void onTeamInfoChanged() {
    }

    public cjh(cje cje) {
        this.c = cje;
    }

    public final void a() {
        if (this.c.d()) {
            ahm.b(this.f);
            ahm.a(this.f, 30);
        }
    }

    public final ArrayList<GeoPoint> b() {
        ArrayList<GeoPoint> arrayList = new ArrayList<>();
        synchronized (this.b) {
            try {
                if (this.b.size() == 0) {
                    cjt a2 = cjt.a();
                    if (a2 != null) {
                        List<MemberInfo> f2 = a2.f();
                        int size = f2.size();
                        for (int i = 0; i < size; i++) {
                            MemberInfo memberInfo = f2.get(i);
                            if (!(memberInfo == null || memberInfo.lon == 0.0d || memberInfo.lat == 0.0d)) {
                                arrayList.add(new GeoPoint(memberInfo.lon, memberInfo.lat));
                            }
                        }
                    }
                } else {
                    Iterator<MemberInfo> it = this.b.iterator();
                    while (it.hasNext()) {
                        MemberInfo next = it.next();
                        arrayList.add(new GeoPoint(next.lon, next.lat));
                    }
                }
            }
        }
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            arrayList.add(latestPosition.clone());
        }
        return arrayList;
    }

    public final MemberInfo a(String str) {
        MemberInfo memberInfo = null;
        if (str == null) {
            return null;
        }
        synchronized (this.b) {
            try {
                Iterator<MemberInfo> it = this.b.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MemberInfo next = it.next();
                    if (str.equals(next.uid)) {
                        memberInfo = next;
                        break;
                    }
                }
            }
        }
        return memberInfo;
    }

    public final void onMemberBaseInfoChanged() {
        a();
    }

    public final void onMemberLocationInfoChanged() {
        a();
    }

    public final void onTeamStatusChanged(TeamStatus teamStatus) {
        if (teamStatus == TeamStatus.STATUS_TEAM_DISMISS || teamStatus == TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM || teamStatus == TeamStatus.STATUS_NONE) {
            this.d = true;
            synchronized (this.a) {
                this.a.clear();
            }
            synchronized (this.b) {
                this.b.clear();
            }
        }
        a();
    }
}
