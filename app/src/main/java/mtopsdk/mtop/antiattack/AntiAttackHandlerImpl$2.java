package mtopsdk.mtop.antiattack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import mtopsdk.common.util.TBSdkLog;

public class AntiAttackHandlerImpl$2 extends BroadcastReceiver {
    final /* synthetic */ fef a;

    public AntiAttackHandlerImpl$2(fef fef) {
        this.a = fef;
    }

    public void onReceive(Context context, Intent intent) {
        try {
            TBSdkLog.b("mtopsdk.AntiAttackHandlerImpl", "[onReceive]AntiAttack result: ".concat(String.valueOf(intent.getStringExtra("Result"))));
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.a.b.removeCallbacks(this.a.d);
            this.a.a.set(false);
            throw th;
        }
        this.a.b.removeCallbacks(this.a.d);
        this.a.a.set(false);
    }
}
