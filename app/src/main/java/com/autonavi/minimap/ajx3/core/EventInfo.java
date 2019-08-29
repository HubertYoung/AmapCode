package com.autonavi.minimap.ajx3.core;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class EventInfo {
    /* access modifiers changed from: private */
    public long altNodeId;
    /* access modifiers changed from: private */
    public Parcel attribute;
    /* access modifiers changed from: private */
    public JSONObject content;
    /* access modifiers changed from: private */
    public String eventName;
    /* access modifiers changed from: private */
    public long hoverNodeId;
    /* access modifiers changed from: private */
    public long nodeId;
    /* access modifiers changed from: private */
    public Parcel touch;

    public static class Builder {
        private EventInfo eventInfo = new EventInfo();
        private Map<String, Object> mAttributes = new HashMap();
        private Map<String, Object> mConents = new HashMap();

        public Builder setEventName(String str) {
            this.eventInfo.eventName = str;
            return this;
        }

        public Builder setNodeId(long j) {
            this.eventInfo.nodeId = j;
            return this;
        }

        public Builder setAltNodeId(long j) {
            this.eventInfo.altNodeId = j;
            return this;
        }

        public Builder setHoverNodeId(long j) {
            this.eventInfo.hoverNodeId = j;
            return this;
        }

        public Builder setAttribute(Parcel parcel) {
            this.eventInfo.attribute = parcel;
            return this;
        }

        public Builder setTouch(Parcel parcel) {
            this.eventInfo.touch = parcel;
            return this;
        }

        public Builder setContent(JSONObject jSONObject) {
            this.eventInfo.content = jSONObject;
            return this;
        }

        public Builder addAttribute(String str, String str2) {
            if (!TextUtils.isEmpty(str)) {
                this.mAttributes.put(str, str2);
            }
            return this;
        }

        public Builder addContent(String str, Object obj) {
            if (!TextUtils.isEmpty(str)) {
                this.mConents.put(str, obj);
            }
            return this;
        }

        public EventInfo build() {
            if (this.mConents.size() > 0) {
                if (this.eventInfo.content == null) {
                    this.eventInfo.content = new JSONObject();
                }
                try {
                    for (Entry next : this.mConents.entrySet()) {
                        this.eventInfo.content.put((String) next.getKey(), next.getValue());
                    }
                } catch (JSONException unused) {
                }
            }
            if (this.mAttributes.size() > 0) {
                if (this.eventInfo.attribute == null) {
                    this.eventInfo.attribute = new Parcel();
                } else {
                    this.eventInfo.attribute.reset();
                    int readInt = this.eventInfo.attribute.readInt();
                    for (int i = 0; i < readInt / 2; i++) {
                        this.mAttributes.put(this.eventInfo.attribute.readString(), this.eventInfo.attribute.readString());
                    }
                    this.eventInfo.attribute.reset();
                }
                this.eventInfo.attribute.writeInt(this.mAttributes.size() * 2);
                for (Entry next2 : this.mAttributes.entrySet()) {
                    this.eventInfo.attribute.writeString((String) next2.getKey());
                    this.eventInfo.attribute.writeString((String) next2.getValue());
                }
            }
            return this.eventInfo;
        }
    }

    private EventInfo() {
        this.nodeId = -1;
        this.altNodeId = -1;
        this.hoverNodeId = -1;
    }

    public String getEventName() {
        return this.eventName;
    }

    public long getNodeId() {
        return this.nodeId;
    }

    public long getAltNodeId() {
        return this.altNodeId;
    }

    public long getHoverNodeId() {
        return this.hoverNodeId;
    }

    public Parcel getAttribute() {
        return this.attribute;
    }

    public Parcel getTouch() {
        return this.touch;
    }

    public JSONObject getContent() {
        return this.content;
    }
}
