package com.airbnb.lottie.model.content;

import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import org.json.JSONObject;

public final class MergePaths implements hn {
    public final String a;
    public final MergePathsMode b;

    public enum MergePathsMode {
        Merge,
        Add,
        Subtract,
        Intersect,
        ExcludeIntersections;

        /* access modifiers changed from: private */
        public static MergePathsMode forId(int i) {
            switch (i) {
                case 1:
                    return Merge;
                case 2:
                    return Add;
                case 3:
                    return Subtract;
                case 4:
                    return Intersect;
                case 5:
                    return ExcludeIntersections;
                default:
                    return Merge;
            }
        }
    }

    public static class a {
        public static MergePaths a(JSONObject jSONObject) {
            return new MergePaths(jSONObject.optString(LogItem.MM_C18_K4_NM), MergePathsMode.forId(jSONObject.optInt("mm", 1)), 0);
        }
    }

    /* synthetic */ MergePaths(String str, MergePathsMode mergePathsMode, byte b2) {
        this(str, mergePathsMode);
    }

    private MergePaths(String str, MergePathsMode mergePathsMode) {
        this.a = str;
        this.b = mergePathsMode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MergePaths{mode=");
        sb.append(this.b);
        sb.append('}');
        return sb.toString();
    }

    @Nullable
    public final fe a(ew ewVar, hx hxVar) {
        if (!ewVar.l) {
            return null;
        }
        return new fm(this);
    }
}
