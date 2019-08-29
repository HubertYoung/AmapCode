package defpackage;

import android.content.res.Resources;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.busline.model.BusLineSearchException;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearchResultImpl;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

/* renamed from: duk reason: default package */
/* compiled from: BusLineSearchResponse */
public final class duk extends AbstractAOSParser {
    public IBusLineSearchResult a = null;

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        if (this.errorCode != -1) {
            if (this.errorCode == 1) {
                this.a = new BusLineSearchResultImpl();
                this.a.parse(this.mDataObject);
                return;
            }
            throw new BusLineSearchException(this.errorCode, getErrorDesc(this.errorCode));
        }
    }

    public final String getErrorDesc(int i) {
        Resources resources = AMapAppGlobal.getApplication().getApplicationContext().getResources();
        if (i == 7) {
            return resources.getString(R.string.error_no_record_found);
        }
        if (i == 12) {
            return resources.getString(R.string.error_permission_denial);
        }
        switch (i) {
            case 0:
                return resources.getString(R.string.error_server_busy);
            case 1:
                return "";
            case 2:
                return resources.getString(R.string.error_request_failure);
            case 3:
                return resources.getString(R.string.error_incorrect_parameter);
            case 4:
                return resources.getString(R.string.error_incorrect_signature);
            case 5:
                return resources.getString(R.string.error_outdated_license);
            default:
                return resources.getString(R.string.error_default_message);
        }
    }
}
