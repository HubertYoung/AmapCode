package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drive.common.yuncontrol.VersionInfoItem;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.xidea.el.json.JSONDecoder;

/* renamed from: ni reason: default package */
/* compiled from: YunControlResponser */
public final class ni extends AbstractAOSParser {
    public VersionInfoItem a;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONObject parseHeader = parseHeader(bArr);
        if (this.errorCode == 1 && parseHeader != null) {
            this.a = (VersionInfoItem) JSONDecoder.decode(new JSONObject(new JSONObject(parseHeader.getString("data")).getString("conf_data")).toString(), VersionInfoItem.class);
            JSONObject jSONObject = new JSONObject(this.a.md5_inner);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                String string = jSONObject.getString(next);
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(string)) {
                    this.a.items.put(next, string);
                }
            }
            NaviManager.a();
            StringBuilder sb = new StringBuilder("YunConfigurationManager : update info,");
            sb.append(this.a.file);
            sb.append("/n");
            sb.append(this.a.path);
            sb.append("/n");
            sb.append(this.a.size);
            sb.append("/n");
            sb.append(this.a.md5_zip);
            sb.append("/n");
            sb.append(this.a.version);
            NaviManager.a(sb.toString());
        }
    }
}
