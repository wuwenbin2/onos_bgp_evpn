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
import org.onosproject.bgpio.protocol.RouteType;
import org.onosproject.bgpio.types.BgpErrorType;
import org.onosproject.bgpio.types.BgpValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

/**
 * Implementation of Evpn NLRI.
 */
public class BgpEvpnNlriVer4 implements BgpEvpnNlri, BgpValueType {

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

    public static final int TYPE_AND_LEN = 4;
    private byte routeType;
    private byte length;
    private RouteTypeSpec routeTypeSpec;

    /**
     * Resets parameters.
     */
    public BgpEvpnNlriVer4() {
        this.routeType = 2;
        this.length = 0;
        this.routeTypeSpec = null;

    }

    /**
     * Constructor to initialize parameters for BGP PrefixLSNlri.
     *
     * @param identifier field in BGP PrefixLSNlri
     * @param protocolId protocol Id
     * @param bgpPrefixLSIdentifier prefix LS Identifier
     * @param routeDistinguisher RouteDistinguisher
     * @param isVpn vpn availability in message
     */
    public BgpEvpnNlriVer4(byte routeType, byte length,
                           RouteTypeSpec routeTypeSpefic) {
        this.routeType = routeType;
        this.length = length;
        this.routeTypeSpec = routeTypeSpefic;
    }

    /**
     * Reads from channelBuffer and parses Prefix LS Nlri.
     *
     * @param cb ChannelBuffer
     * @param afi Address family identifier
     * @param safi Subsequent address family identifier
     * @return object of BGPPrefixIPv4LSNlriVer4
     * @throws BgpParseException while parsing Prefix LS Nlri
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
            return new BgpEvpnNlriVer4(type, length, routeTypeSpefic);
        } else {
            return new BgpEvpnNlriVer4();
        }

    }

    @Override
    public RouteTypeSpec getRouteTypeSpefic() {
        return routeTypeSpec;
    }

    @Override
    public RouteType getRouteType() throws BgpParseException {
        switch (routeType) {
        case 1:
            return RouteType.ETHERNET_AUTO_DISCOVERY;
        case 2:
            return RouteType.MAC_IP_ADVERTISEMENT;
        case 3:
            return RouteType.INCLUSIVE_MULTICASE_ETHERNET;
        case 4:
            return RouteType.ETHERNET_SEGMENT;
        default:
            throw new BgpParseException(BgpErrorType.UPDATE_MESSAGE_ERROR,
                                        (byte) 0, null);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("routeType", routeType).add("length", length)
                .add("routeTypeSpefic ", routeTypeSpec).toString();
    }

    @Override
    public short getType() {
        return routeType;
    }

    @Override
    public int write(ChannelBuffer cb) {
        int iLenStartIndex = cb.writerIndex();
        cb.writeShort(routeType);
        cb.writeByte(length);
        routeTypeSpec.write(cb);
        return cb.writerIndex() - iLenStartIndex;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


}

