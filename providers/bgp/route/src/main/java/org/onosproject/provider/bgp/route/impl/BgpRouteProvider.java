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

package org.onosproject.provider.bgp.route.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.bgp.controller.BgpController;
import org.onosproject.bgp.controller.BgpRouteListener;
import org.onosproject.bgpio.protocol.linkstate.PathAttrNlriDetails;
import org.onosproject.core.CoreService;
import org.onosproject.incubator.net.resource.label.LabelResourceAdminService;
import org.onosproject.incubator.net.routing.RouteService;
import org.onosproject.mastership.MastershipService;
import org.onosproject.net.config.NetworkConfigService;
import org.onosproject.net.provider.AbstractProvider;
import org.onosproject.net.provider.ProviderId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provider which uses an BGP controller to detect network infrastructure topology.
 */
@Component(immediate = true)
public class BgpRouteProvider extends AbstractProvider {

    /**
     * Creates an instance of BGP route provider.
     */
    public BgpRouteProvider() {
        super(new ProviderId("route", "org.onosproject.provider.bgp"));
    }

    private static final Logger log = LoggerFactory.getLogger(BgpRouteProvider.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected RouteService routeservice;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected BgpController controller;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected MastershipService mastershipService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected LabelResourceAdminService labelResourceAdminService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected NetworkConfigService networkConfigService;

    private InternalBgpProvider listener = new InternalBgpProvider();

    @Activate
    public void activate() {
        log.debug("BgpTopologyProvider activate");
        controller.addRouteListener(listener);
    }

    @Deactivate
    public void deactivate() {
        log.debug("BgpTopologyProvider deactivate");
        controller.removeRouteListener(listener);

    }

    /*
     * Implements route update.
     */
    private class InternalBgpProvider implements BgpRouteListener {

        @Override
        public void addRoute(PathAttrNlriDetails details) {
            // TODO Auto-generated method stub

        }

        @Override
        public void deleteRoute(PathAttrNlriDetails details) {
            // TODO Auto-generated method stub

        }

    }
}
