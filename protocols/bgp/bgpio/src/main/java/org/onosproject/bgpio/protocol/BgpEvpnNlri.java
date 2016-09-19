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
package org.onosproject.bgpio.protocol;

import org.onosproject.bgpio.exceptions.BgpParseException;

/**
 * Abstraction of an entity providing BGP-EVPN NLRI.
 */
public interface BgpEvpnNlri {

    /**
     * Returns route type in Nlri.
     *
     * @return route type in Nlri
     * @throws BgpParseException while getting route type
     */
    RouteType getRouteType() throws BgpParseException;

}
