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

import java.util.Objects;
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.base.MoreObjects;

/**
 * Implementation of EthernetSegmentidentifier.
 */
public class EthernetSegmentidentifier implements Comparable<EthernetSegmentidentifier> {

    private String ethernetSegmentidentifier;

    /**
     * Resets fields.
     */
    public EthernetSegmentidentifier() {
        this.ethernetSegmentidentifier = null;
    }

    /**
     * Constructor to initialize parameters.
     *
     * @param ethernetSegmentidentifier Ethernet Segment identifier
     */
    public EthernetSegmentidentifier(String ethernetSegmentidentifier) {
        this.ethernetSegmentidentifier = ethernetSegmentidentifier;
    }

    /**
     * Reads Ethernet Segment identifier from channelBuffer.
     *
     * @param cb channelBuffer
     * @return object of EthernetSegmentidentifier
     */
    public static EthernetSegmentidentifier read(ChannelBuffer cb) {
        return new EthernetSegmentidentifier(cb.readBytes(10).toString());
    }

    /**
     * Returns Ethernet Segment identifier.
     *
     * @return Ethernet Segment identifier.
     */
    public String getEthernetSegmentidentifier() {
        return this.ethernetSegmentidentifier;
    }

    @Override
    public int compareTo(EthernetSegmentidentifier rd) {
        if (this.equals(rd)) {
            return 0;
        }
        return ((this.getEthernetSegmentidentifier()))
                .compareTo((rd.getEthernetSegmentidentifier()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(ethernetSegmentidentifier);
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof EthernetSegmentidentifier) {
            EthernetSegmentidentifier that = (EthernetSegmentidentifier) obj;
            if (this.ethernetSegmentidentifier == that.ethernetSegmentidentifier) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("ethernetSegmentidentifier", ethernetSegmentidentifier)
                .toString();
    }
}
