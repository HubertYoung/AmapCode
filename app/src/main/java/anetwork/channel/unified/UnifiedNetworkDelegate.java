package anetwork.channel.unified;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.NetworkResponse;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.aidl.RemoteNetwork.Stub;
import anetwork.channel.aidl.adapter.ConnectionDelegate;
import anetwork.channel.aidl.adapter.ParcelableFutureResponse;
import anetwork.channel.aidl.adapter.ParcelableNetworkListenerWrapper;
import anetwork.channel.http.NetworkSdkSetting;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.io.ByteArrayOutputStream;

public abstract class UnifiedNetworkDelegate extends Stub {
    public static final int DEGRADABLE = 1;
    public static final int HTTP = 0;
    private static final String TAG = "anet.UnifiedNetworkDelegate";
    protected int type = 1;

    protected UnifiedNetworkDelegate(Context context) {
        NetworkSdkSetting.init(context);
    }

    public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
        return convertToSync(parcelableRequest);
    }

    public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
        try {
            return asyncSend(new dy(parcelableRequest, this.type, false), parcelableNetworkListener);
        } catch (Exception e) {
            cl.e(TAG, "asyncSend failed", parcelableRequest.m, new Object[0]);
            throw new RemoteException(e.getMessage());
        }
    }

    private ParcelableFuture asyncSend(dy dyVar, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
        return new ParcelableFutureResponse(new en(dyVar, new dx(parcelableNetworkListener, dyVar)).a());
    }

    public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
        try {
            dy dyVar = new dy(parcelableRequest, this.type, true);
            ConnectionDelegate connectionDelegate = new ConnectionDelegate(dyVar);
            connectionDelegate.setFuture(asyncSend(dyVar, (ParcelableNetworkListener) new ParcelableNetworkListenerWrapper(connectionDelegate, null, null)));
            return connectionDelegate;
        } catch (Exception e) {
            cl.e(TAG, "asyncSend failed", parcelableRequest.m, new Object[0]);
            throw new RemoteException(e.getMessage());
        }
    }

    private NetworkResponse convertToSync(ParcelableRequest parcelableRequest) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            ConnectionDelegate connectionDelegate = (ConnectionDelegate) getConnection(parcelableRequest);
            ParcelableInputStream inputStream = connectionDelegate.getInputStream();
            if (inputStream != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.length() > 0 ? inputStream.length() : 1024);
                aa a = a.a.a(2048);
                while (true) {
                    int read = inputStream.read(a.a);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(a.a, 0, read);
                }
                networkResponse.c = byteArrayOutputStream.toByteArray();
            }
            int statusCode = connectionDelegate.getStatusCode();
            if (statusCode < 0) {
                networkResponse.c = null;
            } else {
                networkResponse.d = connectionDelegate.getConnHeadFields();
            }
            networkResponse.a(statusCode);
            networkResponse.e = connectionDelegate.getStatisticData();
            return networkResponse;
        } catch (RemoteException e) {
            networkResponse.a(-103);
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message)) {
                networkResponse.b = cz.a(networkResponse.b, MergeUtil.SEPARATOR_KV, message);
            }
            return networkResponse;
        } catch (Exception unused) {
            networkResponse.a(-201);
            return networkResponse;
        }
    }
}
