package defpackage;

import com.autonavi.minimap.route.train.model.IOrderSearchResult;
import com.autonavi.minimap.route.train.model.TrainOrderResultData;

/* renamed from: eja reason: default package */
/* compiled from: AosTrainOrderListResponser */
public final class eja extends eiy {
    public final String b() {
        return "DATA_CENTER_TRAIN_ORDER";
    }

    public eja(int i) {
        super(i);
    }

    /* access modifiers changed from: protected */
    public final IOrderSearchResult a() {
        return new TrainOrderResultData("DATA_CENTER_TRAIN_ORDER");
    }
}
