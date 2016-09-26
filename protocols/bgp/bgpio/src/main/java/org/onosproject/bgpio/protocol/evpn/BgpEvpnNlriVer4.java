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
package org.onosproject.bgpio.protocol.evpn;

import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.protocol.BgpEvpnNlri;
import org.onosproject.bgpio.types.BgpErrorType;
import org.onosproject.bgpio.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

/**
 * Implementation of Evpn NLRI.
 */
public class BgpEvpnNlriVer4 implements BgpEvpnNlri {

    /*
     * REFERENCE : RFC 7432 BGP MPLS-Based Ethernet VPN
                 +-----------------------------------+
                 |    Route Type (1 octet)           |
                 +-----------------------------------+
                 |     Length (1 octet)              |
                 +-----------------------------------+
                 | Route Type specific (variable)    |
                 +-----------------------------------+

                Figure : The format of the EVPN NLRI
     */

    protected static final Logger log = LoggerFactory
            .getLogger(BgpEvpnNlriVer4.class);

    // total length of route type and length
    public static final short TYPE_AND_LEN = 2;
    private byte routeType;
    private RouteTypeSpec routeTypeSpec;

    /**
     * Resets parameters.
     */
    public BgpEvpnNlriVer4() {
        this.routeType = 2;
        this.routeTypeSpec = null;

    }

    /**
     * Constructor to initialize parameters for BGP EvpnNlri.
     *
     * @param routeType route Type
     * @param routeTypeSpefic route type specific
     */
    public BgpEvpnNlriVer4(byte routeType, RouteTypeSpec routeTypeSpefic) {
        this.routeType = routeType;
        this.routeTypeSpec = routeTypeSpefic;
    }

    /**
     * Reads from channelBuffer and parses Evpn Nlri.
     *
     * @param cb ChannelBuffer
     * @return object of BgpEvpnNlriVer4
     * @throws BgpParseException while parsing Bgp Evpn Nlri
     */
    public static BgpEvpnNlriVer4 read(ChannelBuffer cb)
            throws BgpParseException {

        RouteTypeSpec routeTypeSpefic = null;

        if (cb.readableBytes() > 0) {
            ChannelBuffer tempBuf = cb.copy();
            byte type = cb.readByte();
            byte length = cb.readByte();
            if (cb.readableBytes() < length) {
                throw new BgpParseException(BgpErrorType.UPDATE_MESSAGE_ERROR,
                                            BgpErrorType.OPTIONAL_ATTRIBUTE_ERROR,
                                            tempBuf.readBytes(cb.readableBytes()
                                                    + TYPE_AND_LEN));
            }
            ChannelBuffer tempCb = cb.readBytes(length);
            switch (type) {
            case BgpMacIpAdvNlriVer4.TYPE:
                routeTypeSpefic = BgpMacIpAdvNlriVer4.read(tempCb);
                break;
            default:
                break;
            }
            return new BgpEvpnNlriVer4(type, routeTypeSpefic);
        } else {
            return new BgpEvpnNlriVer4();
        }

    }

    @Override
    public RouteTypeSpec getRouteTypeSpec() {
        return routeTypeSpec;
    }

    @Override
    public RouteType getRouteType() throws BgpParseException {
        switch (routeType) {
        case Constants.BGP_EVPN_ETHERNET_AUTO_DISCOVERY:
            return RouteType.ETHERNET_AUTO_DISCOVERY;
        case Constants.BGP_EVPN_MAC_IP_ADVERTISEMENT:
            return RouteType.MAC_IP_ADVERTISEMENT;
        case Constants.BGP_EVPN_INCLUSIVE_MULTICASE_ETHERNET:
            return RouteType.INCLUSIVE_MULTICASE_ETHERNET;
        case Constants.BGP_EVPN_ETHERNET_SEGMENT:
            return RouteType.ETHERNET_SEGMENT;
        default:
            throw new BgpParseException(BgpErrorType.UPDATE_MESSAGE_ERROR,
                                        (byte) 0, null);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("routeType", routeType)
                .add("routeTypeSpefic ", routeTypeSpec).toString();
    }

    @Override
    public short getType() {
        return routeType;
    }

    @Override
    public int write(ChannelBuffer cb) {
        int iLenStartIndex = cb.writerIndex();
        cb.writeByte(routeType);
        int iSpecStartIndex = cb.writerIndex();
        cb.writeByte(0);
        routeTypeSpec.write(cb);
        cb.setByte(iSpecStartIndex,
                   (short) (cb.writerIndex() - iSpecStartIndex + 1));
        return cb.writerIndex() - iLenStartIndex;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


}

