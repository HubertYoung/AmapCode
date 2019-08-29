package mtopsdk.xstate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.xstate.aidl.IXState.Stub;

public class XStateService extends Service {
    Stub a = null;
    Object b = new Object();

    class XStateStub extends Stub {
        public XStateStub() {
        }

        public String removeKey(String str) throws RemoteException {
            return fgz.b(str);
        }

        public void setValue(String str, String str2) throws RemoteException {
            fgz.a(str, str2);
        }

        public void init() throws RemoteException {
            fgz.a(XStateService.this.getBaseContext());
        }

        public void unInit() throws RemoteException {
            fgz.a();
        }

        public String getValue(String str) throws RemoteException {
            return fgz.a(str);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public IBinder onBind(Intent intent) {
        synchronized (this.b) {
            if (this.a == null) {
                this.a = new XStateStub();
                try {
                    this.a.init();
                } catch (RemoteException e) {
                    TBSdkLog.b((String) "mtopsdk.XStateService", (String) "[onBind]init() exception", (Throwable) e);
                } catch (Throwable th) {
                    TBSdkLog.b((String) "mtopsdk.XStateService", (String) "[onBind]init() error", th);
                }
            }
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder("[onBind] XStateService  stub= ");
            sb.append(this.a.hashCode());
            TBSdkLog.b("mtopsdk.XStateService", sb.toString());
        }
        return this.a;
    }

    public void onDestroy() {
        super.onDestroy();
        synchronized (this.b) {
            if (this.a != null) {
                try {
                    this.a.unInit();
                } catch (RemoteException e) {
                    TBSdkLog.b((String) "mtopsdk.XStateService", (String) "[onDestroy]unInit() exception", (Throwable) e);
                } catch (Throwable th) {
                    TBSdkLog.b((String) "mtopsdk.XStateService", (String) "[onDestroy]unInit() error", th);
                }
            }
        }
    }
}
