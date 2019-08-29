package com.autonavi.miniapp.plugin.map.property;

import java.io.Serializable;

public class Position implements Serializable {
    public double height;
    public double left = 0.0d;
    public double top = 0.0d;
    public double width;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position position = (Position) obj;
        return Double.compare(position.left, this.left) == 0 && Double.compare(position.top, this.top) == 0 && Double.compare(position.width, this.width) == 0 && Double.compare(position.height, this.height) == 0;
    }
}
