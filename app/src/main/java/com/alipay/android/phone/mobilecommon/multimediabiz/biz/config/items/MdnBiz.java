package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MdnBiz extends BaseConfig {
    private static final String DEF_BIZ = "mdn-biz";
    private static final String FILE_FORMAT = "%s%s%s?fileid=%s&bz=%s";
    private static final String FORMAT = "%s%s%s";
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String IMAGE_FORMAT = "%s%s%s?fileid=%s&zoom=%s&bz=%s";
    private static final String TAG = "MdnBiz";
    private static final String mFilePath = "/wsdk/file";
    private static final String mImgPath = "/wsdk/img";
    public HashMap<String, Float> dlbizs = new HashMap<>();
    private final String mDevDomain = "mmtcdp.stable.alipay.net:443";
    private int mDlSwitch = 1;
    public String mDomain = "mdn.alipay.com";
    private int mFileDlHttp = 0;
    private int mImgDlHttp = 0;

    public void setNeedUpdate() {
        super.setNeedUpdate();
        this.dlbizs.clear();
    }

    public boolean checkBusiness(String bizType) {
        if (this.mDlSwitch != 1 || TextUtils.isEmpty(bizType) || this.dlbizs.isEmpty()) {
            return false;
        }
        String target = null;
        Set keySet = this.dlbizs.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return false;
        }
        Iterator<String> it = keySet.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String key = it.next();
            if (bizType.contains(key)) {
                target = key;
                break;
            }
        }
        if (TextUtils.isEmpty(target)) {
            return false;
        }
        Float rate = this.dlbizs.get(target);
        if (rate == null || Double.compare(Math.random(), (double) rate.floatValue()) > 0) {
            return false;
        }
        return true;
    }

    public void parseJson(String json) {
        updateTime();
        if (!TextUtils.isEmpty(json)) {
            JSONObject object = JSON.parseObject(json);
            if (object != null) {
                this.mDlSwitch = object.getIntValue("ds");
                this.mImgDlHttp = object.getIntValue("imgHttp");
                this.mFileDlHttp = object.getIntValue("fileHttp");
                JSONArray downloadBizArray = object.getJSONArray("dlbizs");
                if (downloadBizArray != null) {
                    for (int i = 0; i < downloadBizArray.size(); i++) {
                        String item = downloadBizArray.getString(i);
                        String[] arr = null;
                        if (!TextUtils.isEmpty(item)) {
                            arr = item.split("&&");
                        }
                        if (arr != null && arr.length > 1) {
                            this.dlbizs.put(arr[0], Float.valueOf(Float.valueOf(Float.valueOf(arr[1]).floatValue() / 100.0f).floatValue()));
                        }
                    }
                }
            }
        }
    }

    public String genImageDlMdnUrl(String fileId, String zoom, String bizType) {
        if (!PathUtils.isDjangoPath(fileId)) {
            Logger.D(TAG, "genImageDlMdnUrl fileid=" + fileId + "; is not id", new Object[0]);
            return fileId;
        } else if (!EnvSwitcher.isOnlineEnv()) {
            Object[] objArr = new Object[6];
            objArr[0] = "http://";
            objArr[1] = "mmtcdp.stable.alipay.net:443";
            objArr[2] = mImgPath;
            objArr[3] = fileId;
            objArr[4] = zoom;
            if (TextUtils.isEmpty(bizType)) {
                bizType = DEF_BIZ;
            }
            objArr[5] = bizType;
            return String.format(IMAGE_FORMAT, objArr);
        } else if (this.mImgDlHttp == 1) {
            Object[] objArr2 = new Object[6];
            objArr2[0] = "http://";
            objArr2[1] = this.mDomain;
            objArr2[2] = mImgPath;
            objArr2[3] = fileId;
            objArr2[4] = zoom;
            if (TextUtils.isEmpty(bizType)) {
                bizType = DEF_BIZ;
            }
            objArr2[5] = bizType;
            return String.format(IMAGE_FORMAT, objArr2);
        } else {
            Object[] objArr3 = new Object[6];
            objArr3[0] = "https://";
            objArr3[1] = this.mDomain;
            objArr3[2] = mImgPath;
            objArr3[3] = fileId;
            objArr3[4] = zoom;
            if (TextUtils.isEmpty(bizType)) {
                bizType = DEF_BIZ;
            }
            objArr3[5] = bizType;
            return String.format(IMAGE_FORMAT, objArr3);
        }
    }

    public String genFileDlMdnUrl(String fileId, String bizType) {
        if (!PathUtils.isDjangoPath(fileId)) {
            Logger.D(TAG, "genFileDlMdnUrl fileid=" + fileId + "; is not id", new Object[0]);
            return fileId;
        } else if (!EnvSwitcher.isOnlineEnv()) {
            Object[] objArr = new Object[5];
            objArr[0] = "http://";
            objArr[1] = "mmtcdp.stable.alipay.net:443";
            objArr[2] = mFilePath;
            objArr[3] = fileId;
            if (TextUtils.isEmpty(bizType)) {
                bizType = DEF_BIZ;
            }
            objArr[4] = bizType;
            return String.format(FILE_FORMAT, objArr);
        } else if (this.mFileDlHttp == 1) {
            Object[] objArr2 = new Object[5];
            objArr2[0] = "http://";
            objArr2[1] = this.mDomain;
            objArr2[2] = mFilePath;
            objArr2[3] = fileId;
            if (TextUtils.isEmpty(bizType)) {
                bizType = DEF_BIZ;
            }
            objArr2[4] = bizType;
            return String.format(FILE_FORMAT, objArr2);
        } else {
            Object[] objArr3 = new Object[5];
            objArr3[0] = "https://";
            objArr3[1] = this.mDomain;
            objArr3[2] = mFilePath;
            objArr3[3] = fileId;
            if (TextUtils.isEmpty(bizType)) {
                bizType = DEF_BIZ;
            }
            objArr3[4] = bizType;
            return String.format(FILE_FORMAT, objArr3);
        }
    }

    public String toString() {
        return "MdnBiz{mDlSwitch=" + this.mDlSwitch + ", dlbizs=" + this.dlbizs + '}';
    }
}
