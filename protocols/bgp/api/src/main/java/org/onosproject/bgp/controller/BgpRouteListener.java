/*
 * Copyright 2015-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.onosproject.bgp.controller;

import org.onosproject.bgpio.protocol.linkstate.PathAttrNlriDetails;

/**
 * Allows for providers interested in node events to be notified.
 */
public interface BgpRouteListener {

    /**
     * Notifies that the route was added.
     *
     * @param details attributes and nlri details
     */
    void addRoute(PathAttrNlriDetails details);

    /**
     * Notifies that the route was removed.
     *
     * @param details attributes and nlri details
     */
    void deleteRoute(PathAttrNlriDetails details);
}

