package org.onosproject.bgpio.types;

public enum NlriDetailsType {

        LINK_STATE(1), FLOW_SPEIC(2), EVPN(3);
        int value;

        /**
         * Assign val with the value as the nlri details type.
         *
         * @param val nlri details type
         */
        NlriDetailsType(int val) {
            value = val;
        }

        /**
         * Returns value of nlri details type.
         *
         * @return nlri details type
         */
        public int getType() {
            return value;
        }
    }
