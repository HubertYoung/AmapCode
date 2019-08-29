package com.alipay.mobile.monitor.track.spm.merge;

import android.text.TextUtils;
import com.alipay.android.phone.wallet.spmtracker.Constant;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.monitor.track.spm.SpmUtils;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.MergeTracker;
import java.util.Map;

public class MergeTask {
    private final String a = Constant.UCID;
    private Builder b;
    private int c;
    private int d;
    private String e;

    public MergeTask(MergeTracker mergeTracker) {
        merge(mergeTracker);
    }

    public MergeTask merge(MergeTracker mergeTracker) {
        if (mergeTracker != null && (this.e == null || this.e.equals(mergeTracker.getBehavorId()))) {
            this.e = mergeTracker.getBehavorId();
            if (this.b == null) {
                b(mergeTracker.getBehavorBuilder().build());
            } else {
                a(mergeTracker.getBehavorBuilder().build());
            }
        }
        return this;
    }

    public Builder getBehavorBuider() {
        return this.b;
    }

    public boolean needCommit() {
        return this.c >= MergeUtil.getMergedMaxSize() || this.d >= MergeUtil.getMergedMaxCount();
    }

    private void a(Behavor mergeBehavor) {
        String merged = this.b.build().getExtParams().get(MergeUtil.KEY_EXPOSED);
        if (TextUtils.isEmpty(merged)) {
            b(mergeBehavor);
            return;
        }
        Map param4 = mergeBehavor.getExtParams();
        if (param4 != null) {
            String ridKey = a(param4.get(MergeUtil.KEY_RID));
            String paramStr = a(param4);
            StringBuilder mergedParam4 = new StringBuilder("");
            if (merged.contains(ridKey)) {
                String[] requestList = merged.split("__");
                if (requestList != null && requestList.length > 0) {
                    int i = 0;
                    while (true) {
                        if (i >= requestList.length) {
                            break;
                        } else if (requestList[i].contains(ridKey)) {
                            requestList[i] = requestList[i] + paramStr;
                            break;
                        } else {
                            i++;
                        }
                    }
                    for (String requestItems : requestList) {
                        mergedParam4.append(requestItems);
                        mergedParam4.append("__");
                    }
                }
            } else {
                mergedParam4.append(merged);
                mergedParam4.append(ridKey);
                mergedParam4.append(paramStr);
                mergedParam4.append("__");
            }
            String mergedStr = mergedParam4.toString();
            this.b.addExtParam(MergeUtil.KEY_EXPOSED, mergedStr);
            if (param4.containsKey(MergeUtil.getMergeBlackList())) {
                this.b.addExtParam(MergeUtil.getMergeBlackList(), param4.get(MergeUtil.getMergeBlackList()));
            }
            this.c = mergedStr.getBytes().length;
            this.d++;
        }
    }

    private void b(Behavor copyFrom) {
        if (this.b == null) {
            this.b = new Builder(Constant.UCID);
            this.b.setSeedID(copyFrom.getSeedID());
            this.b.setBehaviourPro(copyFrom.getBehaviourPro()).setPageId(copyFrom.getPageId()).setLoggerLevel(copyFrom.getLoggerLevel());
            this.b.setParam1(copyFrom.getParam1());
            this.b.setParam2(copyFrom.getParam2());
            this.b.setParam3(copyFrom.getParam3());
        }
        Map param4 = copyFrom.getExtParams();
        StringBuilder mergedParam4 = new StringBuilder("");
        if (param4 != null) {
            mergedParam4.append(a(param4.get(MergeUtil.KEY_RID)));
            mergedParam4.append(a(param4));
        }
        mergedParam4.append("__");
        String mergedStr = mergedParam4.toString();
        this.b.addExtParam(MergeUtil.KEY_EXPOSED, mergedStr);
        if (param4.containsKey(MergeUtil.getMergeBlackList())) {
            this.b.addExtParam(MergeUtil.getMergeBlackList(), param4.get(MergeUtil.getMergeBlackList()));
        }
        this.c = mergedStr.getBytes().length;
        this.d++;
    }

    private String a(String rid) {
        StringBuilder sb = new StringBuilder();
        StringBuilder append = sb.append(MergeUtil.KEY_RID).append(MergeUtil.SEPARATOR_KV);
        if (TextUtils.isEmpty(rid)) {
            rid = "";
        }
        append.append(rid).append(":");
        return sb.toString();
    }

    private String a(Map<String, String> param4) {
        if (param4 == null) {
            return "";
        }
        StringBuilder mergedParam4 = new StringBuilder("");
        for (String key : param4.keySet()) {
            if (MergeUtil.KEY_EXPOSED.equals(key)) {
                if (SpmUtils.isDebug) {
                    throw new IllegalArgumentException("\"exposed\"是保留字段，扩展参数中key不能使用\"exposed\"");
                }
            } else if (!MergeUtil.KEY_RID.equals(key) && !MergeUtil.getMergeBlackList().equals(key)) {
                mergedParam4.append(key).append(MergeUtil.SEPARATOR_KV).append(param4.get(key)).append(";");
            }
        }
        mergedParam4.append("&");
        return mergedParam4.toString();
    }

    public String getBehavorId() {
        return this.e;
    }
}
