package android.support.dontuse.app;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput.Factory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RequiresApi(16)
public class RemoteInputCompatJellybean {
    public static RemoteInput[] a(Bundle[] bundleArr, Factory factory) {
        if (bundleArr == null) {
            return null;
        }
        RemoteInput[] a = factory.a(bundleArr.length);
        for (int i = 0; i < bundleArr.length; i++) {
            Bundle bundle = bundleArr[i];
            ArrayList<String> stringArrayList = bundle.getStringArrayList("allowedDataTypes");
            HashSet hashSet = new HashSet();
            if (stringArrayList != null) {
                Iterator<String> it = stringArrayList.iterator();
                while (it.hasNext()) {
                    hashSet.add(it.next());
                }
            }
            a[i] = factory.a(bundle.getString("resultKey"), bundle.getCharSequence("label"), bundle.getCharSequenceArray("choices"), bundle.getBoolean("allowFreeFormInput"), bundle.getBundle("extras"), hashSet);
        }
        return a;
    }

    public static Bundle[] a(RemoteInput[] remoteInputArr) {
        if (remoteInputArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[remoteInputArr.length];
        for (int i = 0; i < remoteInputArr.length; i++) {
            RemoteInput remoteInput = remoteInputArr[i];
            Bundle bundle = new Bundle();
            bundle.putString("resultKey", remoteInput.a());
            bundle.putCharSequence("label", remoteInput.b());
            bundle.putCharSequenceArray("choices", remoteInput.c());
            bundle.putBoolean("allowFreeFormInput", remoteInput.e());
            bundle.putBundle("extras", remoteInput.f());
            Set<String> d = remoteInput.d();
            if (d != null && !d.isEmpty()) {
                ArrayList arrayList = new ArrayList(d.size());
                for (String add : d) {
                    arrayList.add(add);
                }
                bundle.putStringArrayList("allowedDataTypes", arrayList);
            }
            bundleArr[i] = bundle;
        }
        return bundleArr;
    }
}
