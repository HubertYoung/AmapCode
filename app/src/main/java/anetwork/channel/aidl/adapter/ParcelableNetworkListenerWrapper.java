package anetwork.channel.aidl.adapter;

import android.os.Handler;
import android.os.RemoteException;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.DefaultProgressEvent;
import anetwork.channel.aidl.ParcelableHeader;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.aidl.ParcelableNetworkListener.Stub;

public class ParcelableNetworkListenerWrapper extends Stub {
    private static final String TAG = "anet.ParcelableNetworkListenerWrapper";
    private Handler handler;
    private dh listener;
    private Object mContext;
    private byte state = 0;

    public dh getListener() {
        return this.listener;
    }

    public ParcelableNetworkListenerWrapper(dh dhVar, Handler handler2, Object obj) {
        this.listener = dhVar;
        if (dhVar != null) {
            if (a.class.isAssignableFrom(dhVar.getClass())) {
                this.state = (byte) (this.state | 1);
            }
            if (c.class.isAssignableFrom(dhVar.getClass())) {
                this.state = (byte) (this.state | 2);
            }
            if (d.class.isAssignableFrom(dhVar.getClass())) {
                this.state = (byte) (this.state | 4);
            }
            if (b.class.isAssignableFrom(dhVar.getClass())) {
                this.state = (byte) (this.state | 8);
            }
        }
        this.handler = handler2;
        this.mContext = obj;
    }

    private void dispatch(final byte b, final Object obj) {
        if (this.handler == null) {
            dispatchCallback(b, obj);
        } else {
            this.handler.post(new Runnable() {
                public final void run() {
                    ParcelableNetworkListenerWrapper.this.dispatchCallback(b, obj);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void dispatchCallback(byte b, Object obj) {
        if (b == 4) {
            try {
                ParcelableHeader parcelableHeader = (ParcelableHeader) obj;
                ((d) this.listener).onResponseCode(parcelableHeader.a, parcelableHeader.b, this.mContext);
                if (cl.a(1)) {
                    cl.a(TAG, "[onResponseCode]".concat(String.valueOf(parcelableHeader)), null, new Object[0]);
                }
            } catch (Exception unused) {
                cl.d(TAG, "dispatchCallback error", null, new Object[0]);
            }
        } else if (b == 2) {
            DefaultProgressEvent defaultProgressEvent = (DefaultProgressEvent) obj;
            if (defaultProgressEvent != null) {
                defaultProgressEvent.d = this.mContext;
            }
            if (cl.a(1)) {
                cl.a(TAG, "[onDataReceived]".concat(String.valueOf(defaultProgressEvent)), null, new Object[0]);
            }
        } else if (b == 1) {
            DefaultFinishEvent defaultFinishEvent = (DefaultFinishEvent) obj;
            if (defaultFinishEvent != null) {
                defaultFinishEvent.a = this.mContext;
            }
            ((a) this.listener).onFinished(defaultFinishEvent, this.mContext);
            if (cl.a(1)) {
                cl.a(TAG, "[onFinished]".concat(String.valueOf(defaultFinishEvent)), null, new Object[0]);
            }
        } else {
            if (b == 8) {
                ((b) this.listener).onInputStreamGet((ParcelableInputStream) obj, this.mContext);
                if (cl.a(1)) {
                    cl.a(TAG, "[onInputStreamReceived]", null, new Object[0]);
                }
            }
        }
    }

    public void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException {
        if ((this.state & 2) != 0) {
            dispatch(2, defaultProgressEvent);
        }
    }

    public void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException {
        if ((this.state & 1) != 0) {
            dispatch(1, defaultFinishEvent);
        }
        this.listener = null;
        this.mContext = null;
        this.handler = null;
    }

    public boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException {
        if ((this.state & 4) != 0) {
            dispatch(4, parcelableHeader);
        }
        return false;
    }

    public void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException {
        if ((this.state & 8) != 0) {
            dispatch(8, parcelableInputStream);
        }
    }

    public byte getListenerState() throws RemoteException {
        return this.state;
    }
}
