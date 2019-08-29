package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.taobao.agoo.TaobaoRegister;

/* renamed from: dma reason: default package */
/* compiled from: PushIntentInterceptor */
public final class dma implements dlh {
    public final boolean a(Intent intent) {
        if (!(intent != null && TextUtils.equals(BaseIntentDispatcher.INTENT_CALL_OWNER_UMENG_PUSH, intent.getStringExtra("owner")))) {
            return false;
        }
        String stringExtra = intent.getStringExtra("key_message_id");
        if (!TextUtils.isEmpty(stringExtra)) {
            TaobaoRegister.clickMessage(MapApplication.getContext(), stringExtra, intent.getStringExtra("key_task_id"));
        }
        Uri data = intent.getData();
        if (data != null && data.isHierarchical()) {
            String queryParameter = data.getQueryParameter(DriveUtil.SOURCE_APPLICATION);
            if (!TextUtils.isEmpty(queryParameter)) {
                IMsgboxService iMsgboxService = (IMsgboxService) a.a.a(IMsgboxService.class);
                if (iMsgboxService != null) {
                    iMsgboxService.handlePush(queryParameter);
                }
            }
        }
        return false;
    }
}
