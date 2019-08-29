package com.alibaba.analytics.core.config;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.utils.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;

public class UTSampleConfBiz extends UTOrangeConfBiz {
    private static UTSampleConfBiz s_instance;
    private Map<String, UTSampleItem> mSampleItemMap = new HashMap();

    static class UTSampleItem {
        private static final String KEY_ARG1 = "arg1";
        private static final String KEY_CP = "cp";
        private static final Random s_random = new Random();
        private Map<String, Integer> mArg1CP = new HashMap();
        private int mDefaultCP = 0;

        private UTSampleItem() {
        }

        public boolean isSampleSuccess(String str) {
            return _isArg1SampleSuccess(str);
        }

        private boolean _isArg1SampleSuccess(String str) {
            if (str != null) {
                try {
                    for (String next : this.mArg1CP.keySet()) {
                        if (!next.startsWith("%") || !next.endsWith("%")) {
                            if (str.equals(next)) {
                                return _isSuccess(this.mArg1CP.get(next).intValue());
                            }
                        } else if (str.contains(next.substring(1, next.length() - 1))) {
                            return _isSuccess(this.mArg1CP.get(next).intValue());
                        }
                    }
                } catch (Throwable unused) {
                }
            }
            return _isSuccess(this.mDefaultCP);
        }

        private boolean _isSuccess(int i) {
            return i != 0 && s_random.nextInt(10000) < i;
        }

        public static UTSampleItem parseJson(String str) {
            try {
                UTSampleItem uTSampleItem = new UTSampleItem();
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("cp")) {
                    uTSampleItem.mDefaultCP = jSONObject.optInt("cp");
                }
                if (jSONObject.has(KEY_ARG1)) {
                    HashMap hashMap = new HashMap();
                    JSONObject optJSONObject = jSONObject.optJSONObject(KEY_ARG1);
                    if (optJSONObject != null) {
                        Iterator<String> keys = optJSONObject.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            hashMap.put(next, Integer.valueOf(Integer.parseInt(optJSONObject.optString(next))));
                        }
                    }
                    uTSampleItem.mArg1CP = hashMap;
                }
                return uTSampleItem;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static class UTSampleResult {
        private boolean mIsRuleExist;
        private boolean mResult;

        private UTSampleResult() {
            this.mResult = false;
            this.mIsRuleExist = false;
        }

        public void setResult(boolean z) {
            this.mResult = z;
        }

        public boolean getResult() {
            return this.mResult;
        }

        public void setIsRuleExist(boolean z) {
            this.mIsRuleExist = z;
        }

        public boolean isRuleExist() {
            return this.mIsRuleExist;
        }
    }

    public static UTSampleConfBiz getInstance() {
        if (s_instance == null) {
            s_instance = new UTSampleConfBiz();
        }
        return s_instance;
    }

    private UTSampleConfBiz() {
    }

    public void resetSampleItemMap() {
        this.mSampleItemMap.clear();
    }

    public synchronized boolean isSampleSuccess(Map<String, String> map) {
        try {
        } catch (Exception e) {
            Logger.e("UTSampleConfBiz", e, new Object[0]);
            return false;
        }
        return isSampleSuccess(Integer.parseInt(map.get(LogField.EVENTID.toString())), map.get(LogField.ARG1.toString()));
    }

    public synchronized boolean isSampleSuccess(int i, String str) {
        if (Variables.getInstance().getDebugSamplingOption()) {
            return true;
        }
        if (this.mSampleItemMap.size() == 0) {
            return true;
        }
        UTSampleResult _getSampleResult = _getSampleResult(i, str);
        if (_getSampleResult.getResult()) {
            return true;
        }
        if (_getSampleResult.isRuleExist()) {
            return false;
        }
        UTSampleResult _getSampleResult2 = _getSampleResult(i - (i % 10), str);
        if (_getSampleResult2.getResult()) {
            return true;
        }
        if (_getSampleResult2.isRuleExist()) {
            return false;
        }
        UTSampleResult _getSampleResult3 = _getSampleResult(i - (i % 100), str);
        if (_getSampleResult3.getResult()) {
            return true;
        }
        if (_getSampleResult3.isRuleExist()) {
            return false;
        }
        UTSampleResult _getSampleResult4 = _getSampleResult(i - (i % 1000), str);
        if (_getSampleResult4.getResult()) {
            return true;
        }
        if (_getSampleResult4.isRuleExist()) {
            return false;
        }
        UTSampleResult _getSampleResult5 = _getSampleResult(-1, str);
        if (_getSampleResult5.getResult()) {
            return true;
        }
        return _getSampleResult5.isRuleExist() ? false : false;
    }

    private UTSampleResult _getSampleResult(int i, String str) {
        String valueOf = String.valueOf(i);
        UTSampleResult uTSampleResult = new UTSampleResult();
        if (this.mSampleItemMap.containsKey(valueOf)) {
            uTSampleResult.setIsRuleExist(true);
            uTSampleResult.setResult(this.mSampleItemMap.get(valueOf).isSampleSuccess(str));
            return uTSampleResult;
        }
        uTSampleResult.setResult(false);
        return uTSampleResult;
    }

    public void onNonOrangeConfigurationArrive(String str) {
        super.onNonOrangeConfigurationArrive(str);
    }

    public synchronized void onOrangeConfigurationArrive(String str, Map<String, String> map) {
        this.mSampleItemMap.clear();
        for (String next : map.keySet()) {
            String str2 = map.get(next);
            if (str2 != null) {
                UTSampleItem parseJson = UTSampleItem.parseJson(str2);
                if (parseJson != null) {
                    this.mSampleItemMap.put(next, parseJson);
                }
            }
        }
    }

    public String[] getOrangeGroupnames() {
        return new String[]{"ut_sample"};
    }
}
