package com.fuck.gateway;

import com.fuck.gateway.annotation.NettyHandler;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBootGatewayApplication
 * @author duansg
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(includeFilters = @ComponentScan.Filter(NettyHandler.class))
public class SpringBootGatewayApplication {

    /**
     * Turn off the Spring Boot Web feature，It's just starting with Netty.
     * @param clazz
     * @param args
     */
    protected static void run( Class<? extends SpringBootGatewayApplication> clazz , String[] args ) {
        new SpringApplicationBuilder(clazz).web(WebApplicationType.NONE).run(args);
    }
}
