package org.onosproject.bgpio.types;

import java.util.Objects;
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.base.MoreObjects;

public class MplsLabel implements Comparable<MplsLabel> {

    private String mplsLabel;

    /**
     * Resets fields.
     */
    public MplsLabel() {
        this.mplsLabel = null;
    }

    /**
     * Constructor to initialize parameters.
     *
     * @param routeDistinguisher route distinguisher
     */
    public MplsLabel(String mplslabel) {
        this.mplsLabel = mplslabel;
    }

    /**
     * Reads route distinguisher from channelBuffer.
     *
     * @param cb channelBuffer
     * @return object of RouteDistinguisher
     */
    public static MplsLabel read(ChannelBuffer cb) {
        return new MplsLabel(cb.readBytes(3).toString());
    }

    /**
     * Returns route distinguisher.
     *
     * @return route distinguisher
     */
    public String getMplsLabel() {
        return this.mplsLabel;
    }

    @Override
    public int compareTo(MplsLabel mplsLabel) {
        if (this.equals(mplsLabel)) {
            return 0;
        }
        return ((this.getMplsLabel())).compareTo((mplsLabel.getMplsLabel()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof MplsLabel) {

            MplsLabel that = (MplsLabel) obj;

            if (this.mplsLabel == that.mplsLabel) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mplsLabel);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("mplsLabel", mplsLabel)
                .toString();
    }
}
