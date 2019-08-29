package com.autonavi.minimap.ajx3.widget.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.dom.KeyDefine;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.animator.evaluator.StringEvaluator;
import com.autonavi.minimap.ajx3.widget.animator.interpolator.CubicBezierInterpolator;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class AjxAnimator implements AjxAnimatable {
    static final int ANIMATOR_STATE_DESTROY = 4;
    static final int ANIMATOR_STATE_FINISHED = 3;
    static final int ANIMATOR_STATE_PAUSED = 2;
    static final int ANIMATOR_STATE_PENDING = 0;
    static final int ANIMATOR_STATE_RUNNING = 1;
    private static final int PROPERTY_TYPE_ATTRIBUTE = 1056964769;
    private static final int PROPERTY_TYPE_SIZE = 1056964768;
    private boolean isNeedReverse = false;
    /* access modifiers changed from: private */
    public IAjxContext mAjxContext;
    /* access modifiers changed from: private */
    public AjxDomTree mAjxDomTree;
    /* access modifiers changed from: private */
    public ValueAnimator mAnimator = new ValueAnimator();
    /* access modifiers changed from: private */
    public final long mAnimatorId;
    /* access modifiers changed from: private */
    public int mCurState = 0;
    private long mCurrentPlayTime = -1;
    private final CubicBezierInterpolator mEaseInInterpolator = new CubicBezierInterpolator(0.42f, 0.0f, 1.0f, 1.0f);
    private final CubicBezierInterpolator mEaseInOutInterpolator = new CubicBezierInterpolator(0.42f, 0.0f, 0.58f, 1.0f);
    private final CubicBezierInterpolator mEaseInterpolator = new CubicBezierInterpolator(0.25f, 0.1f, 0.25f, 1.0f);
    private final CubicBezierInterpolator mEaseOutInterpolator = new CubicBezierInterpolator(0.0f, 0.0f, 0.58f, 1.0f);
    private TimeInterpolator mInterpolator = this.mEaseInterpolator;
    /* access modifiers changed from: private */
    public boolean mIsForbidEvent = false;
    private final CubicBezierInterpolator mLinearInterpolator = new CubicBezierInterpolator(0.0f, 0.0f, 1.0f, 1.0f);
    private Map<String, Float> mNeedReplaceEndPropertySet = new HashMap();
    private Map<String, Float> mNeedReplaceStartPropertySet = new HashMap();
    /* access modifiers changed from: private */
    public String mOptionFillType = Constants.ANIMATOR_NONE;
    /* access modifiers changed from: private */
    public Map<String, Integer> mPropertyTypeMap;
    /* access modifiers changed from: private */
    public long mTargetNodeId;

    static class AjxAnimatorUpdateListener implements AnimatorUpdateListener {
        private WeakReference<AjxAnimator> mAnimator;
        private AjxDomNode node;

        AjxAnimatorUpdateListener(AjxAnimator ajxAnimator) {
            this.mAnimator = new WeakReference<>(ajxAnimator);
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            AjxAnimator ajxAnimator = (AjxAnimator) this.mAnimator.get();
            if (ajxAnimator != null && 4 != ajxAnimator.mCurState && ajxAnimator.mCurState != 0 && 2 != ajxAnimator.mCurState) {
                this.node = ajxAnimator.mAjxDomTree.findNodeById(ajxAnimator.mTargetNodeId);
                if (this.node == null) {
                    ajxAnimator.destroy();
                    ajxAnimator.mCurState = 3;
                    ajxAnimator.mAjxContext.getJsContext().invokeAnimation(ajxAnimator.mAnimatorId, "oncancel", ajxAnimator.getPlayState());
                    return;
                }
                PropertyValuesHolder[] values = valueAnimator.getValues();
                if (values != null && values.length > 0 && !ajxAnimator.mAjxContext.hasDestroy()) {
                    for (PropertyValuesHolder propertyName : values) {
                        String propertyName2 = propertyName.getPropertyName();
                        Object animatedValue = valueAnimator.getAnimatedValue(propertyName2);
                        int intValue = ((Integer) ajxAnimator.mPropertyTypeMap.get(propertyName2)).intValue();
                        if (intValue == AjxAnimator.PROPERTY_TYPE_SIZE && (animatedValue instanceof Float)) {
                            this.node.setSize(propertyName2, ((Float) animatedValue).floatValue(), true, true, false, true);
                        } else if (KeyDefine.isStyleProperty(intValue)) {
                            this.node.setStyle(2, intValue, animatedValue, true, true, false, true);
                        } else {
                            this.node.setAttribute(propertyName2, animatedValue, true, true, false, true);
                        }
                    }
                }
            }
        }
    }

    AjxAnimator(@NonNull IAjxContext iAjxContext, @NonNull JSONArray jSONArray, @NonNull JSONObject jSONObject, long j, long j2) {
        this.mAjxContext = iAjxContext;
        this.mAnimatorId = j2;
        this.mAjxDomTree = iAjxContext.getDomTree();
        this.mPropertyTypeMap = new HashMap();
        this.mTargetNodeId = j;
        setKeyFrames(jSONArray);
        initOptions(jSONObject);
    }

    private void setKeyFrames(JSONArray jSONArray) {
        if (jSONArray != null && jSONArray.length() > 0) {
            if (jSONArray.length() == 1) {
                initProperties(jSONArray.optJSONObject(0));
            } else {
                initPropertiesByKeyFrame(jSONArray);
            }
            addListener();
            this.mAnimator.setTarget(this.mAjxDomTree);
        }
    }

    private void initProperties(@NonNull JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            String optString = jSONObject.optString(next);
            if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(optString) && !"__native_value__".equals(next)) {
                try {
                    JSONArray jSONArray = new JSONArray(optString);
                    if (jSONArray.length() >= 2) {
                        Object opt = jSONArray.opt(0);
                        Object opt2 = jSONArray.opt(1);
                        boolean z = (opt instanceof Integer) || (opt instanceof Double);
                        boolean z2 = (opt2 instanceof Integer) || (opt2 instanceof Double);
                        if (z && z2) {
                            float parseFloat = Float.parseFloat(String.valueOf(opt));
                            float parseFloat2 = Float.parseFloat(String.valueOf(opt2));
                            int i = (parseFloat > 1000000.0f ? 1 : (parseFloat == 1000000.0f ? 0 : -1));
                            if (i == 0 && parseFloat2 == 1000000.0f) {
                                try {
                                    LogHelper.showErrorMsg("ajx3支撑层，ajx3动画,不支持开始值和结束值均设置1000000", this.mAjxContext.getNativeContext());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (i == 0) {
                                    this.mNeedReplaceStartPropertySet.put(next, Float.valueOf(parseFloat2));
                                }
                                if (parseFloat2 == 1000000.0f) {
                                    this.mNeedReplaceEndPropertySet.put(next, Float.valueOf(parseFloat));
                                }
                                this.mPropertyTypeMap.put(next, Integer.valueOf(getTypeFromProperty(next)));
                                arrayList.add(PropertyValuesHolder.ofFloat(next, new float[]{parseFloat, parseFloat2}));
                            }
                        } else if (!(opt instanceof String) || !(opt2 instanceof String)) {
                            LogHelper.showErrorMsg("ajx3支撑层，ajx3动画,开始值或者结束值设置的有问题,比如开始值或者结束值设置为字符串".concat(String.valueOf(this)), this.mAjxContext.getNativeContext());
                        } else {
                            int parseColorByAnimator = StringUtils.parseColorByAnimator((String) opt);
                            int parseColorByAnimator2 = StringUtils.parseColorByAnimator((String) opt2);
                            if (parseColorByAnimator == -2 || parseColorByAnimator2 == -2) {
                                LogHelper.showErrorMsg("ajx3支撑层，ajx3动画,开始值或者结束值设置的有问题,比如开始值或者结束值设置为字符串".concat(String.valueOf(this)), this.mAjxContext.getNativeContext());
                            } else {
                                this.mPropertyTypeMap.put(next, Integer.valueOf(getTypeFromProperty(next)));
                                arrayList.add(PropertyValuesHolder.ofObject(next, new ArgbEvaluator(), new Object[]{Integer.valueOf(parseColorByAnimator), Integer.valueOf(parseColorByAnimator2)}));
                            }
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        this.mAnimator = ObjectAnimator.ofPropertyValuesHolder((PropertyValuesHolder[]) arrayList.toArray(new PropertyValuesHolder[arrayList.size()]));
    }

    private void initPropertiesByKeyFrame(@NonNull JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        String str = null;
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                Iterator<String> keys = optJSONObject.keys();
                String str2 = str;
                double d = -1.0d;
                Object obj = null;
                TimeInterpolator timeInterpolator = null;
                while (keys.hasNext()) {
                    String next = keys.next();
                    if (!"__native_value__".equals(next)) {
                        if ("offset".equals(next)) {
                            d = optJSONObject.optDouble(next);
                        } else if ("easing".equals(next)) {
                            timeInterpolator = getInterpolatorByString(optJSONObject.optString(next));
                        } else {
                            obj = optJSONObject.opt(next);
                            str2 = next;
                        }
                    }
                }
                if (d == -1.0d) {
                    int length = jSONArray.length() - 1;
                    d = length > 0 ? (double) (i / length) : 1.0d;
                }
                if (obj instanceof Integer) {
                    Keyframe ofFloat = Keyframe.ofFloat((float) d, (float) ((Integer) obj).intValue());
                    ofFloat.setInterpolator(timeInterpolator);
                    arrayList.add(ofFloat);
                } else if (obj instanceof Double) {
                    Keyframe ofFloat2 = Keyframe.ofFloat((float) d, (float) ((Double) obj).doubleValue());
                    ofFloat2.setInterpolator(timeInterpolator);
                    arrayList.add(ofFloat2);
                } else if (obj instanceof String) {
                    int parseColorByAnimator = StringUtils.parseColorByAnimator((String) obj);
                    if (parseColorByAnimator != -2) {
                        Keyframe ofInt = Keyframe.ofInt((float) d, parseColorByAnimator);
                        ofInt.setInterpolator(timeInterpolator);
                        arrayList.add(ofInt);
                        str = str2;
                        z = true;
                    } else {
                        Keyframe ofObject = Keyframe.ofObject((float) d, obj);
                        ofObject.setInterpolator(timeInterpolator);
                        arrayList.add(ofObject);
                        str = str2;
                        z2 = true;
                    }
                }
                str = str2;
            }
        }
        this.mPropertyTypeMap.put(str, Integer.valueOf(getTypeFromProperty(str)));
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(str, (Keyframe[]) arrayList.toArray(new Keyframe[arrayList.size()]));
        if (z) {
            ofKeyframe.setEvaluator(new ArgbEvaluator());
        } else if (z2) {
            ofKeyframe.setEvaluator(new StringEvaluator());
        }
        this.mAnimator = ObjectAnimator.ofPropertyValuesHolder(Long.valueOf(this.mTargetNodeId), new PropertyValuesHolder[]{ofKeyframe});
    }

    private void initOptions(@NonNull JSONObject jSONObject) {
        if (jSONObject != null) {
            long optLong = jSONObject.optLong("duration");
            if (optLong > 0) {
                this.mAnimator.setDuration(optLong);
            }
            this.mAnimator.setStartDelay(jSONObject.optLong("delay", 0));
            initDirectionOption(jSONObject.optString("direction"));
            this.mInterpolator = getInterpolatorByString(jSONObject.optString("easing"));
            this.mAnimator.setInterpolator(this.mInterpolator);
            String optString = jSONObject.optString("fill");
            if (!TextUtils.isEmpty(optString)) {
                this.mOptionFillType = optString;
            }
            initIterationsOption(jSONObject.optString("iterations"));
        }
    }

    private void initDirectionOption(@Nullable String str) {
        if (!TextUtils.isEmpty(str)) {
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1408024454) {
                if (hashCode != -1039745817) {
                    if (hashCode != 831741071) {
                        if (hashCode == 1099846370 && str.equals("reverse")) {
                            c = 2;
                        }
                    } else if (str.equals("alternate-reverse")) {
                        c = 3;
                    }
                } else if (str.equals("normal")) {
                    c = 0;
                }
            } else if (str.equals("alternate")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    this.isNeedReverse = false;
                    this.mAnimator.setRepeatMode(1);
                    return;
                case 1:
                    this.isNeedReverse = false;
                    this.mAnimator.setRepeatMode(2);
                    return;
                case 2:
                    this.isNeedReverse = true;
                    this.mAnimator.setRepeatMode(1);
                    return;
                case 3:
                    this.isNeedReverse = true;
                    this.mAnimator.setRepeatMode(2);
                    break;
            }
        }
    }

    private TimeInterpolator getInterpolatorByString(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return this.mEaseInterpolator;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1965120668) {
            if (hashCode != -1102672091) {
                if (hashCode != -789192465) {
                    if (hashCode == -361990811 && str.equals("ease-in-out")) {
                        c = 3;
                    }
                } else if (str.equals("ease-out")) {
                    c = 2;
                }
            } else if (str.equals("linear")) {
                c = 0;
            }
        } else if (str.equals("ease-in")) {
            c = 1;
        }
        switch (c) {
            case 0:
                return this.mLinearInterpolator;
            case 1:
                return this.mEaseInInterpolator;
            case 2:
                return this.mEaseOutInterpolator;
            case 3:
                return this.mEaseInOutInterpolator;
            default:
                if (!str.startsWith(Constants.CUBIC_BEGIN)) {
                    int length = str.length();
                    int indexOf = str.indexOf("(") + 1;
                    int indexOf2 = str.indexOf(")");
                    if (indexOf < length && indexOf < indexOf2 && indexOf2 < length) {
                        String[] split = str.substring(indexOf, indexOf2).split(",");
                        if (split.length == 4) {
                            try {
                                return new CubicBezierInterpolator(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return this.mEaseInterpolator;
        }
    }

    private void initIterationsOption(@Nullable String str) {
        if (!TextUtils.isEmpty(str)) {
            if (Constants.ANIMATOR_INFINITE.equals(str)) {
                this.mAnimator.setRepeatCount(-1);
                return;
            }
            try {
                int parseInt = Integer.parseInt(str);
                if (parseInt > 0) {
                    this.mAnimator.setRepeatCount(parseInt - 1);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void addListener() {
        this.mAnimator.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animator) {
                AjxAnimator.this.mCurState = 1;
                if (AjxAnimator.this.mIsForbidEvent) {
                    AjxAnimator.this.mAjxContext.getDomTree().beginForbidEvents(AjxAnimator.this.getAnimatorId());
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (AjxAnimator.this.mIsForbidEvent) {
                    AjxAnimator.this.mAjxContext.getDomTree().stopForbidEvents(AjxAnimator.this.getAnimatorId());
                }
                if (4 != AjxAnimator.this.mCurState) {
                    AjxAnimator.this.syncPropertyValue();
                    if (1 == AjxAnimator.this.mCurState) {
                        AjxAnimator.this.mCurState = 3;
                        if (Constants.ANIMATOR_NONE.equals(AjxAnimator.this.mOptionFillType) || Constants.ANIMATOR_BACKWARDS.equals(AjxAnimator.this.mOptionFillType)) {
                            AjxAnimator.this.mAnimator.setCurrentPlayTime(0);
                        }
                        AjxAnimator.this.resetAnimator();
                        AjxAnimator.this.mAjxContext.getJsContext().invokeAnimation(AjxAnimator.this.mAnimatorId, "onfinish", AjxAnimator.this.getPlayState());
                    }
                }
            }

            public void onAnimationCancel(Animator animator) {
                if (AjxAnimator.this.mIsForbidEvent) {
                    AjxAnimator.this.mAjxContext.getDomTree().stopForbidEvents(AjxAnimator.this.getAnimatorId());
                }
            }

            public void onAnimationRepeat(Animator animator) {
                if (AjxAnimator.this.mIsForbidEvent) {
                    AjxAnimator.this.mAjxContext.getDomTree().beginForbidEvents(AjxAnimator.this.getAnimatorId());
                }
            }
        });
        this.mAnimator.addUpdateListener(new AjxAnimatorUpdateListener(this));
    }

    /* access modifiers changed from: private */
    public void resetAnimator() {
        this.mCurrentPlayTime = -1;
        if (this.mAnimator.getInterpolator() != this.mInterpolator) {
            this.mAnimator.setInterpolator(this.mInterpolator);
        }
    }

    /* access modifiers changed from: private */
    public void syncPropertyValue() {
        PropertyValuesHolder[] values = this.mAnimator.getValues();
        int length = values.length;
        if (length > 0) {
            Parcel parcel = new Parcel();
            parcel.writeInt(length * 2);
            for (PropertyValuesHolder propertyName : values) {
                String propertyName2 = propertyName.getPropertyName();
                Object animatedValue = this.mAnimator.getAnimatedValue(propertyName2);
                parcel.writeString(propertyName2);
                parcel.writeString(animatedValue != null ? animatedValue.toString() : null);
            }
            this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mTargetNodeId).setAttribute(parcel).build());
        }
    }

    public String getPlayState() {
        switch (this.mCurState) {
            case 0:
                return "pending";
            case 1:
                return MiscUtils.KEY_RUNNING;
            case 2:
                return "paused";
            case 3:
                return "finish";
            case 4:
                return "destroy";
            default:
                return "pending";
        }
    }

    public Animator getAnimator() {
        return this.mAnimator;
    }

    public long[] getTargetNodeIds() {
        return new long[]{this.mTargetNodeId};
    }

    public long getAnimatorId() {
        return this.mAnimatorId;
    }

    public void checkStartEndValue() {
        if (this.mAnimator.getValues() != null) {
            replacePropertyValue(this.mNeedReplaceStartPropertySet, true);
            replacePropertyValue(this.mNeedReplaceEndPropertySet, false);
        }
    }

    private void replacePropertyValue(Map<String, Float> map, boolean z) {
        PropertyValuesHolder[] values;
        for (Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            float floatValue = ((Float) next.getValue()).floatValue();
            for (PropertyValuesHolder propertyValuesHolder : this.mAnimator.getValues()) {
                if (propertyValuesHolder.getPropertyName().equals(str)) {
                    Object valueFromProperty = getValueFromProperty(str);
                    if (valueFromProperty == null) {
                        StringBuilder sb = new StringBuilder("ajx3支撑层，AjxAnimator设置1000000时未找到属性值:");
                        sb.append(floatValue);
                        sb.append(" ,");
                        sb.append(this);
                        LogHelper.showErrorMsg(sb.toString(), this.mAjxContext.getNativeContext());
                    } else if (z) {
                        try {
                            propertyValuesHolder.setFloatValues(new float[]{Float.parseFloat(String.valueOf(valueFromProperty)), floatValue});
                        } catch (Exception e) {
                            e.printStackTrace();
                            StringBuilder sb2 = new StringBuilder("ajx3支撑层，AjxAnimator设置1000000时未找到属性值:");
                            sb2.append(floatValue);
                            sb2.append(" ,");
                            sb2.append(this);
                            LogHelper.showErrorMsg(sb2.toString(), this.mAjxContext.getNativeContext());
                        }
                    } else {
                        propertyValuesHolder.setFloatValues(new float[]{floatValue, Float.parseFloat(String.valueOf(valueFromProperty))});
                    }
                }
            }
        }
    }

    public boolean isForbidEvent() {
        return this.mIsForbidEvent;
    }

    public void setForbidEventFlag(boolean z) {
        this.mIsForbidEvent = z;
    }

    public void play() {
        if (this.mAnimator.getValues() != null) {
            if (2 == this.mCurState) {
                this.mAnimator.setCurrentPlayTime(this.mCurrentPlayTime);
            } else {
                checkStartEndValue();
            }
            if (this.isNeedReverse) {
                this.mAnimator.reverse();
            } else {
                this.mAnimator.start();
            }
            this.mCurState = 1;
        }
    }

    public void pause() {
        if (3 != this.mCurState && this.mAnimator.isStarted()) {
            this.mCurrentPlayTime = this.mAnimator.getCurrentPlayTime();
            this.mCurState = 2;
            this.mAnimator.cancel();
        }
    }

    public void resume() {
        if (this.mCurrentPlayTime > 0) {
            this.mAnimator.setCurrentPlayTime(this.mCurrentPlayTime);
            this.mAnimator.start();
            return;
        }
        if (1 == this.mCurState) {
            this.mCurState = 3;
            this.mAnimator.end();
        }
    }

    public void reverse() {
        if (this.mAnimator.getValues() != null) {
            checkStartEndValue();
            this.mCurState = 1;
            this.mAnimator.reverse();
        }
    }

    public void destroy() {
        this.mCurState = 4;
        this.mAnimator.removeAllUpdateListeners();
        this.mAnimator.removeAllListeners();
    }

    public void cancel() {
        if (3 != this.mCurState && this.mCurState != 0 && 4 != this.mCurState) {
            this.mCurState = 3;
            this.mAnimator.setCurrentPlayTime(0);
            this.mAnimator.cancel();
            resetAnimator();
            this.mAjxContext.getJsContext().invokeAnimation(this.mAnimatorId, "oncancel", getPlayState());
        }
    }

    public void finish() {
        if (3 != this.mCurState) {
            this.mAnimator.end();
        }
    }

    private Object getValueFromProperty(String str) {
        AjxDomNode findNodeById = this.mAjxDomTree.findNodeById(this.mTargetNodeId);
        if (findNodeById == null) {
            return null;
        }
        if ("left".equals(str) || "top".equals(str) || "width".equals(str) || "height".equals(str)) {
            return Float.valueOf(findNodeById.getSize(str));
        }
        int name2Type = KeyDefine.name2Type(str);
        if (name2Type == 1056964767) {
            return findNodeById.getAttributeValue(str);
        }
        return findNodeById.getStyleValue(name2Type, 0);
    }

    private int getTypeFromProperty(String str) {
        if ("left".equals(str) || "top".equals(str) || "width".equals(str) || "height".equals(str)) {
            return PROPERTY_TYPE_SIZE;
        }
        int name2Type = KeyDefine.name2Type(str);
        return name2Type == 1056964767 ? PROPERTY_TYPE_ATTRIBUTE : name2Type;
    }
}
