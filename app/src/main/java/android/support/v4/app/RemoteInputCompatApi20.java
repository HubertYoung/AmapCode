package android.support.v4.app;

import android.app.RemoteInput;
import android.app.RemoteInput.Builder;
import android.content.Intent;
import android.os.Bundle;

class RemoteInputCompatApi20 {
    RemoteInputCompatApi20() {
    }

    static Bundle a(Intent intent) {
        return RemoteInput.getResultsFromIntent(intent);
    }

    static void a(RemoteInputCompatBase.RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) {
        RemoteInput[] remoteInputArr2;
        if (remoteInputArr == null) {
            remoteInputArr2 = null;
        } else {
            RemoteInput[] remoteInputArr3 = new RemoteInput[remoteInputArr.length];
            for (int i = 0; i < remoteInputArr.length; i++) {
                RemoteInputCompatBase.RemoteInput remoteInput = remoteInputArr[i];
                remoteInputArr3[i] = new Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build();
            }
            remoteInputArr2 = remoteInputArr3;
        }
        RemoteInput.addResultsToIntent(remoteInputArr2, intent, bundle);
    }
}
