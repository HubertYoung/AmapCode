package com.autonavi.minimap.ajx3.htmcompat;

import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.htmcompat.AjxImageGetter.AjxImageSpan;
import com.autonavi.minimap.ajx3.htmcompat.AjxImageGetter.RemoteDrawable;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.view.Html;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class HtmlParser {
    private static final String TAG = "HtmlParser";

    private static native Parcel nativeFromHtml(String str);

    public static Spanned fromHtml(String str, AjxImageGetter ajxImageGetter) {
        String str2 = str;
        AjxSpannableStringBuilder ajxSpannableStringBuilder = new AjxSpannableStringBuilder();
        Parcel nativeFromHtml = nativeFromHtml(str);
        if (nativeFromHtml == null) {
            return ajxSpannableStringBuilder.append(str2);
        }
        nativeFromHtml.reset();
        boolean readBoolean = nativeFromHtml.readBoolean();
        int readInt = nativeFromHtml.readInt();
        if (readInt <= 0 || readBoolean) {
            if (readBoolean && Ajx.getInstance().getJsRuntimeExceptionListener() != null) {
                IAjxContext ajxContext = Ajx.getInstance().getAjxContext(Ajx.getInstance().getCurrJsContext());
                if (ajxContext == null || ajxContext.hasDestroy() || ajxContext.getDomTree() == null) {
                    return ajxSpannableStringBuilder.append(str2);
                }
                String url = ajxContext.getDomTree().getRootView().getUrl();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("message", str2);
                    jSONObject.put("name", "richtext parse error !!");
                } catch (JSONException unused) {
                }
                Ajx.getInstance().getJsRuntimeExceptionListener().onRuntimeException(ajxContext, 2, url, jSONObject.toString());
            }
            return ajxSpannableStringBuilder.append(str2);
        }
        for (int i = 0; i < readInt; i++) {
            String readString = nativeFromHtml.readString();
            if (readString == null) {
                readString = "";
            }
            int readInt2 = nativeFromHtml.readInt();
            if (readInt2 <= 0) {
                ajxSpannableStringBuilder.append(readString);
                AjxImageGetter ajxImageGetter2 = ajxImageGetter;
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < readInt2; i2++) {
                    int readInt3 = nativeFromHtml.readInt();
                    int readInt4 = nativeFromHtml.readInt();
                    HashMap hashMap = new HashMap();
                    if (readInt4 > 0) {
                        for (int i3 = 0; i3 < readInt4; i3++) {
                            hashMap.put(nativeFromHtml.readString(), nativeFromHtml.readString());
                        }
                    }
                    switch (readInt3) {
                        case 0:
                            AjxImageGetter ajxImageGetter3 = ajxImageGetter;
                            arrayList.add(new NothingSpan());
                            break;
                        case 1:
                            AjxImageGetter ajxImageGetter4 = ajxImageGetter;
                            arrayList.add(new FontSpan(hashMap));
                            break;
                        case 2:
                            AjxImageGetter ajxImageGetter5 = ajxImageGetter;
                            arrayList.add(new ASpan(hashMap));
                            break;
                        case 3:
                            AjxImageGetter ajxImageGetter6 = ajxImageGetter;
                            arrayList.add(new BrSpan());
                            break;
                        case 4:
                            AjxImageGetter ajxImageGetter7 = ajxImageGetter;
                            ajxSpannableStringBuilder.append(10).append(10);
                            arrayList.add(new AjxQuoteSpan());
                            break;
                        case 5:
                            AjxImageGetter ajxImageGetter8 = ajxImageGetter;
                            arrayList.add(new StyleSpan(2));
                            break;
                        case 6:
                            AjxImageGetter ajxImageGetter9 = ajxImageGetter;
                            arrayList.add(new UnderlineSpan());
                            break;
                        case 7:
                            AjxImageGetter ajxImageGetter10 = ajxImageGetter;
                            arrayList.add(new StrikethroughSpan());
                            break;
                        case 8:
                            AjxImageGetter ajxImageGetter11 = ajxImageGetter;
                            arrayList.add(new SubscriptSpan());
                            break;
                        case 9:
                            AjxImageGetter ajxImageGetter12 = ajxImageGetter;
                            arrayList.add(new SuperscriptSpan());
                            break;
                        case 10:
                        case 11:
                            AjxImageGetter ajxImageGetter13 = ajxImageGetter;
                            arrayList.add(new StyleSpan(1));
                            break;
                        case 12:
                            AjxImageGetter ajxImageGetter14 = ajxImageGetter;
                            arrayList.add(new RelativeSizeSpan(0.8f));
                            break;
                        case 13:
                            arrayList.add(new ImgSpan(hashMap, ajxImageGetter));
                            break;
                        default:
                            AjxImageGetter ajxImageGetter15 = ajxImageGetter;
                            break;
                    }
                }
                AjxImageGetter ajxImageGetter16 = ajxImageGetter;
                setSpan(ajxSpannableStringBuilder, readString, arrayList);
            }
        }
        return ajxSpannableStringBuilder;
    }

    private static void setSpan(AjxSpannableStringBuilder ajxSpannableStringBuilder, String str, List<Object> list) {
        int length = ajxSpannableStringBuilder.length();
        int length2 = str.length() + length;
        ajxSpannableStringBuilder.append(str);
        for (Object next : list) {
            if (!(next instanceof NothingSpan)) {
                if (next instanceof AjxQuoteSpan) {
                    ajxSpannableStringBuilder.setSpan(next, length, length2, 17);
                    ajxSpannableStringBuilder.append(10).append(10);
                } else if (next instanceof BrSpan) {
                    ajxSpannableStringBuilder.append(10);
                } else if (next instanceof ASpan) {
                    ajxSpannableStringBuilder.setSpan(new AjxURLSpan(((ASpan) next).mAttributeMap.get("href")), length, length2, 33);
                } else if (next instanceof FontSpan) {
                    FontSpan fontSpan = (FontSpan) next;
                    if (fontSpan.mAttributeMap != null && fontSpan.mAttributeMap.size() > 0) {
                        String str2 = fontSpan.mAttributeMap.get("color");
                        if (!TextUtils.isEmpty(str2)) {
                            int htmlColor = ColorUtils.getHtmlColor(str2);
                            if (htmlColor != -1) {
                                ajxSpannableStringBuilder.setSpan(new ForegroundColorSpan(htmlColor | -16777216), length, length2, 33);
                            }
                        }
                        String str3 = fontSpan.mAttributeMap.get("face");
                        if (!TextUtils.isEmpty(str3)) {
                            ajxSpannableStringBuilder.setSpan(new AjxTypefaceSpan(str3), length, length2, 33);
                        }
                        String str4 = fontSpan.mAttributeMap.get("size");
                        if (!TextUtils.isEmpty(str4)) {
                            int indexOf = str4.indexOf(Params.UNIT_PX);
                            if (indexOf != -1) {
                                str4 = str4.substring(0, indexOf);
                            }
                            ajxSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(DimensionUtils.standardUnitToPixel((float) Integer.parseInt(str4))), length, length2, 33);
                        }
                    }
                } else if (next instanceof ImgSpan) {
                    ImgSpan imgSpan = (ImgSpan) next;
                    if (imgSpan.mImageGetter != null) {
                        String str5 = imgSpan.mAttributeMap.get("src");
                        Drawable drawable = imgSpan.mImageGetter.getDrawable(str5, imgSpan.mAttributeMap);
                        AjxImageSpan ajxImageSpan = new AjxImageSpan(drawable, imgSpan.mAttributeMap.get("id"), str5, 1);
                        ajxSpannableStringBuilder.append("￼");
                        length2 = ajxSpannableStringBuilder.length();
                        ajxSpannableStringBuilder.setSpan(ajxImageSpan, length, length2, 33);
                        if (drawable instanceof RemoteDrawable) {
                            RemoteDrawable remoteDrawable = (RemoteDrawable) drawable;
                            remoteDrawable.update();
                            View view = remoteDrawable.getView();
                            if (view instanceof Html) {
                                ((Html) view).addAjxImageSpan(ajxImageSpan);
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    ajxSpannableStringBuilder.setSpan(next, length, length2, 17);
                }
            }
        }
    }
}
