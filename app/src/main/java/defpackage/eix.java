package defpackage;

import com.autonavi.minimap.route.train.model.TrainTypeItem;
import java.util.ArrayList;
import java.util.List;

/* renamed from: eix reason: default package */
/* compiled from: TrainTicketTabData */
public final class eix {
    public int a;
    public String b;
    public List<TrainTypeItem> c = new ArrayList();
    private boolean d = false;
    private int e = 0;
    private List<eix> f = new ArrayList();
    private List<eix> g = new ArrayList();

    public final int a() {
        if (this.d) {
            return this.e;
        }
        int i = 0;
        if (this.f == null || this.f.size() <= 0) {
            return 0;
        }
        int size = this.f.get(0).f != null ? this.f.get(0).f.size() : 0;
        while (true) {
            i++;
            if (i >= this.f.size()) {
                this.d = true;
                this.e = size;
                return this.e;
            } else if (this.f.get(i).f != null) {
                size = Math.max(size, this.f.get(i).f.size());
            }
        }
    }
}
