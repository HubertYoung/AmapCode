package com.autonavi.bundle.msgbox.ajx;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessageModel;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

@AjxModule("messageBoxService")
public final class ModuleMessageBox extends AbstractModule {
    /* access modifiers changed from: private */
    public JsFunctionCallback mHasNewMessageJsFuncCallback;
    private IMessageBoxService mIMessageBoxService = new MessageBoxServiceImpl();

    public static class a extends com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.a {
        public final boolean a(AmapMessage amapMessage) {
            return AmapMessage.TYPE_MSG.equals(amapMessage.type) && amapMessage.isUnRead && amapMessage.isNewComing;
        }
    }

    public class b implements com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.b {
        public b() {
        }

        public final void a(List<AmapMessage> list, List<btb> list2, boolean z) {
            IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
            int access$000 = ModuleMessageBox.getBadgeCount(list, list2) + (iMsgboxService != null ? iMsgboxService.getMsgboxStorageService().a() : 0);
            if (ModuleMessageBox.this.mHasNewMessageJsFuncCallback != null) {
                if (access$000 > 0) {
                    ModuleMessageBox.this.mHasNewMessageJsFuncCallback.callback("1");
                    return;
                }
                ModuleMessageBox.this.mHasNewMessageJsFuncCallback.callback("0");
            }
        }
    }

    public ModuleMessageBox(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("updateMyUnreadNewsCount")
    public final void updateMyUnreadNewsCount(String str) {
        if (!TextUtils.isEmpty(str)) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("myMsgUnreadCount", Integer.parseInt(str));
        }
    }

    @AjxMethod("getOnlineData")
    public final void getOnlineData(final JsFunctionCallback jsFunctionCallback, String str) {
        if (!(jsFunctionCallback == null || this.mIMessageBoxService == null)) {
            this.mIMessageBoxService.a(new awm() {
                public final void a(List<AmapMessageModel> list, List<btb> list2) {
                    String a2 = daw.a(list, list2);
                    jsFunctionCallback.callback(a2);
                }
            }, str);
        }
    }

    @AjxMethod("showMsgRedDot")
    public final void showMsgRedDot() {
        ((IMainMapService) ank.a(IMainMapService.class)).c().setNewMsgVisibility(0);
    }

    @AjxMethod("updateDataToReadForId")
    public final void updateDataToReadForId(String str) {
        if (this.mIMessageBoxService != null) {
            this.mIMessageBoxService.a(str);
        }
    }

    @AjxMethod("updateInnerRedPointToReadForIds")
    public final void updateInnerRedPointToReadForIds(String str) {
        if (this.mIMessageBoxService != null) {
            this.mIMessageBoxService.b(jsonToStringArray(str));
        }
    }

    @AjxMethod("updateDataToAppearedForId")
    public final void updateDataToAppearedForId(String str) {
        this.mIMessageBoxService.a(str);
    }

    private String[] jsonToStringArray(String str) {
        String[] strArr = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            String[] strArr2 = new String[jSONArray.length()];
            int i = 0;
            while (i < jSONArray.length()) {
                try {
                    strArr2[i] = jSONArray.getString(i);
                    i++;
                } catch (JSONException e) {
                    e = e;
                    strArr = strArr2;
                    e.printStackTrace();
                    return strArr;
                }
            }
            return strArr2;
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return strArr;
        }
    }

    @AjxMethod("hasNewMessage")
    public final void hasNewMessage(JsFunctionCallback jsFunctionCallback) {
        MessageBoxManager.getInstance().getMessages(new b(), false, new a());
        this.mHasNewMessageJsFuncCallback = jsFunctionCallback;
    }

    @AjxMethod("intoMessageCenter")
    public final void intoMessageCenter() {
        czj czj = (czj) ((IMainMapService) ank.a(IMainMapService.class)).a(czj.class);
        if (czj != null) {
            czj.b();
        }
        IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.jumpToMainPage();
            defpackage.csb.a.k();
        }
        fhb fhb = (fhb) defpackage.esb.a.a.a(fhb.class);
        if (fhb != null) {
            fhb.d().a();
        }
    }

    /* access modifiers changed from: private */
    public static final int getBadgeCount(List<AmapMessage> list, List<btb> list2) {
        int i = 0;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        HashSet hashSet = new HashSet();
        if (list2 != null) {
            for (btb next : list2) {
                if ("1".equals(next.f) && !TextUtils.isEmpty(next.a)) {
                    hashSet.add(next.a);
                }
            }
        }
        for (AmapMessage next2 : list) {
            if (AmapMessage.TYPE_MSG.equals(next2.type) && next2.isNewComing && next2.msgType != 1) {
                if (hashSet.contains(next2.category) || !TextUtils.isEmpty(next2.label)) {
                    i++;
                }
            }
        }
        return i;
    }
}
