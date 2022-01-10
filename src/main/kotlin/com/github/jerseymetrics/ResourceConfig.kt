package com.github.jerseymetrics

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.ApplicationPath

@Configuration
@ApplicationPath("/jersey")
class ResourceConfig: ResourceConfig(), InitializingBean {
    override fun afterPropertiesSet() {
        registerClasses(TestResource::class.java)
    }
}
