package com.alipay.android.phone.bluetoothsdk.scan.message.protocol;

import android.text.TextUtils;
import com.alipay.android.phone.bluetoothsdk.scan.message.utils.SHA1Helper;
import java.util.ArrayList;
import java.util.List;

public class MessageDivider {
    private List<MessageFragment> mFragments = new ArrayList();

    public void divide(String data) {
        if (TextUtils.isEmpty(data)) {
            throw new NullPointerException("the length of the data cannot be 0");
        }
        int len = data.length();
        int numFragments = (len / 15) + (len % 15 == 0 ? 0 : 1);
        byte[] dataBytes = data.getBytes();
        byte[] sha = SHA1Helper.getTopSevenHexEncode(dataBytes);
        if (sha == null) {
            throw new RuntimeException("cannot get the sha1 of the data");
        }
        for (int i = 0; i < numFragments; i++) {
            MessageFragment messageFragment = new MessageFragment(sha);
            messageFragment.compose(numFragments, i, dataBytes);
            this.mFragments.add(messageFragment);
        }
    }

    public List<MessageFragment> getFragments() {
        return this.mFragments;
    }
}
