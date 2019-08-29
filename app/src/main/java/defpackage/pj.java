package defpackage;

import android.text.TextUtils;
import com.autonavi.map.db.model.Car;

/* renamed from: pj reason: default package */
/* compiled from: DataModel */
public final class pj {
    public Car a;
    public String b;
    public boolean c = true;
    public boolean d = true;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        pj pjVar = (pj) obj;
        if (!(this.a == null || pjVar.a == null)) {
            if (!TextUtils.equals(this.a.plateNum, pjVar.a.plateNum) || this.a.vehicleType != pjVar.a.vehicleType) {
                return false;
            }
            if (this.a.vehicleType == 2) {
                if (this.a.vehiclePowerType != pjVar.a.vehiclePowerType || !TextUtils.equals(this.a.weight, pjVar.a.weight) || !TextUtils.equals(this.a.length, pjVar.a.length) || !TextUtils.equals(this.a.width, pjVar.a.width) || !TextUtils.equals(this.a.height, pjVar.a.height) || !TextUtils.equals(this.a.capacity, pjVar.a.capacity)) {
                    return false;
                }
            } else if (this.a.vehiclePowerType != pjVar.a.vehiclePowerType) {
                return false;
            }
        }
        if (!this.b.equals(pjVar.b) || this.c != pjVar.c || this.d != pjVar.d) {
            return false;
        }
        if (this.a != null || pjVar.a == null) {
            return this.a == null || pjVar.a != null;
        }
        return false;
    }
}
