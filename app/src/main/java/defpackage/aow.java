package defpackage;

import com.autonavi.minimap.account.deactivate.model.DeactivateResponse;

/* renamed from: aow reason: default package */
/* compiled from: DeactivateCallback */
public class aow implements dko<DeactivateResponse> {
    public void a(Exception exc) {
    }

    public void a(DeactivateResponse deactivateResponse) {
        if (deactivateResponse != null) {
            if (deactivateResponse.code == 1 || deactivateResponse.result || deactivateResponse.code == 14 || deactivateResponse.code == 24 || deactivateResponse.code == 32) {
                aoy.a();
            }
        }
    }
}
