package com.tencent.mm.opensdk.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.channel.a.b;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;

public class MMessageActV2 {
    public static final String DEFAULT_ENTRY_CLASS_NAME = ".wxapi.WXEntryActivity";
    public static final String MM_ENTRY_PACKAGE_NAME = "com.tencent.mm";
    public static final String MM_MSG_ENTRY_CLASS_NAME = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
    private static final String TAG = "MicroMsg.SDK.MMessageAct";

    public static class Args {
        public static final int INVALID_FLAGS = -1;
        public Bundle bundle;
        public String content;
        public int flags = -1;
        public String targetClassName;
        public String targetPkgName;
        public String token;

        public String toString() {
            StringBuilder sb = new StringBuilder("targetPkgName:");
            sb.append(this.targetPkgName);
            sb.append(", targetClassName:");
            sb.append(this.targetClassName);
            sb.append(", content:");
            sb.append(this.content);
            sb.append(", flags:");
            sb.append(this.flags);
            sb.append(", bundle:");
            sb.append(this.bundle);
            return sb.toString();
        }
    }

    public static boolean send(Context context, Args args) {
        if (context == null || args == null) {
            Log.e(TAG, "send fail, invalid argument");
            return false;
        } else if (d.b(args.targetPkgName)) {
            StringBuilder sb = new StringBuilder("send fail, invalid targetPkgName, targetPkgName = ");
            sb.append(args.targetPkgName);
            Log.e(TAG, sb.toString());
            return false;
        } else {
            if (d.b(args.targetClassName)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(args.targetPkgName);
                sb2.append(DEFAULT_ENTRY_CLASS_NAME);
                args.targetClassName = sb2.toString();
            }
            StringBuilder sb3 = new StringBuilder("send, targetPkgName = ");
            sb3.append(args.targetPkgName);
            sb3.append(", targetClassName = ");
            sb3.append(args.targetClassName);
            Log.d(TAG, sb3.toString());
            Intent intent = new Intent();
            intent.setClassName(args.targetPkgName, args.targetClassName);
            if (args.bundle != null) {
                intent.putExtras(args.bundle);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, 620953856);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, args.content);
            intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(args.content, 620953856, packageName));
            intent.putExtra(ConstantsAPI.TOKEN, args.token);
            if (args.flags == -1) {
                intent.addFlags(268435456).addFlags(134217728);
            } else {
                intent.setFlags(args.flags);
            }
            try {
                context.startActivity(intent);
                Log.d(TAG, "send mm message, intent=".concat(String.valueOf(intent)));
                return true;
            } catch (Exception e) {
                StringBuilder sb4 = new StringBuilder("send fail, ex = ");
                sb4.append(e.getMessage());
                Log.e(TAG, sb4.toString());
                return false;
            }
        }
    }
}
