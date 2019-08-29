package com.autonavi.minimap.bundle.msgbox.network;

import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.a;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public class MessageBoxCallback extends FalconAosPrepareResponseCallback<dar> {
    public final HashSet<das> a = new HashSet<>();
    private final ReentrantLock b = new ReentrantLock();

    public final /* bridge */ /* synthetic */ void a(Object obj) {
    }

    public MessageBoxCallback(Set<das> set) {
        if (this.a != null && set != null) {
            this.a.addAll(set);
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        MessageBoxManager.getInstance().retrieveLocalMessages(this.a, true);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public dar a(AosByteResponse aosByteResponse) {
        dar dar = new dar();
        try {
            dar.parser((byte[]) aosByteResponse.getResult());
            AMapAppGlobal.getApplication().getApplicationContext();
            dbc b2 = dbc.b();
            if (dar.result) {
                MapSharePreference mapSharePreference = new MapSharePreference((String) MessageBoxManager.SP_NAME_MessageBox);
                mapSharePreference.putStringValue("cursor", dar.c);
                mapSharePreference.putStringValue(MessageBoxManager.SP_KEY_MSG_BOX_CATEGORY_VERSION, dar.d);
                ArrayList<AmapMessage> allLocalMessages = MessageBoxManager.getInstance().getAllLocalMessages();
                HashSet hashSet = new HashSet();
                if (allLocalMessages != null) {
                    int size = allLocalMessages.size();
                    for (int i = 0; i < size; i++) {
                        hashSet.add(allLocalMessages.get(i).id);
                    }
                }
                List<btb> list = dar.b;
                if (list.isEmpty()) {
                    list = b2.d();
                } else if (list != null && !list.isEmpty()) {
                    try {
                        b2.c.insertOrReplaceInTx((Iterable<T>) list);
                    } catch (IllegalStateException e) {
                        dbc.a((Exception) e);
                    } catch (SQLiteReadOnlyDatabaseException e2) {
                        dbc.a((Exception) e2);
                    }
                }
                ArrayList<AmapMessage> arrayList = dar.a;
                daw.a(arrayList);
                MapSharePreference mapSharePreference2 = new MapSharePreference((String) MessageBoxManager.SP_NAME_PUSH_MSG);
                long j = dar.timeStamp;
                AMapLog.i("------xing----->", "本次更新时间-->updateTime=".concat(String.valueOf(j)));
                Iterator<AmapMessage> it = arrayList.iterator();
                while (it.hasNext()) {
                    AmapMessage next = it.next();
                    if (!hashSet.contains(next.id)) {
                        HashSet hashSet2 = hashSet;
                        if (!TextUtils.isEmpty(next.id) && mapSharePreference2.sharedPrefs().getLong(next.id, -1) > 0) {
                            next.isUnRead = false;
                        }
                        if (!TextUtils.isEmpty(next.pushMsgId) && mapSharePreference2.sharedPrefs().getLong(next.pushMsgId, -1) > 0) {
                            next.isUnRead = false;
                        }
                        next.updateTime = j;
                        allLocalMessages.add(next);
                        b2.a(next);
                        hashSet = hashSet2;
                    }
                }
                if (dar.e) {
                    MessageBoxManager.getInstance().shrinkMessages(allLocalMessages);
                    allLocalMessages = MessageBoxManager.getInstance().getAllLocalMessages();
                }
                this.b.lock();
                Iterator<das> it2 = this.a.iterator();
                while (it2.hasNext()) {
                    das next2 = it2.next();
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<AmapMessage> it3 = allLocalMessages.iterator();
                    while (it3.hasNext()) {
                        AmapMessage next3 = it3.next();
                        a aVar = next2.b;
                        if (aVar != null && aVar.a(next3)) {
                            arrayList2.add(next3.clone());
                        }
                    }
                    if (next2.a != null) {
                        ArrayList arrayList3 = new ArrayList();
                        arrayList3.addAll(list);
                        next2.a.a(arrayList2, arrayList3, true);
                    }
                }
                this.b.unlock();
                return dar;
            }
            ArrayList<AmapMessage> allLocalMessages2 = MessageBoxManager.getInstance().getAllLocalMessages();
            List<btb> d = b2.d();
            this.b.lock();
            Iterator<das> it4 = this.a.iterator();
            while (it4.hasNext()) {
                das next4 = it4.next();
                ArrayList arrayList4 = new ArrayList();
                Iterator<AmapMessage> it5 = allLocalMessages2.iterator();
                while (it5.hasNext()) {
                    AmapMessage next5 = it5.next();
                    a aVar2 = next4.b;
                    if (aVar2 != null && aVar2.a(next5)) {
                        arrayList4.add(next5.clone());
                    }
                }
                if (next4.a != null) {
                    ArrayList arrayList5 = new ArrayList();
                    arrayList5.addAll(d);
                    next4.a.a(arrayList4, arrayList5, true);
                }
            }
            this.b.unlock();
            return dar;
        } catch (RuntimeException e3) {
            throw e3;
        } catch (UnsupportedEncodingException e4) {
            kf.a((Throwable) e4);
        } catch (JSONException e5) {
            kf.a((Throwable) e5);
        } catch (Throwable th) {
            Throwable th2 = th;
            this.b.unlock();
            throw th2;
        }
    }
}
