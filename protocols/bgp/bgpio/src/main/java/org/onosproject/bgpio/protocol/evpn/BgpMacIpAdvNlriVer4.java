package org.onosproject.bgpio.protocol.evpn;



import java.net.InetAddress;
import org.jboss.netty.buffer.ChannelBuffer;
import org.onlab.packet.MacAddress;
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.protocol.RouteType;
import org.onosproject.bgpio.types.EthernetSegmentidentifier;
import org.onosproject.bgpio.types.MplsLabel;
import org.onosproject.bgpio.types.RouteDistinguisher;
import org.onosproject.bgpio.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

public class BgpMacIpAdvNlriVer4 implements RouteTypeSpec {

    /*
     * REFERENCE : RFC 7432 BGP MPLS-Based Ethernet VPN
         +---------------------------------------+
         | RD (8 octets) |
         +---------------------------------------+
         |Ethernet Segment Identifier (10 octets)|
         +---------------------------------------+
         | Ethernet Tag ID (4 octets) |
         +---------------------------------------+
         | MAC Address Length (1 octet) |
         +---------------------------------------+
         | MAC Address (6 octets) |
         +---------------------------------------+
         | IP Address Length (1 octet) |
         +---------------------------------------+
         | IP Address (0, 4, or 16 octets) |
         +---------------------------------------+
         | MPLS Label1 (3 octets) |
         +---------------------------------------+
         | MPLS Label2 (0 or 3 octets) |
         +---------------------------------------+

      Figure : A MAC/IP Advertisement route type specific EVPN NLRI

     */

    public static final short TYPE = 2;
    protected static final Logger log = LoggerFactory.getLogger(BgpMacIpAdvNlriVer4.class);
    public static final short IPV4_ADDRESS_LENGTH = 4;
    public static final short MAC_ADDRESS_LENGTH = 6;
    private RouteDistinguisher routeDistinguisher;
    private EthernetSegmentidentifier ethernetSegmentidentifier;
    private int ethernetTagID;
    private byte macAddressLength;
    private MacAddress macAddress;
    private byte ipAddressLength;
    private InetAddress ipAddress;
    private MplsLabel mplsLabel1;
    private MplsLabel mplsLabel2;

    /**
     * Resets parameters.
     */
    public BgpMacIpAdvNlriVer4() {
        this.routeDistinguisher = null;
        this.ethernetSegmentidentifier = null;
        this.ethernetTagID = 0;
        this.macAddressLength = MAC_ADDRESS_LENGTH;
        this.macAddress = null;
        this.ipAddressLength = IPV4_ADDRESS_LENGTH;
        this.ipAddress = null;
        this.mplsLabel1 = null;
        this.mplsLabel2 = null;
    }

    public BgpMacIpAdvNlriVer4(RouteDistinguisher routeDistinguisher,
                               EthernetSegmentidentifier ethernetSegmentidentifier,
                               int ethernetTagID, byte macAddressLength, MacAddress macAddress,
                               byte ipAddressLength, InetAddress ipAddress, MplsLabel mplsLabel1,
                               MplsLabel mplsLabel2) {
        this.routeDistinguisher = routeDistinguisher;
        this.ethernetSegmentidentifier = ethernetSegmentidentifier;
        this.ethernetTagID = ethernetTagID;
        this.macAddressLength = macAddressLength;
        this.macAddress = macAddress;
        this.ipAddressLength = ipAddressLength;
        this.ipAddress = ipAddress;
        this.mplsLabel1 = mplsLabel1;
        this.mplsLabel2 = mplsLabel2;
    }

    public static BgpMacIpAdvNlriVer4 read(ChannelBuffer cb) throws BgpParseException {
        if (cb.readableBytes() == 0) {
            return null;
        }
        RouteDistinguisher routeDistinguisher = RouteDistinguisher.read(cb);
        EthernetSegmentidentifier ethernetSegmentidentifier = EthernetSegmentidentifier.read(cb);
        int ethernetTagID = cb.readInt();
        byte macAddressLength = cb.readByte();
        MacAddress macAddress = Validation.toMacAddress(MAC_ADDRESS_LENGTH, cb);
        byte ipAddressLength = cb.readByte();
        InetAddress ipAddress = Validation.toInetAddress(IPV4_ADDRESS_LENGTH, cb);
        MplsLabel mplsLabel1 = MplsLabel.read(cb);
        MplsLabel mplsLabel2 = MplsLabel.read(cb);

        return new BgpMacIpAdvNlriVer4(routeDistinguisher,
                                       ethernetSegmentidentifier, ethernetTagID,
                                       macAddressLength, macAddress,
                                       ipAddressLength, ipAddress, mplsLabel1,
                                       mplsLabel2);
    }

    @Override
    public int write(ChannelBuffer cb) {
        int iLenStartIndex = cb.writerIndex();
        cb.writeLong(routeDistinguisher.getRouteDistinguisher());
        return cb.writerIndex() - iLenStartIndex;
    }

    public RouteDistinguisher getRouteDistinguisher() {
        return routeDistinguisher;
    }

    public EthernetSegmentidentifier getEthernetSegmentidentifier() {
        return ethernetSegmentidentifier;
    }

    public int getEthernetTagID() {
        return ethernetTagID;
    }

    public MacAddress getMacAddress() {
        return macAddress;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public MplsLabel getMplsLable1() {
        return mplsLabel1;
    }

    public MplsLabel getMplsLable2() {
        return mplsLabel2;
    }

    @Override
    public RouteType getType() {
        return RouteType.MAC_IP_ADVERTISEMENT;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("routeDistinguisher ", routeDistinguisher)
                .add("ethernetSegmentidentifier", ethernetSegmentidentifier)
                .add("ethernetTagID", ethernetTagID)
                .add("macAddressLength", macAddressLength)
                .add("macAddress ", macAddress)
                .add("ipAddressLength", ipAddressLength)
                .add("ipAddress", ipAddress)
                .add("mplsLabel1 ", mplsLabel1)
                .add("mplsLabel2", mplsLabel2).toString();
    }

}
