package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.life.api.api.ISpotGuideManager;
import com.autonavi.minimap.life.spotguide.inter.impl.SpotGuideManagerImpl;

@BundleInterface(avu.class)
/* renamed from: avz reason: default package */
/* compiled from: LifeServiceImpl */
public class avz implements avu {
    public final avt a() {
        return new avy();
    }

    public final ISpotGuideManager b() {
        return new SpotGuideManagerImpl();
    }
}
