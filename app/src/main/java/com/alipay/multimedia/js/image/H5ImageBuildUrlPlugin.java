package com.alipay.multimedia.js.image;

import android.os.Bundle;
import android.util.Log;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5ImageBuildUrlPlugin extends MMH5SimplePlugin {
    public static final String ACTIONS = "buildUrl";

    public class Params {
        public static final int MATCH_CENTER_CROP = 1;
        public static final int MATCH_CENTER_CROP_V2 = 3;
        public static final int MATCH_KEEP_RATIO = 0;
        public static final int MATCH_ORIGINAL = 2;
        public static final int MATCH_SMART_CROP = 4;
        public static final String UNIT_DP = "dp";
        public static final String UNIT_PX = "px";
        @JSONField(name = "height")
        public int height;
        @JSONField(name = "id")
        public String id;
        @JSONField(name = "match")
        public int match = 0;
        @JSONField(name = "https")
        public boolean preferHttps = true;
        @JSONField(name = "publicDomain")
        public boolean publicDomain = false;
        @JSONField(name = "quality")
        public int quality = -1;
        @JSONField(name = "unit")
        public String unit = UNIT_DP;
        @JSONField(name = "width")
        public int width;

        public Params() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public String toString() {
            return "Params{id='" + this.id + '\'' + ", width=" + this.width + ", height=" + this.height + ", match=" + this.match + ", preferHttps=" + this.preferHttps + ", quality=" + this.quality + ", unit='" + this.unit + '\'' + ", publicDomain='" + this.publicDomain + '\'' + '}';
        }
    }

    public H5ImageBuildUrlPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTIONS);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        final Params params = (Params) parseParams(event, Params.class);
        Logger.debug("H5ImageBuildUrl", "handleEvent params: " + params);
        if (params != null) {
            a(params);
            Logger.debug("H5ImageBuildUrl", "handleEvent fixSize params: " + params);
            final MultimediaImageService service = (MultimediaImageService) Utils.getService(MultimediaImageService.class);
            if (service != null) {
                final H5BridgeContext h5BridgeContext = context;
                final H5Event h5Event = event;
                Utils.execute(new Runnable() {
                    {
                        if (Boolean.FALSE.booleanValue()) {
                            Log.v("hackbyte ", ClassVerifier.class.toString());
                        }
                    }

                    public void run() {
                        Builder builder = new Builder().width(Integer.valueOf(params.width)).height(Integer.valueOf(params.height));
                        switch (params.match) {
                            case 0:
                                builder.imageScaleType(CutScaleType.KEEP_RATIO);
                                break;
                            case 1:
                            case 3:
                                builder.imageScaleType(CutScaleType.AUTO_CUT_EXACTLY);
                                break;
                            case 2:
                                builder.imageScaleType(CutScaleType.NONE);
                                break;
                            case 4:
                                builder.imageScaleType(CutScaleType.SMART_CROP);
                                break;
                            default:
                                builder.imageScaleType(CutScaleType.NONE);
                                break;
                        }
                        if (params.quality > 0) {
                            builder.quality(params.quality);
                        }
                        Bundle extraConfig = new Bundle();
                        if (params.preferHttps) {
                            extraConfig.putBoolean("https", true);
                        }
                        extraConfig.putBoolean("publicDomain", params.publicDomain);
                        try {
                            DisplayImageOptions options = builder.build();
                            String url = service.buildUrl(params.id, options, extraConfig);
                            Logger.debug("H5ImageBuildUrl", "buildUrl param: " + params + ", opts: " + options + ", ext: " + extraConfig + ", url: " + url);
                            h5BridgeContext.sendBridgeResult("url", url);
                        } catch (Exception e2) {
                            Logger.error("H5ImageBuildUrl", "buildUrl error, params: " + params, e2);
                            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
                        }
                    }
                });
                return true;
            }
        }
        return context.sendError(event, Error.INVALID_PARAM);
    }

    private void a(Params params) {
        if (params != null && params.width > 0 && params.height > 0 && Params.UNIT_DP.equalsIgnoreCase(params.unit)) {
            params.width = dip2px((float) params.width);
            params.height = dip2px((float) params.height);
        }
    }
}
