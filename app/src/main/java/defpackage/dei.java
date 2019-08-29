package defpackage;

import com.amap.bundle.tripgroup.api.IAutoRemoteController.ConnectionType;
import com.autonavi.minimap.drive.auto.page.AutoBluetoothLinkManagerPage;

/* renamed from: dei reason: default package */
/* compiled from: AutoBluetoothLinkManagerModel */
public final class dei extends su<den> {
    public aga c = new aga() {
        public final void a(ConnectionType connectionType) {
            ((AutoBluetoothLinkManagerPage) ((den) dei.this.a).mPage).finish();
        }

        public final void b(ConnectionType connectionType) {
            ((AutoBluetoothLinkManagerPage) ((den) dei.this.a).mPage).a();
        }
    };

    public dei(den den) {
        super(den);
    }
}
