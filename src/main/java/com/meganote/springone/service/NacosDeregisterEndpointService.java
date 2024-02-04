package com.meganote.springone.service;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;

import lombok.extern.slf4j.Slf4j;

@Component
@Endpoint(id = "deregister")
@Slf4j
public class NacosDeregisterEndpointService {

    final private NacosDiscoveryProperties nacosDiscoveryProperties;
    final private NacosRegistration nacosRegistration;
    final private NacosServiceRegistry nacosServiceRegistry;

    public NacosDeregisterEndpointService(NacosDiscoveryProperties nacosDiscoveryProperties,
            NacosRegistration nacosRegistration, NacosServiceRegistry nacosServiceRegistry) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        this.nacosRegistration = nacosRegistration;
        this.nacosServiceRegistry = nacosServiceRegistry;
    }

    @ReadOperation
    public String endpoint() {
        String service = nacosDiscoveryProperties.getService();
        String group = nacosDiscoveryProperties.getGroup();
        String clusterName = nacosDiscoveryProperties.getClusterName();
        String ip = nacosDiscoveryProperties.getIp();
        int port = nacosDiscoveryProperties.getPort();

        nacosServiceRegistry.setStatus(nacosRegistration, "DOWN");

        log.info("deregister from nacos: {}, {}, {}, {}, {}", service, group, clusterName, ip, port);

        return HttpStatus.OK.getReasonPhrase();
    }

}
