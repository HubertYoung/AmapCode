package defpackage;

import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: bra reason: default package */
/* compiled from: SlidePanelManager */
public final class bra {
    private LinkedHashMap<Integer, b> a = new LinkedHashMap<>();

    /* renamed from: bra$a */
    /* compiled from: SlidePanelManager */
    public interface a {
        void onOffsetBG(int i);

        void onResetBG();
    }

    /* renamed from: bra$b */
    /* compiled from: SlidePanelManager */
    public class b {
        public WeakReference<a> a;
        public int b = 0;
        public boolean c = true;

        b() {
        }
    }

    public final b a(a aVar) {
        int hashCode = aVar.hashCode();
        if (this.a.containsKey(Integer.valueOf(hashCode))) {
            return this.a.get(Integer.valueOf(hashCode));
        }
        b bVar = new b();
        bVar.a = new WeakReference<>(aVar);
        this.a.put(Integer.valueOf(hashCode), bVar);
        return bVar;
    }

    public final void b(a aVar) {
        AtomicReference atomicReference = new AtomicReference(Integer.valueOf(0));
        a a2 = a(atomicReference);
        if (a2 != null) {
            MapBasePage.LogCQ(String.format("SildePanelManager onRefeshPanelOffset. maxOffestPanel: %s, maxOffset: %s", new Object[]{a2.getClass().getSimpleName(), atomicReference.get()}));
            a2.onOffsetBG(((Integer) atomicReference.get()).intValue());
            return;
        }
        MapBasePage.LogCQ(String.format("SildePanelManager onRefeshPanelOffset. onReset. panel: %s", new Object[]{aVar.getClass().getSimpleName()}));
        aVar.onResetBG();
    }

    private a a(AtomicReference<Integer> atomicReference) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        a aVar = null;
        for (Entry next : this.a.entrySet()) {
            int intValue = ((Integer) next.getKey()).intValue();
            b bVar = (b) next.getValue();
            if (bVar.a == null || bVar.a.get() == null) {
                arrayList.add(Integer.valueOf(intValue));
            } else if (!bVar.c && (atomicReference.get() == null || bVar.b > atomicReference.get().intValue())) {
                aVar = (a) bVar.a.get();
                atomicReference.set(Integer.valueOf(bVar.b));
            }
        }
        if (arrayList.size() > 0) {
            for (Integer intValue2 : arrayList) {
                this.a.remove(Integer.valueOf(intValue2.intValue()));
            }
        }
        return aVar;
    }
}
