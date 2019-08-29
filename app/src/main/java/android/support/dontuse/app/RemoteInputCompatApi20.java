package android.support.dontuse.app;

import android.app.RemoteInput.Builder;
import android.support.annotation.RequiresApi;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput.Factory;

@RequiresApi(20)
public class RemoteInputCompatApi20 {
    public static RemoteInput[] a(android.app.RemoteInput[] remoteInputArr, Factory factory) {
        if (remoteInputArr == null) {
            return null;
        }
        RemoteInput[] a = factory.a(remoteInputArr.length);
        for (int i = 0; i < remoteInputArr.length; i++) {
            android.app.RemoteInput remoteInput = remoteInputArr[i];
            a[i] = factory.a(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null);
        }
        return a;
    }

    public static android.app.RemoteInput[] a(RemoteInput[] remoteInputArr) {
        if (remoteInputArr == null) {
            return null;
        }
        android.app.RemoteInput[] remoteInputArr2 = new android.app.RemoteInput[remoteInputArr.length];
        for (int i = 0; i < remoteInputArr.length; i++) {
            RemoteInput remoteInput = remoteInputArr[i];
            remoteInputArr2[i] = new Builder(remoteInput.a()).setLabel(remoteInput.b()).setChoices(remoteInput.c()).setAllowFreeFormInput(remoteInput.e()).addExtras(remoteInput.f()).build();
        }
        return remoteInputArr2;
    }
}
