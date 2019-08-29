package com.android.dingtalk.share.ddsharemodule.message;

import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage.Builder;

public class SendMessageToDD {

    public static class Req extends BaseReq {
        public static final int DDSceneSession = 0;
        private static final String TAG = "SendMessageToDD.Req";
        public DDMediaMessage mMediaMessage;
        private int mScene = 0;

        public Req() {
        }

        public Req(Bundle bundle) {
            fromBundle(bundle);
        }

        public int getType() {
            return this.mMediaMessage.getType();
        }

        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
            this.mMediaMessage = Builder.fromBundle(bundle);
            this.mScene = bundle.getInt(ShareConstant.EXTRA_SEND_MESSAGE_SCENE);
        }

        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
            bundle.putAll(Builder.toBundle(this.mMediaMessage));
            bundle.putInt(ShareConstant.EXTRA_SEND_MESSAGE_SCENE, this.mScene);
        }

        public final boolean checkArgs() {
            if (this.mMediaMessage == null) {
                return false;
            }
            return this.mMediaMessage.checkArgs();
        }
    }

    public static class Resp extends BaseResp {
        /* access modifiers changed from: 0000 */
        public final boolean checkArgs() {
            return true;
        }

        public int getType() {
            return 1;
        }

        public Resp() {
        }

        public Resp(Bundle bundle) {
            fromBundle(bundle);
        }

        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
        }

        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
        }
    }
}
