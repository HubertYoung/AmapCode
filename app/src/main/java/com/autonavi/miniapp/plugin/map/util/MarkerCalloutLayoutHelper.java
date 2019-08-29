package com.autonavi.miniapp.plugin.map.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.CustomCallout;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Marker;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.RichTextInfo;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class MarkerCalloutLayoutHelper {
    private static final String CUSTOMCALLOUT_ICONFONT_RIGHT_ARROW = "iconfont_right_arrow";
    private DefaultCalloutLayoutHolder defaultCalloutLayoutHolder;
    private GrayCalloutLayoutHolder grayCalloutLayoutHolder;
    private MultiColorTextCalloutHolder multiColorTextCalloutHolder;
    private WhiteCalloutLayoutHolder whiteCalloutLayoutHolder;

    static class DefaultCalloutLayoutHolder {
        ViewGroup container;
        TextView content;
        ViewGroup root;
        TextView title;

        private DefaultCalloutLayoutHolder() {
        }
    }

    static class GrayCalloutLayoutHolder {
        TextView descText;
        AUIconView iconView;
        View leftLayout;
        View root;
        View splitLine;
        TextView timeText;

        private GrayCalloutLayoutHolder() {
        }
    }

    static class MultiColorTextCalloutHolder {
        TextView descView;
        View root;

        private MultiColorTextCalloutHolder() {
        }
    }

    class StringInfo {
        public int color;
        public int end;
        public int start;

        private StringInfo() {
        }
    }

    static class WhiteCalloutLayoutHolder {
        View containerView;
        TextView descText;
        AUIconView iconView;
        View leftLayout;
        View root;
        View splitLine;
        TextView timeText;
        TextView timeUnitText;

        private WhiteCalloutLayoutHolder() {
        }
    }

    public Bitmap getMarkerCustomCallout(Context context, Marker marker) {
        View view;
        Bitmap bitmap;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        if (layoutInflater == null) {
            return null;
        }
        CustomCallout customCallout = marker.customCallout;
        if (customCallout != null) {
            switch (marker.customCallout.type) {
                case 0:
                    view = getGrayCallout(layoutInflater, customCallout.time, customCallout.descList);
                    break;
                case 1:
                    view = getWhiteCallout(layoutInflater, customCallout.time, customCallout.descList);
                    break;
                case 2:
                    view = getMultiColorTextCallout(layoutInflater, customCallout.descList);
                    break;
            }
        }
        view = null;
        if (view == null) {
            return null;
        }
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        try {
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            try {
                view.draw(new Canvas(bitmap));
            } catch (OutOfMemoryError unused) {
            }
        } catch (OutOfMemoryError unused2) {
            bitmap = null;
        }
        return bitmap;
    }

    public Bitmap getMarkerCallout(Context context, Marker marker) {
        Bitmap bitmap;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        if (layoutInflater == null) {
            return null;
        }
        View defaultCallout = getDefaultCallout(layoutInflater, marker);
        if (defaultCallout == null) {
            return null;
        }
        defaultCallout.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        defaultCallout.layout(0, 0, defaultCallout.getMeasuredWidth(), defaultCallout.getMeasuredHeight());
        try {
            bitmap = Bitmap.createBitmap(defaultCallout.getWidth(), defaultCallout.getHeight(), Config.ARGB_8888);
            try {
                defaultCallout.draw(new Canvas(bitmap));
            } catch (OutOfMemoryError unused) {
            }
        } catch (OutOfMemoryError unused2) {
            bitmap = null;
        }
        return bitmap;
    }

    private View getMultiColorTextCallout(LayoutInflater layoutInflater, List<RichTextInfo> list) {
        if (this.multiColorTextCalloutHolder == null) {
            this.multiColorTextCalloutHolder = new MultiColorTextCalloutHolder();
            View inflate = layoutInflater.inflate(R.layout.custom_callout_white_style_layout, null);
            this.multiColorTextCalloutHolder.root = inflate;
            this.multiColorTextCalloutHolder.descView = (TextView) inflate.findViewById(R.id.custom_callout_desc);
        }
        this.multiColorTextCalloutHolder.descView.setText(getRichTextInfoString(list));
        return this.multiColorTextCalloutHolder.root;
    }

    private View getGrayCallout(LayoutInflater layoutInflater, String str, List<RichTextInfo> list) {
        Context context = layoutInflater.getContext();
        if (this.grayCalloutLayoutHolder == null) {
            this.grayCalloutLayoutHolder = new GrayCalloutLayoutHolder();
            View inflate = layoutInflater.inflate(R.layout.custom_callout_layout, null);
            this.grayCalloutLayoutHolder.root = inflate;
            this.grayCalloutLayoutHolder.timeText = (TextView) inflate.findViewById(R.id.custom_callout_left_value);
            this.grayCalloutLayoutHolder.descText = (TextView) inflate.findViewById(R.id.custom_callout_right_desc);
            this.grayCalloutLayoutHolder.iconView = (AUIconView) inflate.findViewById(R.id.custom_callout_right_arrow);
            this.grayCalloutLayoutHolder.leftLayout = inflate.findViewById(R.id.custom_callout_left_layout);
            this.grayCalloutLayoutHolder.splitLine = inflate.findViewById(R.id.custom_callout_split_line);
        }
        this.grayCalloutLayoutHolder.descText.setText(getRichTextInfoString(list));
        this.grayCalloutLayoutHolder.timeText.setText(str);
        this.grayCalloutLayoutHolder.iconView.setIconfontColor(context.getResources().getColor(R.color.custom_callout_black_style_arrow_color));
        this.grayCalloutLayoutHolder.iconView.setIconByName(CUSTOMCALLOUT_ICONFONT_RIGHT_ARROW);
        if (TextUtils.isEmpty(str)) {
            this.grayCalloutLayoutHolder.leftLayout.setVisibility(8);
            this.grayCalloutLayoutHolder.splitLine.setVisibility(8);
        } else {
            this.grayCalloutLayoutHolder.leftLayout.setVisibility(0);
            this.grayCalloutLayoutHolder.splitLine.setVisibility(0);
        }
        return this.grayCalloutLayoutHolder.root;
    }

    private View getWhiteCallout(LayoutInflater layoutInflater, String str, List<RichTextInfo> list) {
        Context context = layoutInflater.getContext();
        if (this.whiteCalloutLayoutHolder == null) {
            this.whiteCalloutLayoutHolder = new WhiteCalloutLayoutHolder();
            View inflate = layoutInflater.inflate(R.layout.custom_callout_layout, null);
            this.whiteCalloutLayoutHolder.root = inflate;
            this.whiteCalloutLayoutHolder.containerView = inflate.findViewById(R.id.custom_callout_container);
            this.whiteCalloutLayoutHolder.timeText = (TextView) inflate.findViewById(R.id.custom_callout_left_value);
            this.whiteCalloutLayoutHolder.descText = (TextView) inflate.findViewById(R.id.custom_callout_right_desc);
            this.whiteCalloutLayoutHolder.timeUnitText = (TextView) inflate.findViewById(R.id.custom_callout_left_value_unit);
            this.whiteCalloutLayoutHolder.iconView = (AUIconView) inflate.findViewById(R.id.custom_callout_right_arrow);
            this.whiteCalloutLayoutHolder.leftLayout = inflate.findViewById(R.id.custom_callout_left_layout);
            this.whiteCalloutLayoutHolder.splitLine = inflate.findViewById(R.id.custom_callout_split_line);
        }
        this.whiteCalloutLayoutHolder.containerView.setBackgroundResource(R.drawable.white_bg_9);
        this.whiteCalloutLayoutHolder.iconView.setIconfontColor(context.getResources().getColor(R.color.custom_callout_white_style_arrow_color));
        this.whiteCalloutLayoutHolder.iconView.setIconByName(CUSTOMCALLOUT_ICONFONT_RIGHT_ARROW);
        this.whiteCalloutLayoutHolder.timeText.setTextColor(context.getResources().getColor(R.color.custom_callout_white_style_time_color));
        this.whiteCalloutLayoutHolder.timeUnitText.setTextColor(context.getResources().getColor(R.color.custom_callout_white_style_time_unit_color));
        this.whiteCalloutLayoutHolder.timeText.setText(str);
        this.whiteCalloutLayoutHolder.descText.setText(getRichTextInfoString(list));
        this.whiteCalloutLayoutHolder.splitLine.setBackgroundColor(context.getResources().getColor(R.color.custom_callout_white_style_split_color));
        if (TextUtils.isEmpty(str)) {
            this.whiteCalloutLayoutHolder.leftLayout.setVisibility(8);
            this.whiteCalloutLayoutHolder.splitLine.setVisibility(8);
        } else {
            this.whiteCalloutLayoutHolder.leftLayout.setVisibility(0);
            this.whiteCalloutLayoutHolder.splitLine.setVisibility(0);
        }
        return this.whiteCalloutLayoutHolder.root;
    }

    private SpannableStringBuilder getRichTextInfoString(List<RichTextInfo> list) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
        if (list == null || list.isEmpty()) {
            return spannableStringBuilder;
        }
        StringBuilder sb = new StringBuilder("");
        ArrayList<StringInfo> arrayList = new ArrayList<>();
        int i = 0;
        for (RichTextInfo next : list) {
            if (next != null && !TextUtils.isEmpty(next.desc)) {
                sb.append(next.desc);
                int length = next.desc.length() + i;
                arrayList.add(initStringInfo(i, length, next.descColor));
                i = length;
            }
        }
        spannableStringBuilder.append(sb.toString());
        for (StringInfo stringInfo : arrayList) {
            spannableStringBuilder.setSpan(new ForegroundColorSpan(stringInfo.color), stringInfo.start, stringInfo.end, 33);
        }
        return spannableStringBuilder;
    }

    private StringInfo initStringInfo(int i, int i2, String str) {
        int i3;
        StringInfo stringInfo = new StringInfo();
        stringInfo.start = i;
        stringInfo.end = i2;
        try {
            i3 = H5MapUtils.convertRGBAColor(str);
        } catch (Throwable unused) {
            i3 = H5MapUtils.convertRGBAColor("#000000");
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "parseColor error, color=".concat(String.valueOf(str)));
        }
        stringInfo.color = i3;
        return stringInfo;
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x014e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.View getDefaultCallout(android.view.LayoutInflater r13, com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Marker r14) {
        /*
            r12 = this;
            r0 = 0
            if (r14 == 0) goto L_0x0014
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r1 = r14.callout
            if (r1 == 0) goto L_0x0014
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r1 = r14.callout
            int r1 = r1.fontSize
            if (r1 != 0) goto L_0x0014
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r1 = r14.callout
            int r1 = r1.padding
            if (r1 != 0) goto L_0x0014
            return r0
        L_0x0014:
            android.content.Context r1 = r13.getContext()
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r2 = r12.defaultCalloutLayoutHolder
            if (r2 != 0) goto L_0x0053
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r2 = new com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder
            r2.<init>()
            r12.defaultCalloutLayoutHolder = r2
            int r2 = com.autonavi.minimap.R.layout.miniapp_point_callout
            android.view.View r13 = r13.inflate(r2, r0)
            android.view.ViewGroup r13 = (android.view.ViewGroup) r13
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            r0.root = r13
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            int r2 = com.autonavi.minimap.R.id.miniapp_point_callout_layout
            android.view.View r2 = r13.findViewById(r2)
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            r0.container = r2
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            int r2 = com.autonavi.minimap.R.id.miniapp_point_callout_title
            android.view.View r2 = r13.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r0.title = r2
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            int r2 = com.autonavi.minimap.R.id.miniapp_point_callout_content
            android.view.View r13 = r13.findViewById(r2)
            android.widget.TextView r13 = (android.widget.TextView) r13
            r0.content = r13
        L_0x0053:
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r13 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r13 = r13.title
            r0 = 8
            r13.setVisibility(r0)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r13 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r13 = r13.content
            r13.setVisibility(r0)
            r13 = 0
            if (r14 == 0) goto L_0x0091
            java.lang.String r2 = r14.title
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0091
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r2 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r2 = r2.title
            java.lang.String r3 = r14.title
            r2.setText(r3)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r2 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r2 = r2.title
            r2.setVisibility(r13)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r2 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r2 = r2.title
            r3 = -16514044(0xffffffffff040404, float:-1.7547895E38)
            r2.setTextColor(r3)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r2 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r2 = r2.title
            r3 = 1096810496(0x41600000, float:14.0)
            r2.setTextSize(r3)
        L_0x0091:
            r2 = -1
            if (r14 == 0) goto L_0x0160
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            if (r3 == 0) goto L_0x0160
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            java.lang.String r3 = r3.content
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0160
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r3 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r3 = r3.content
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r4 = r14.callout
            java.lang.String r4 = r4.content
            r3.setText(r4)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r3 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r3 = r3.content
            r3.setVisibility(r13)
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            int r3 = r3.fontSize
            if (r3 != 0) goto L_0x00c2
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r3 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r3 = r3.content
            r3.setVisibility(r0)
            goto L_0x00d6
        L_0x00c2:
            if (r3 >= 0) goto L_0x00ce
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r0 = r0.content
            r3 = 1093664768(0x41300000, float:11.0)
            r0.setTextSize(r3)
            goto L_0x00d6
        L_0x00ce:
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r0 = r0.content
            float r3 = (float) r3
            r0.setTextSize(r3)
        L_0x00d6:
            r0 = -8618884(0xffffffffff7c7c7c, float:-3.3561181E38)
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            java.lang.String r3 = r3.color
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x00ec
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout     // Catch:{ IllegalArgumentException -> 0x00ec }
            java.lang.String r3 = r3.color     // Catch:{ IllegalArgumentException -> 0x00ec }
            int r3 = com.autonavi.miniapp.plugin.map.util.H5MapUtils.convertRGBAColor(r3)     // Catch:{ IllegalArgumentException -> 0x00ec }
            r0 = r3
        L_0x00ec:
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r3 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r3 = r3.content
            r3.setTextColor(r0)
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r0 = r14.callout
            java.lang.String r0 = r0.textAlign
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0160
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r0 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r0 = r0.content
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            java.lang.String r3 = r3.textAlign
            int r4 = r3.hashCode()
            r5 = -1364013995(0xffffffffaeb2cc55, float:-8.1307995E-11)
            r6 = 1
            if (r4 == r5) goto L_0x0135
            r5 = 3317767(0x32a007, float:4.649182E-39)
            if (r4 == r5) goto L_0x012b
            r5 = 108511772(0x677c21c, float:4.6598146E-35)
            if (r4 == r5) goto L_0x0120
            goto L_0x013f
        L_0x0120:
            java.lang.String r4 = "right"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x013f
            r3 = 1
            goto L_0x0140
        L_0x012b:
            java.lang.String r4 = "left"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x013f
            r3 = 0
            goto L_0x0140
        L_0x0135:
            java.lang.String r4 = "center"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x013f
            r3 = 2
            goto L_0x0140
        L_0x013f:
            r3 = -1
        L_0x0140:
            r4 = 3
            switch(r3) {
                case 0: goto L_0x014e;
                case 1: goto L_0x014a;
                case 2: goto L_0x0147;
                default: goto L_0x0144;
            }
        L_0x0144:
            r0.gravity = r4
            goto L_0x0150
        L_0x0147:
            r0.gravity = r6
            goto L_0x0150
        L_0x014a:
            r3 = 5
            r0.gravity = r3
            goto L_0x0150
        L_0x014e:
            r0.gravity = r4
        L_0x0150:
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r3 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r3 = r3.content
            r3.setLayoutParams(r0)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r3 = r12.defaultCalloutLayoutHolder
            android.widget.TextView r3 = r3.content
            int r0 = r0.gravity
            r3.setGravity(r0)
        L_0x0160:
            r0 = 1084227584(0x40a00000, float:5.0)
            int r0 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r0)
            float r6 = (float) r0
            r0 = 10
            if (r14 == 0) goto L_0x01a4
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            if (r3 == 0) goto L_0x01a4
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            int r3 = r3.padding
            if (r3 < 0) goto L_0x017a
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            int r3 = r3.padding
            goto L_0x017c
        L_0x017a:
            r3 = 10
        L_0x017c:
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r4 = r14.callout
            int r4 = r4.padding
            if (r4 < 0) goto L_0x0187
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r4 = r14.callout
            int r4 = r4.padding
            goto L_0x0189
        L_0x0187:
            r4 = 10
        L_0x0189:
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r5 = r14.callout
            int r5 = r5.padding
            if (r5 < 0) goto L_0x0194
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r5 = r14.callout
            int r5 = r5.padding
            goto L_0x0196
        L_0x0194:
            r5 = 10
        L_0x0196:
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r7 = r14.callout
            int r7 = r7.padding
            if (r7 < 0) goto L_0x01a0
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r0 = r14.callout
            int r0 = r0.padding
        L_0x01a0:
            r11 = r3
            r3 = r0
            r0 = r11
            goto L_0x01aa
        L_0x01a4:
            r3 = 10
            r4 = 10
            r5 = 10
        L_0x01aa:
            if (r14 == 0) goto L_0x01b5
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r7 = r14.callout
            if (r7 == 0) goto L_0x01b5
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r7 = r14.callout
            int r7 = r7.borderWidth
            goto L_0x01b6
        L_0x01b5:
            r7 = 0
        L_0x01b6:
            int r0 = r0 + r7
            int r4 = r4 + r7
            int r5 = r5 + r7
            int r3 = r3 + r7
            float r0 = (float) r0
            int r0 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r0)
            float r4 = (float) r4
            int r4 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r4)
            float r5 = (float) r5
            int r5 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r5)
            float r3 = (float) r3
            int r3 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r3)
            float r3 = (float) r3
            float r3 = r3 + r6
            int r3 = (int) r3
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r8 = r12.defaultCalloutLayoutHolder
            android.view.ViewGroup r8 = r8.container
            r8.setPadding(r4, r0, r5, r3)
            r0 = 1086324736(0x40c00000, float:6.0)
            int r0 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r0)
            float r0 = (float) r0
            if (r14 == 0) goto L_0x021a
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            if (r3 == 0) goto L_0x021a
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r0 = r14.callout
            float r0 = r0.borderRadius
            int r0 = com.alipay.mobile.antui.utils.DensityUtil.dip2px(r1, r0)
            float r0 = (float) r0
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            java.lang.String r3 = r3.borderColor
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0201
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout     // Catch:{ IllegalArgumentException -> 0x0201 }
            java.lang.String r3 = r3.borderColor     // Catch:{ IllegalArgumentException -> 0x0201 }
            int r3 = com.autonavi.miniapp.plugin.map.util.H5MapUtils.convertRGBAColor(r3)     // Catch:{ IllegalArgumentException -> 0x0201 }
            r13 = r3
        L_0x0201:
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r3 = r14.callout
            java.lang.String r3 = r3.bgColor
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0217
            com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Callout r14 = r14.callout     // Catch:{ IllegalArgumentException -> 0x0217 }
            java.lang.String r14 = r14.bgColor     // Catch:{ IllegalArgumentException -> 0x0217 }
            int r14 = com.autonavi.miniapp.plugin.map.util.H5MapUtils.convertRGBAColor(r14)     // Catch:{ IllegalArgumentException -> 0x0217 }
            r8 = r13
            r9 = r14
            r5 = r0
            goto L_0x021d
        L_0x0217:
            r8 = r13
            r5 = r0
            goto L_0x021c
        L_0x021a:
            r5 = r0
            r8 = 0
        L_0x021c:
            r9 = -1
        L_0x021d:
            com.autonavi.miniapp.plugin.view.MiniAppBubbleDrawable r13 = new com.autonavi.miniapp.plugin.view.MiniAppBubbleDrawable
            r14 = 1073741824(0x40000000, float:2.0)
            float r4 = r6 * r14
            float r14 = (float) r7
            int r14 = defpackage.agn.a(r1, r14)
            float r7 = (float) r14
            r10 = 3
            r3 = r13
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r14 = r12.defaultCalloutLayoutHolder
            android.view.ViewGroup r14 = r14.container
            r14.setBackground(r13)
            com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper$DefaultCalloutLayoutHolder r13 = r12.defaultCalloutLayoutHolder
            android.view.ViewGroup r13 = r13.root
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.util.MarkerCalloutLayoutHelper.getDefaultCallout(android.view.LayoutInflater, com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor$Marker):android.view.View");
    }
}
