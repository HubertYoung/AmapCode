package com.autonavi.minimap.ajx3.acanvas.module;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.property.TouchHelper;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule(isInUiThread = false, value = "ajx.touch")
public class AjxModuleTouch extends AbstractModule {
    private TouchHelper mTouchHelper;
    private JSONObject mTouchPosition = new JSONObject();

    public AjxModuleTouch(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getTouchPosition")
    public JSONObject getTouchPosition() {
        if (this.mTouchHelper == null) {
            this.mTouchHelper = getContext().getDomTree().getRootView().getHelper();
        }
        try {
            float[] touchMovePosition = this.mTouchHelper.getTouchMovePosition();
            this.mTouchPosition.put("clientX", (double) DimensionUtils.pixelToStandardUnit(touchMovePosition[0]));
            this.mTouchPosition.put("clientY", (double) DimensionUtils.pixelToStandardUnit(touchMovePosition[1]));
            this.mTouchPosition.put("screenX", (double) DimensionUtils.pixelToStandardUnit(touchMovePosition[2]));
            this.mTouchPosition.put("screenY", (double) DimensionUtils.pixelToStandardUnit(touchMovePosition[3]));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.mTouchPosition;
    }
}
