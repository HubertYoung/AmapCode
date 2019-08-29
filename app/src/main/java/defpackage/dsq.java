package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.minimap.onekeycheck.action.ActionListener;
import com.autonavi.minimap.onekeycheck.action.BaseAction;
import com.autonavi.minimap.onekeycheck.module.PackData;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dsq reason: default package */
/* compiled from: UploadDatasAction */
public final class dsq extends BaseAction {
    public long d = System.currentTimeMillis();
    public List<AosRequest> e = new ArrayList();
    private yq f = yq.a();
    private UploadDataResult g;
    private int h;

    public dsq(ActionListener actionListener) {
        super(actionListener);
    }

    public final void start() {
        super.start();
        this.g = new UploadDataResult();
        this.a.update(3);
    }

    public static String a(PackData packData) {
        return packData != null ? packData.getPackRootNode().toJSONString() : "";
    }

    public final void stop() {
        if (this.f != null && !dsx.a(this.e)) {
            for (AosRequest a : this.e) {
                yq.a(a);
            }
        }
    }

    public static /* synthetic */ void a(dsq dsq, boolean z, boolean z2) {
        UploadDataResult uploadDataResult = dsq.g;
        int i = dsq.h;
        dsq.h = i + 1;
        uploadDataResult.addPackageState(i, z ? "success" : UploadDataResult.FAIL_MSG);
        if (z2) {
            dsq.finish();
            dsq.cancelWaitTimer();
            dsq.callbackOnResponse(dsq.g);
        }
    }
}
