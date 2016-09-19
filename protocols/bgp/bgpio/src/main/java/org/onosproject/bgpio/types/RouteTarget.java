/*
 * Copyright 2015-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.bgpio.types;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bgpio.util.Constants;
import com.google.common.base.MoreObjects;

/**
 * Implementation of RouteTarget.
 */
public class RouteTarget implements BgpValueType {

    public static final short TYPE = Constants.BGP_ROUTE_TARGET;
    private long routeTarget;

    /**
     * Resets fields.
     */
    public RouteTarget() {
        this.routeTarget = 0;
    }

    /**
     * Constructor to initialize parameters.
     *
     * @param routeTarget route target
     */
    public RouteTarget(long routeTarget) {
        this.routeTarget = routeTarget;
    }

    /**
     * Reads route target from channelBuffer.
     *
     * @param cb channelBuffer
     * @return object of RouteTarget
     */
    public static RouteTarget read(ChannelBuffer cb) {
        return new RouteTarget(cb.readLong());
    }

    /**
     * Returns route target.
     *
     * @return route target
     */
    public long getRouteTarget() {
        return this.routeTarget;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof RouteTarget) {
            RouteTarget that = (RouteTarget) obj;
            if (this.routeTarget == that.routeTarget) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(routeTarget);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("routeTarget", routeTarget).toString();
    }

    @Override
    public short getType() {
        return TYPE;
    }

    @Override
    public int write(ChannelBuffer cb) {
        int iLenStartIndex = cb.writerIndex();
        cb.writeShort(TYPE);
        cb.writeLong(routeTarget);
        return cb.writerIndex() - iLenStartIndex;
    }

    @Override
    public int compareTo(Object rd) {
        if (this.equals(rd)) {
            return 0;
        }
        return ((Long) (this.getRouteTarget()))
                .compareTo((Long) (((RouteTarget) rd).getRouteTarget()));
    }
}
